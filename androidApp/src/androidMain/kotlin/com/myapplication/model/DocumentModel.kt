package com.myapplication.model

import android.graphics.drawable.Drawable
import android.net.Uri

data class DocumentModel(
    val name: String,
    val uri: Uri,
    val icon: Int,
    val date: String,
    val pageCount: Int
)
