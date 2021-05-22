package com.bangkit.aksesin.ui.adapter

import com.bangkit.aksesin.R
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.databinding.ItemLocationBinding

class SearchPlacesAdapter : BaseAdapter<Place>(R.layout.item_location) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val place = data[position]
        val binding = ItemLocationBinding.bind(holder.itemView)
        with(binding) {
            tvTitleLocation.text = place.name
            tvDistance.text = place.distanceMeters.toString()
            tvDescLocation.text = place.address

            root.setOnClickListener {
                onItemClickListener?.invoke(place)
            }
        }
    }
}