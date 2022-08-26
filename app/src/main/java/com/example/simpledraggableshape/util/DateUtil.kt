package com.example.simpledraggableshape.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun getTodayDate(): String {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.format(cal.time)
    }

    fun getDate(formattedDate: String): Date? {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.parse(formattedDate)
    }

    fun getDateString(date: Date): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun isMoreThanWeek(formattedDate: String): Boolean {
        return getDate(formattedDate)?.let { isMoreThanWeek(it) } ?: return false
    }

    fun isMoreThanWeek(date: Date): Boolean {
        val dateCalendar = Calendar.getInstance().apply { time = date }
        val weekAgoCalendar = Calendar.getInstance().apply {
            add(Calendar.DATE, -7)
        }
       return dateCalendar.before(weekAgoCalendar)
    }

}