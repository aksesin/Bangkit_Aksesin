package com.bangkit.aksesin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bangkit.aksesin.databinding.ActivityMainBinding
import com.bangkit.aksesin.ui.base.BaseActivity
import com.bangkit.aksesin.ui.util.Constants.SPLASH_DELAY

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashDelay()
    }

    private fun splashDelay() {
        val intent = Intent(this, MapsActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(intent)
            }, SPLASH_DELAY
        )
    }
}