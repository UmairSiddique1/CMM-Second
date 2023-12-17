package com.myapplication.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapplication.R
import com.myapplication.Utils
import com.myapplication.dialogs.CustomDialog.CustomAlertDialog
import kotlinx.coroutines.delay
class HomeScreen:Screen {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun BottomNav() {
        val screens = listOf("Home", "Docs", "", "Tools", "Settings")
        val selectedScreen = remember { mutableStateOf(screens.first()) }
        val context= LocalContext.current
        val showCustomDialog= remember { mutableStateOf(false) }

        // Create a function to show a toast
        fun showToast(screen: String) {
            Toast.makeText(context, "Clicked on $screen", Toast.LENGTH_SHORT).show()
        }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    elevation = 8.dp
                ) {
                    screens.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                val iconSize = if (screen.isEmpty()) 58.dp else 20.dp
                                if (screen.isEmpty()) {
                                    // Show your custom Composable here
                                    CustomComposable()
                                } else {
                                    Image(
                                        painterResource(id = getIconForScreen(screen)),
                                        contentDescription = null,
                                        modifier = Modifier.size(iconSize)
                                    )
                                }
                            },
                            label = {
                                // Use Row to prevent line breaks in the text
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = screen,
                                        modifier = if (screen.isEmpty()) Modifier else Modifier,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            selected = screen == selectedScreen.value,
                            onClick = {
                                selectedScreen.value = screen
                                showToast(screen)
                                if(screen.isEmpty()){
                                    showCustomDialog.value=!showCustomDialog.value
                                }
                            },
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(R.drawable.ic_documentscan), contentDescription = null)
                    Text("Add your first document", textAlign = TextAlign.Center, color = Color(0xFF5E5E5E))
                }
            }
        )

        if (showCustomDialog.value) {
            Dialog(onDismissRequest = { }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true)
            ) {
                CustomAlertDialog(
                    onDismiss = {showCustomDialog.value=false },
                    onExit = { },dismissOnBackPressed = true,dismissOnClickOutside = true,true
                )
            }
        }
    }

    @Composable
    fun CustomComposable() {
        val infiniteTransition = rememberInfiniteTransition(label = "")

        val heartSize by infiniteTransition.animateFloat(
            initialValue = 25.0f,
            targetValue = 30.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, delayMillis = 100, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        Box(
            modifier = Modifier
                .background(Color(0xFFA36DFF), shape = CircleShape)
                .padding(8.dp).size(heartSize.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(4.dp).align(Alignment.Center)
            )
        }
    }

    private fun getIconForScreen(screen: String): Int {
        return when (screen) {
            "Home" -> R.drawable.ic_home
            "Docs" -> R.drawable.ic_docs
            "" -> R.drawable.frame
            "Tools" -> R.drawable.ic_tools
            else -> R.drawable.ic_settings
        }
    }

    @Composable
    fun CircularIcon(@DrawableRes iconId: Int, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .size(60.dp)
                .background(
                    color = Color(0xFFF6F6F6), // Set the background color to white
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(32.dp).padding(2.dp)
            )
        }
    }

    @Composable
    fun RowOfCircularIcons(iconIds: List<Int>) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // Set the background color of the entire row to white
                .padding(10.dp), // Adjust the padding as needed
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(iconIds.size) { iconId ->
                CircularIcon(iconId = iconIds[iconId])
            }
        }
    }

    // Example usage:
    @Composable
    fun Tools() {
        val iconList = listOf(
            R.drawable.ic_editpdf,
            R.drawable.ic_imagetopdf,
            R.drawable.ic_scanid,
            R.drawable.ic_mergepdf,
            R.drawable.ic_mergepdf,
            R.drawable.ic_mergepdf,
            R.drawable.ic_mergepdf,
            R.drawable.ic_mergepdf
            // Add more icon resources as needed
        )

        RowOfCircularIcons(iconIds = iconList)
    }

    @Composable
    fun RecentFiles() {
        val navigator= LocalNavigator.currentOrThrow
        Row(
            modifier = Modifier
                .fillMaxWidth().background(color = Color.White).padding(10.dp)
        ) {
            Text(
                "Recent Files",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                textAlign = TextAlign.Start
            )
            Text(
                "View All",
                modifier = Modifier
                    .padding(start = 10.dp).clickable {
navigator.push(ViewAllScreen())
                    },
                textAlign = TextAlign.End,
                color = Color(0xFFBF9AFE),
                textDecoration = TextDecoration.Underline
            )
        }
    }
@Composable
    override fun Content() {
    Column {
        Row(modifier = Modifier.background(Color.White).align(Alignment.CenterHorizontally).padding(5.dp)) {
            Utils.SearchBar()
            Icon(painterResource(R.drawable.ic_crown),contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically).padding(2.dp))
            Icon(painterResource(R.drawable.ic_scanner),contentDescription = null,modifier = Modifier.align(Alignment.CenterVertically).padding(2.dp))
        }
        Tools()
        RecentFiles()
        BottomNav()
    }
    }
}