package com.bangkit.aksesin.ui.info

import androidx.lifecycle.ViewModel
import com.bangkit.aksesin.core.data.source.local.InfoData
import com.bangkit.aksesin.core.domain.model.Info

class InfoViewModel : ViewModel() {

    fun getInfoData(): List<Info> = InfoData.generateInfoCredits()
}