package com.myapplication

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.lang.reflect.Modifier


object Utils {
    @RequiresApi(Build.VERSION_CODES.P)
    @Composable
    fun RequestContentPermission(onImageSelectedComposable: @Composable (androidx.compose.ui.Modifier) -> Unit) {
        val imageUri = remember {
            mutableStateOf<Uri?>(null)
        }
        val context = LocalContext.current

        val bitmap =  remember {
            mutableStateOf<Bitmap?>(null)
        }

        val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri.value = uri
        }
        Column() {
            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "Pick image")
            }
            onImageSelectedComposable(
                androidx.compose.ui.Modifier.clickable {
                }
            )

            imageUri.let {
                if (Build.VERSION.SDK_INT < 28) {

                    val source=ImageDecoder
                        .createSource(context.contentResolver, it.value!!)
                    bitmap.value = ImageDecoder.decodeBitmap(source)

                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it.value!!)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let {  btm ->
                    Image(bitmap = btm.asImageBitmap(),
                        contentDescription =null,
                        modifier = androidx.compose.ui.Modifier.size(400.dp))
                }
            }

        }
    }

}