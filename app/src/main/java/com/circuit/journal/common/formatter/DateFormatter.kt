package com.circuit.journal.common.formatter

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

fun getFormattedDateAndTime(date: Date?): String {
    date ?: return ""
    val dateFormatter = SimpleDateFormat.getDateInstance()
    val timeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
    return "${dateFormatter.format(date)} ${timeFormatter.format(date)}"
}