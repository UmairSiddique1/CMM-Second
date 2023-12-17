package com.myapplication.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import com.myapplication.R


import android.graphics.Bitmap
import android.net.Uri
import android.view.Gravity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapplication.screens.CameraScreen

object CustomDialog {

    @Composable
    fun LayoutDialog(color:Color,iconRes:Int,text:String,onDismiss: () -> Unit){
        val context= LocalContext.current
        val imageUri = remember {
            mutableStateOf<Uri?>(null)
        }

        val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri.value = uri
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 10.dp).padding(vertical = 5.dp).pointerInput(Unit) {
                detectTapGestures {
                    if (text.isNotEmpty() && text == "Camera") {
                        context.startActivity(Intent(context, CameraScreen::class.java))
                    } else if (text.isNotEmpty() && text == "Gallery") {
                        launcher.launch("image/*")
                    }
                    onDismiss()
                }
            },
            elevation = 8.dp,
            backgroundColor =color
        ) {
            // Add padding inside the Card
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                // Camera icon and text aligned together
                Box(contentAlignment = Alignment.Center){
                    Row( modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center // Adjust the spacing as needed
                    ) {
                        // Increase the size of the icon by adjusting the size parameter
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)// Adjust the size as needed
                        )
                        Text(
                            text = text,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun CustomAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit,dismissOnBackPressed:Boolean,dismissOnClickOutside:Boolean,showDialog:Boolean){
if(showDialog){
    val canvasPadding=5.dp
    val context= LocalContext.current

        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
// Get the existing layout params
        val layoutParams = dialogWindowProvider.window.attributes
// Set the gravity to the bottom
        layoutParams.gravity = Gravity.BOTTOM
// Adjust the vertical position to position it just above the bottom navigation
        layoutParams.y = LocalDensity.current.run { 60.dp.toPx() }.toInt()
// Set the updated layout params back to the window
        dialogWindowProvider.window.attributes = layoutParams

        Column() {

            LayoutDialog(Color(0xFFf1e6ff),R.drawable.ic_cameraicon,"Camera", onDismiss = onDismiss)
            LayoutDialog(Color.White,R.drawable.ic_gallery,"Gallery", onDismiss = onDismiss)

            Canvas(
                modifier = Modifier
                    .height(30.dp).width(30.dp).align(Alignment.CenterHorizontally).offset(y = -canvasPadding)
            ) {
                drawPath(
                    path = Path().apply {
                        // Move to the bottom center
                        moveTo(size.width / 2f, size.height)
                        // Line to the top right
                        lineTo(size.width, 0f)
                        // Line to the top left
                        lineTo(0f, 0f)
                        // Close the path
                        close()
                    },
                    color = Color.White// Change the color as needed
                )
            }
        }
    }
    }
}
