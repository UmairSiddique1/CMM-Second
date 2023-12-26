package com.myapplication.documents

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.myapplication.R
import com.myapplication.model.DocumentModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FetchDocuments {
    @RequiresApi(Build.VERSION_CODES.R)
    fun fetchPdfFiles(context: Context,pdfList:MutableList<DocumentModel>){
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns._ID)

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT} AND ${MediaStore.Files.FileColumns.MIME_TYPE}='application/pdf'"

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val query = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.query(
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                projection,
                selection,
                null,
                sortOrder
            )
        } else {
            context. contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                sortOrder
            )
        }

        query?.use { cursor ->
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumn)
                val dateModified = cursor.getLong(dateColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri("external"),
                    cursor.getLong(idColumn)
                )

                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateModified * 1000))

                // Load PDF icon here (you may need to implement this)

                // Fetch number of pages using AndroidPdfViewer library

                val pdfFileInfo = DocumentModel(name, uri, R.drawable.ic_gallery, date, 12)
                pdfList.add(pdfFileInfo)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    fun fetchFiles(
        context: Context,
        fileList: MutableList<DocumentModel>,
        mimeType: String,
        iconResource: Int
    ) {
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns._ID)

        // Adjust the selection based on whether a specific MIME type is provided
        val selection = if (mimeType.isNotEmpty()) {
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT} AND ${MediaStore.Files.FileColumns.MIME_TYPE}=?"
        } else {
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=${MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT}"
        }
        val selectionArgs = if (mimeType.isNotEmpty()) arrayOf(mimeType) else null

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val query = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.query(
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
        } else {
            context.contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
        }

        query?.use { cursor ->
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumn)
                val dateModified = cursor.getLong(dateColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Files.getContentUri("external"),
                    cursor.getLong(idColumn)
                )

                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateModified * 1000))

                val fileInfo = DocumentModel(name, uri, iconResource, date, 0) // 0 for page count or you can calculate it based on the file type
                fileList.add(fileInfo)
            }
        }
    }


}