package com.example.simpledraggableshape.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"

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
        val dateCalendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR , 0)
            set(Calendar.MINUTE , 0)
            set(Calendar.SECOND , 0)
            time = date
        }
        val weekAgoCalendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR , 0)
            set(Calendar.MINUTE , 0)
            set(Calendar.SECOND , 0)
            add(Calendar.DATE, -7)
        }
       return dateCalendar.before(weekAgoCalendar)
    }

}