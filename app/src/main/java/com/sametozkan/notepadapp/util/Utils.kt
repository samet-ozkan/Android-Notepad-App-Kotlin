package com.sametozkan.notepadapp.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun convertTimestampToString(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(date)
    }
}