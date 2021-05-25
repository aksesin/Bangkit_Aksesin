package com.bangkit.aksesin.ui.adapter

import com.bangkit.aksesin.R
import com.bangkit.aksesin.core.domain.model.Place
import com.bangkit.aksesin.databinding.ItemLocationBinding
import com.bangkit.aksesin.ui.util.toKmString

class SearchPlacesAdapter : BaseAdapter<Place>(R.layout.item_location) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val place = data[position]
        val binding = ItemLocationBinding.bind(holder.itemView)
        with(binding) {
            tvTitleLocation.text = place.locationName
            tvDistance.text = place.distanceMeters.toKmString()
            tvDescLocation.text = place.address

            root.setOnClickListener {
                onItemClickListener?.invoke(place)
            }
        }
    }
}