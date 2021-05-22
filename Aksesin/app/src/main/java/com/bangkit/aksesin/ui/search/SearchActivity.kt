package com.bangkit.aksesin.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.aksesin.R
import com.bangkit.aksesin.core.data.Resource
import com.bangkit.aksesin.core.domain.model.Location
import com.bangkit.aksesin.databinding.ActivitySearchBinding
import com.bangkit.aksesin.ui.adapter.SearchPlacesAdapter
import com.bangkit.aksesin.ui.base.BaseActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var searchAdapter: SearchPlacesAdapter

    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecyclerView()

        searchPlaces()
    }

    private fun searchPlaces() {
        val lastKnownLocation = intent.getParcelableExtra<Location>(EXTRA_CURR_LOCATION)
        val place = LatLng(lastKnownLocation!!.lat, lastKnownLocation.lng)
        binding.svLocation.onActionViewExpanded()
        binding.svLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchPlace(newText, place).observe(this@SearchActivity) { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            searchAdapter.setData(resource.data)
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> Unit
                    }
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() = binding.rvSearch.apply {
        searchAdapter = SearchPlacesAdapter()
        adapter = searchAdapter
        layoutManager = LinearLayoutManager(this@SearchActivity)
        setHasFixedSize(true)
    }

    companion object {
        const val EXTRA_CURR_LOCATION = "curr_location"
    }
}