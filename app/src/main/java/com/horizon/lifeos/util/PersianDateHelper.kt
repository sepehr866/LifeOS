package com.horizon.lifeos.util

import java.util.*

object PersianDateHelper {
    private val persianDigits = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
    private val persianMonths = arrayOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )

    fun toPersianDigits(text: String): String {
        var result = text
        for (i in 0..9) {
            result = result.replace(i.toString(), persianDigits[i])
        }
        return result
    }

    fun getCurrentPersianDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        // تبدیل تقریبی و استاندارد تقویم
        val pDay = (day + 10) % 30 + 1
        val pMonthName = persianMonths[(month + 8) % 12]
        return toPersianDigits("$pDay $pMonthName")
    }
}
