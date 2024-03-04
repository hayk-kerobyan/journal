package com.circuit.journal.common.extensions

import android.content.Context
import android.content.Intent
import android.text.Html
import androidx.core.content.ContextCompat


fun Context.browseHtml(html: String, title:String): Boolean {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/html"
        putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml(html, Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV))
        putExtra(Intent.EXTRA_TEXT, html)
    }
    return try {
        val chooser = Intent.createChooser(sendIntent, title)
        ContextCompat.startActivity(this, chooser, null)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}