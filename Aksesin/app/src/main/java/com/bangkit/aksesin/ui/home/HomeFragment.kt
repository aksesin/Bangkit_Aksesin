@file:Suppress("DEPRECATION")

package com.bangkit.aksesin.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.aksesin.R
import com.bangkit.aksesin.databinding.FragmentHomeBinding
import com.bangkit.aksesin.ui.base.BaseFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private lateinit var map: GoogleMap

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var locationRequest: LocationRequest

    private var googleApiClient: GoogleApiClient? = null

    private var lastLocation: Location? = null

    private var currLocationMarker: Marker? = null

    private var isLocationPermissionGranted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))

    }

    override fun onPause() {
        super.onPause()

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        getLocationPermission()

        binding.fabMyLocation.setOnClickListener {
            if (isLocationPermissionGranted) {
                buildGoogleApiClient()
                map.isMyLocationEnabled = true
            } else {
                checkLocationPermission()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        if (currLocationMarker != null) {
            currLocationMarker?.remove()
        }

        val latLng = LatLng(location.latitude, location.longitude)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM.toFloat()))
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest.apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        if (isLocationPermissionGranted) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
            )
        }
    }

    override fun onConnectionSuspended(p0: Int) = Unit

    override fun onConnectionFailed(p0: ConnectionResult) = Unit

    private fun checkLocationPermission() {
        if (!isLocationPermissionGranted) {
            val isPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            if (isPermissionRationale) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        requestPermission()
                    }
                    .create()
                    .show()
            } else {
                requestPermission()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_ACCESS_FINE_LOCATION -> {
                getLocationPermission()
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isLocationPermissionGranted) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        map.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient?.connect()
    }

    private fun getLocationPermission() {
        val permissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            isLocationPermissionGranted = true
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
        private const val DEFAULT_ZOOM = 10

        private val defaultLocation = LatLng(-6.21462, 106.84513)
    }
}

/*private var map: GoogleMap? = null

private var cameraPosition: CameraPosition? = null
private lateinit var mapFragment: SupportMapFragment

private lateinit var locationRequest: LocationRequest

private var googleApiClient: GoogleApiClient? = null

private lateinit var lastLocation: Location

private var currLocationMarker: Marker? = null

private lateinit var placesClient: PlacesClient

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

private var locationPermissionGranted = false

private var lastKnownLocation: Location? = null

private val defaultLocation = LatLng(-6.21462, 106.84513)

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (activity != null) {

        mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        *//*if (savedInstanceState != null) {
                lastKnownLocation = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
                cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
            }

            Places.initialize(requireContext(), MAPS_API_KEY)
            placesClient = Places.createClient(requireContext())

            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
*//*

        }
    }

    override fun onPause() {
        super.onPause()
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
    }

    override fun onConnected(bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest.apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
            )
        }


    }

    override fun onConnectionSuspended(p0: Int) = Unit

    override fun onConnectionFailed(p0: ConnectionResult) = Unit

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        if (currLocationMarker != null) {
            currLocationMarker?.remove()
        }

        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.apply {
            position(latLng)
            title("Current Position")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        }
        currLocationMarker = map?.addMarker(markerOptions)

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM.toFloat()))
    }

    */
/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 *//*
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
                map?.isMyLocationEnabled = true
            } else {
                checkLocationPermission()
            }
        } else {
            buildGoogleApiClient()
            map?.isMyLocationEnabled = true
        }
        *//*//*/ Add a marker in Jakarta and move the camera
        val jakarta = LatLng(-6.21462, 106.84513)
        map?.addMarker(MarkerOptions().position(jakarta).title("Marker in Jakarta"))
        map?.moveCamera(CameraUpdateFactory.newLatLng(jakarta))*//*

        *//*updateLocationUI()

        getDeviceLocation()*//*
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location Permission, please accept to us location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSION_REQUEST_ACCESS_FINE_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(requireContext()).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        googleApiClient?.connect()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //locationPermissionGranted = false
        when (requestCode) {
            PERMISSION_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //locationPermissionGranted = true
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        map?.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

        //updateLocationUI()
    }

    private fun getDeviceLocation() {
        binding.fabMyLocation.setOnClickListener {
            try {
                if (locationPermissionGranted) {
                    val locationResult = fusedLocationProviderClient.lastLocation
                    locationResult.addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            lastKnownLocation = task.result

                            if (lastKnownLocation != null) {
                                val location = LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                )

                                map?.addMarker(MarkerOptions().position(location))
                                map?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        location,
                                        DEFAULT_ZOOM.toFloat()
                                    )
                                )
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.")
                            Log.d(TAG, "Exception: %s", task.exception)
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    defaultLocation,
                                    DEFAULT_ZOOM.toFloat()
                                )
                            )
                            map?.uiSettings?.isMyLocationButtonEnabled = false
                        }
                    }
                }
            } catch (e: SecurityException) {
                Log.e(TAG, "Exception: ${e.message}", e)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }

        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Exception: ${e.message}", e)
        }

    }

    private fun getLocationPermission() {
        val permissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permissionGranted) {
            locationPermissionGranted = true
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
        private val TAG = HomeFragment::class.java.simpleName
        private const val DEFAULT_ZOOM = 10
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
} */