package com.myapplication.screens


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.myapplication.R
import com.myapplication.dialogs.CustomDialog.CustomAlertDialog


object HomeScreen {
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
                                Image(
                                    painterResource(id = getIconForScreen(screen)),
                                    contentDescription = null,
                                    modifier = Modifier.size(iconSize)
                                )
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
Image(painter = painterResource(R.drawable.ic_documentscan),contentDescription = null)
                    Text("Add your first document", textAlign = TextAlign.Center, color = Color(0xFF5E5E5E))
                }
            }
        )

        if (showCustomDialog.value) {
            Dialog(onDismissRequest = { }, properties = DialogProperties(
                dismissOnBackPress = true,dismissOnClickOutside = true)
            ) {
            CustomAlertDialog(
                onDismiss = {showCustomDialog.value=false },
                onExit = { },dismissOnBackPressed = true,dismissOnClickOutside = true,true
            )
        }
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


    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
        ExperimentalComposeUiApi::class
    )
    @Composable
    fun SearchBarSample() {
        val text = rememberSaveable { mutableStateOf("") }
        val active = rememberSaveable { mutableStateOf(false) }

        // Obtain the LocalSoftwareKeyboardController
        val keyboardController = LocalSoftwareKeyboardController.current

        Row  (
            Modifier
                .fillMaxWidth().size(80.dp)
                .semantics { isTraversalGroup = true }
                .background(Color.White)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
                    .semantics { traversalIndex = -1f },
                query = text.value, colors = SearchBarDefaults.colors(Color(0xFFF6F6F6),Color(0xFFF6F6F6)),
                onQueryChange = { newText ->
                    text.value = newText

                    // Handle the search action when the text is not empty
                    if (newText.isNotEmpty()) {
                        // Perform search action or any other relevant action here
                        // ...

                        // Clear focus and hide the keyboard
                        keyboardController?.hide()
                    }
                },
                onSearch = {
                    // Handle the search action when the search icon is clicked
                    // ...

                    // Clear focus and hide the keyboard
                    keyboardController?.hide()
                },
                active = active.value,
                onActiveChange = {
                    active.value = it
                    // Show or hide the keyboard based on the search bar's active state
                    if (it) {
                        //   keyboardController?.show()
                    } else {
                        keyboardController?.hide()
                    }
                },
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            ) {}

            Icon(painter = painterResource(R.drawable.ic_crown),contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically).padding(2.dp))
            Icon(painter = painterResource(R.drawable.ic_scanner),contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically).padding(2.dp))
        }
    }



    @Composable
    fun RecentFiles() {
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

                    },
                textAlign = TextAlign.End,
                color = Color(0xFFBF9AFE),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}