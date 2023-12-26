package com.myapplication

import android.os.Build
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.myapplication.documents.FetchDocuments
import com.myapplication.model.DocumentModel
import kotlinx.coroutines.delay

object SearchScreen:Screen {

    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    override fun Content() {
        SearchScreenLayout()
    }
    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    fun SearchScreenLayout(){
        val context= LocalContext.current

        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)) {
            Icon(painter = painterResource(R.drawable.ic_backarrow),contentDescription = null,
                modifier = Modifier.padding(start = 10.dp).padding(end = 10.dp).align(Alignment.Top).padding(top = 20.dp))
            SearchBar()
        }
    }

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar() {
    val context= LocalContext.current
    val searchText = remember { mutableStateOf("") }
    val documentList= remember {mutableStateListOf<DocumentModel>()}
    documentList.clear()
//
    FetchDocuments.fetchPdfFiles(context,documentList)
   val filteredSuggestions:MutableList<DocumentModel> = documentList.filter {
       it.name.contains(searchText.value,ignoreCase = true)
   }.toMutableList()

    val expanded = remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()
LaunchedEffect(Unit){
    focusRequester.requestFocus()
}
    Column {
        TextField(
            value = searchText.value,
            onValueChange = { text ->
                searchText.value = text
                expanded.value = text.isNotEmpty() && filteredSuggestions.isNotEmpty()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF6F6F6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                textColor = Color.Black
            ),
            placeholder = {
                Text("Search")
            },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(100.dp))
                .heightIn(min = 45.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        if (expanded.value && filteredSuggestions.isNotEmpty()) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Transparent), shape = RoundedCornerShape(100.dp))) {
                items(filteredSuggestions.size) { suggestion ->
                    Text(
                        text = filteredSuggestions[suggestion].name.toString(),
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                searchText.value = filteredSuggestions[suggestion].name
                                expanded.value = false // Request focus when an item is selected
                            }
                    )
                }
            }

        } else if (searchText.value.isNotEmpty() &&!expanded.value && filteredSuggestions.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                contentAlignment = Alignment.Center) {
                Text("No suggestions available", style = MaterialTheme.typography.body1)
            }
        }
    }
}
}