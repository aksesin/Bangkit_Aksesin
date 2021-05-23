package com.bangkit.aksesin.ui.util

import java.text.DecimalFormat

fun Int.toKmString(): String {
    val toDouble = this.toDouble()
    val decimalFormat = DecimalFormat("#.#")
    val toKm: Double = toDouble / 1000
    return "${decimalFormat.format(toKm)} Km"
}