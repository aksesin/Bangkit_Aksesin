package com.bangkit.aksesin.ui.adapter

import com.bangkit.aksesin.R
import com.bangkit.aksesin.core.domain.model.Info
import com.bangkit.aksesin.databinding.ItemCreditsBinding

class InfoAdapter : BaseAdapter<Info>(R.layout.item_credits) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val info = data[position]
        val binding = ItemCreditsBinding.bind(holder.itemView)
        with(binding) {
            tvTitleLicence.text = info.title
            tvLink.text = info.link
            tvDesc.text = info.description
        }
    }
}