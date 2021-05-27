package com.bangkit.aksesin.core.domain.model

import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {

    fun getInfoData(): List<Info> = InfoData.generateInfoCredits()
}