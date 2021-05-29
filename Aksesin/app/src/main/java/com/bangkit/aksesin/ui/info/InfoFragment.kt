package com.bangkit.aksesin.ui.info

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.aksesin.databinding.FragmentInfoBinding
import com.bangkit.aksesin.ui.adapter.InfoAdapter
import com.bangkit.aksesin.ui.base.BaseFragment

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[InfoViewModel::class.java]
            val info = viewModel.getInfoData()

            val infoAdapter = InfoAdapter()
            infoAdapter.setInfo(info)

            with(binding.rvInfo) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = infoAdapter
            }
        }
    }
}