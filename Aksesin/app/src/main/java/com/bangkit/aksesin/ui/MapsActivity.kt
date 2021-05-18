package com.bangkit.aksesin.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bangkit.aksesin.R
import com.bangkit.aksesin.databinding.ActivityMapsBinding
import com.bangkit.aksesin.ui.base.BaseActivity

class MapsActivity : BaseActivity<ActivityMapsBinding>() {

    private lateinit var navController: NavController

    override fun getViewBinding() = ActivityMapsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.mapsNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}