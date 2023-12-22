package com.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapplication.R

class ToolsViewAllScreen:Screen {
    @Composable
    fun EditVewAllLayout(){
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            CustomTopBar()
           GridWithCircularIconsAndText(iconResIds = listOf(
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools,
               R.drawable.ic_tools
           ),
               texts = listOf(
                   "Edit pdf",
                   "Sign",
                   "Import doc",
                   "Eraser",
                   "Split pdf",
                   "Merge pdf",
                   "Compress pdf",
                   "Add watermark",
                   "Remove Watermark",
                   "Reorder pdf file"
               ),Color.White)

        }
    }

    @Composable
    override fun Content() {
  EditVewAllLayout()
    }
}
@Composable
fun CustomTopBar() {
    val navigator= LocalNavigator.currentOrThrow
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {navigator.push(ToolScreen)}
                ) {
                    Icon(
                     painterResource(R.drawable.ic_backarrow),
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Edit",
                    fontWeight = FontWeight.Bold,
                    color =Color.Black
                )
            }
        },
        backgroundColor = Color.White
    )
}

@Composable
fun CircularIconWithText(iconResId: Int, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFF6F6F6), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(iconResId),contentDescription = null)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, style = MaterialTheme.typography.caption, textAlign = TextAlign.Center)
    }
}

@Composable
fun GridWithCircularIconsAndText(iconResIds: List<Int>, texts: List<String>, color: Color) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(10.dp)
            .border(BorderStroke(1.dp, Color.White), RoundedCornerShape(5.dp))
            .background(color)
            .fillMaxWidth()
    ) {
        items(iconResIds.size) { index ->
            CircularIconWithText(
                iconResId = iconResIds[index],
                text = texts[index]
            )
        }
    }
}


