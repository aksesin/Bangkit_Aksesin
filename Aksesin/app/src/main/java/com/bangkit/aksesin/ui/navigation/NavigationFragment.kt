package com.bangkit.aksesin.ui.navigation

import android.os.Bundle
import android.view.View
import com.bangkit.aksesin.databinding.FragmentNavigationBinding
import com.bangkit.aksesin.ui.base.BaseFragment

class NavigationFragment :
    BaseFragment<FragmentNavigationBinding>(FragmentNavigationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

        }
    }
}