package com.horizon.lifeos.util

import java.text.DecimalFormat

object CurrencyFormatter {
    fun rialsToTomansFormatted(rials: Long): String {
        val tomans = rials / 10
        val formatter = DecimalFormat("#,###")
        val formatted = formatter.format(tomans)
        return "${PersianDateHelper.toPersianDigits(formatted)} تومان"
    }
}
