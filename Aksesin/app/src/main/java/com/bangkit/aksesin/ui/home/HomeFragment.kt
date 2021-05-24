@file:Suppress("DEPRECATION")

package com.bangkit.aksesin.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.aksesin.R
import com.bangkit.aksesin.databinding.FragmentHomeBinding
import com.bangkit.aksesin.ui.base.BaseFragment
import com.bangkit.aksesin.ui.search.SearchActivity
import com.bangkit.aksesin.ui.search.SearchActivity.Companion.EXTRA_CURR_LOCATION
import com.bangkit.aksesin.ui.search.SearchActivity.Companion.EXTRA_DESTINATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ObsoleteCoroutinesApi


@ObsoleteCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastKnownLocation: Location? = null

    private var isLocationPermissionGranted = false

    private var place: com.bangkit.aksesin.core.domain.model.Location? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding.btnNavigation.isEnabled = false

        binding.fabMyLocation.setOnClickListener {
            getDeviceLocation()
        }

        navigateToSearchActivity()
    }

    override fun onResume() {
        super.onResume()
        val navBar =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
        getDeviceLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        getLocationPermission()

        if (isLocationPermissionGranted) {
            getDeviceLocation()
        } else {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    defaultLocation,
                    DEFAULT_ZOOM.toFloat()
                )
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_ACCESS_FINE_LOCATION -> {
                val isGrantResultProvided = grantResults.isNotEmpty()
                val isGrantResultGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                isLocationPermissionGranted = isGrantResultProvided && isGrantResultGranted
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val isAlreadySearch = resultCode == RESULT_SEARCH
            val isRequestSearch = requestCode == REQUEST_SEARCH
            if (isRequestSearch && isAlreadySearch) {
                binding.btnNavigation.isEnabled = true
                place = data.getParcelableExtra(EXTRA_DESTINATION)
            }
        } else {
            binding.btnNavigation.isEnabled = false
        }
    }

    private fun navigateToSearchActivity() {
        binding.svLocation.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(EXTRA_CURR_LOCATION, place)
            startActivityForResult(intent, REQUEST_SEARCH)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (isLocationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        lastKnownLocation = location
                        lastKnownLocation?.let { currLocation ->
                            place = com.bangkit.aksesin.core.domain.model.Location(
                                currLocation.latitude,
                                currLocation.longitude
                            )
                            moveCamera(currLocation)
                        }
                    } else {
                        lastKnownLocation?.let { currLocation ->
                            place = com.bangkit.aksesin.core.domain.model.Location(
                                currLocation.latitude,
                                currLocation.longitude
                            )
                            moveCamera(currLocation)
                        }
                        requestPermission()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun moveCamera(location: Location) {
        val currLocation = LatLng(location.latitude, location.longitude)
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                currLocation,
                DEFAULT_ZOOM.toFloat()
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocationPermission() {
        val permissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            isLocationPermissionGranted = true
            map.isMyLocationEnabled = true
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val DEFAULT_ZOOM = 15

        private val defaultLocation = LatLng(-6.21462, 106.84513)

        const val REQUEST_SEARCH = 1

        const val RESULT_SEARCH = 2
    }
}