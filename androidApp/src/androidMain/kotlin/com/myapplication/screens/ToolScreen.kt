package com.myapplication.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapplication.R

object ToolScreen:Screen {
@SuppressLint("NotConstructor")
@Composable
fun ToolScreen(){
    val navigation= LocalNavigator.currentOrThrow
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        CustomToolbar()
        Text(text = "Scan", fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.padding(12.dp))
        RowWithCircularIconsAndText(
            iconResIds = listOf(
                R.drawable.ic_tools,
                R.drawable.ic_tools,
                R.drawable.ic_tools,
                R.drawable.ic_tools
            ),
            texts = listOf(
                "Document",
                "QR",
                "Passport",
                "ID card"
            ),Color(0xFFF6F6F6)
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(12.dp)){
            Text(text = "Edit", fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Left, modifier = Modifier.weight(1f))
            Text("View All", textDecoration = TextDecoration.Underline, color = Color(0xFFBF9AFE), textAlign = TextAlign.Right, modifier = Modifier.weight(1f).clickable {
                navigation.push(ToolsViewAllScreen())
            })
        }
        RowWithCircularIconsAndText(
            iconResIds = listOf(
                R.drawable.ic_tools,
                R.drawable.ic_tools,
                R.drawable.ic_tools,
                R.drawable.ic_tools
            ),
            texts = listOf(
                "Sign",
                "Split pdf",
                "Edit pdf",
                "Merge pdf"
            ),Color(0xFFF6F6F6)
        )
        Text(text = "Convert", fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.padding(12.dp))
        RowWithCircularIconsAndText(
            iconResIds = listOf(
                R.drawable.ic_tools,
                R.drawable.ic_tools,
                R.drawable.ic_tools
            ),
            texts = listOf(
                "Image to pdf",
                "Pdf to word",
                "Pdf to image"
            ),Color(0xFFF6F6F6)
        )
    }
}


@Composable
    override fun Content() {
ToolScreen()
    }
    @Composable
    fun CircularIconWithText(iconResId: Int, text: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(iconResId),contentDescription = null)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, style = MaterialTheme.typography.caption)
        }
    }

    @Composable
    fun RowWithCircularIconsAndText(iconResIds: List<Int>, texts: List<String>,color: Color) {
        Row(
            modifier = Modifier.padding(10.dp).border(BorderStroke(1.dp, Color.White), RoundedCornerShape(5.dp)).background(color)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in iconResIds.indices) {
                CircularIconWithText(
                    iconResId = iconResIds[i],
                    text = texts[i]
                )
            }
        }
    }


    @Composable
    fun CustomToolbar() {
        TopAppBar(
            title = {
                Text(
                    text = "All Tools",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            actions = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .clip(CircleShape)
                ) {
                    IconButton(
                        onClick = {
                            // Handle click on the icon
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_crown),
                            contentDescription = "Settings"
                        )
                    }
                }
            },
            backgroundColor = Color.White
        )
    }
}
