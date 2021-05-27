package com.bangkit.aksesin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aksesin.core.domain.model.Info
import com.bangkit.aksesin.databinding.ItemCreditsBinding

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    private var listCredit = ArrayList<Info>()

    fun setInfo(info: List<Info>?) {
        if (info == null) return
        this.listCredit.clear()
        this.listCredit.addAll(info)
    }

    class InfoViewHolder(private val binding: ItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info) {
            with(binding) {
                tvTitleLicence.text = info.title
                tvLink.text = info.link
                tvDesc.text = info.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemCreditsBinding = ItemCreditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(itemCreditsBinding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val info = listCredit[position]
        holder.bind(info)
    }

    override fun getItemCount(): Int = listCredit.size

}