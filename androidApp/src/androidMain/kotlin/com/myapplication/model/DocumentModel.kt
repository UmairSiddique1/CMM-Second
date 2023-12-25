package com.myapplication.model

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModel

data class DocumentModel(
    val name: String,
    val uri: Uri,
    val icon: Int,
    val date: String,
    val pageCount: Int
){
    fun doesMatchSearchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

