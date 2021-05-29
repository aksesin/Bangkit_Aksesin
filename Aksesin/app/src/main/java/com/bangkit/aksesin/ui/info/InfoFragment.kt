package com.bangkit.aksesin.ui.info

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.aksesin.databinding.FragmentInfoBinding
import com.bangkit.aksesin.ui.adapter.InfoAdapter
import com.bangkit.aksesin.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    private val viewModel: InfoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val info = viewModel.getInfoData()

            val infoAdapter = InfoAdapter()
            infoAdapter.setData(info)

            with(binding.rvInfo) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = infoAdapter
            }
        }
    }
}