package com.bangkit.aksesin.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.aksesin.R
import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Location
import com.bangkit.aksesin.databinding.ActivitySearchBinding
import com.bangkit.aksesin.ui.adapter.SearchPlacesAdapter
import com.bangkit.aksesin.ui.base.BaseActivity
import com.bangkit.aksesin.ui.home.HomeFragment.Companion.RESULT_SEARCH
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var searchAdapter: SearchPlacesAdapter

    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)

    private var destination: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecyclerView()

        searchPlaces()

        binding.imgBack.setOnClickListener {
            finish()
        }

        onItemClicked()
    }

    private fun searchPlaces() {
        val lastKnownLocation = intent.getParcelableExtra<Location>(EXTRA_CURR_LOCATION)
        val place = LatLng(lastKnownLocation!!.lat, lastKnownLocation.lng)
        val getPlace = "${place.latitude}, ${place.longitude}"
        var job: Job? = null
        binding.svLocation.onActionViewExpanded()
        binding.svLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(500)
                    binding.progressBar.visibility = View.VISIBLE
                    subscribeToObserver(newText, getPlace)
                }
                return true
            }
        })
    }

    private fun subscribeToObserver(newText: String, place: String) {
        viewModel.searchPlace(newText, place)
            .observe(this@SearchActivity) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = resource.data
                        if (data.isNullOrEmpty()) {
                            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_SHORT)
                                .show()
                        } else searchAdapter.setData(data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                }
            }
    }

    private fun onItemClicked() {
        searchAdapter.onItemClickListener = { selectedItem ->
            destination = selectedItem.geometry?.location
            val intent = Intent().apply {
                putExtra(EXTRA_DESTINATION, destination)
            }
            setResult(RESULT_SEARCH, intent)
            finish()
        }
    }

    private fun setupRecyclerView() = binding.rvSearch.apply {
        searchAdapter = SearchPlacesAdapter()
        adapter = searchAdapter
        layoutManager = LinearLayoutManager(this@SearchActivity)
        setHasFixedSize(true)
    }

    companion object {
        const val EXTRA_CURR_LOCATION = "curr_location"

        const val EXTRA_DESTINATION = "destination"
    }
}