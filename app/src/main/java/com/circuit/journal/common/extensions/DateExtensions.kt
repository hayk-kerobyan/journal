package com.circuit.journal.common.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

fun Date.displayedDateAndTime(): String {
    val dateFormatter = SimpleDateFormat.getDateInstance()
    val timeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
    return "${dateFormatter.format(this)} ${timeFormatter.format(this)}"
}