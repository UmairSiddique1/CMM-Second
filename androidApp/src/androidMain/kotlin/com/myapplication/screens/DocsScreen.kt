package com.myapplication.screens

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.myapplication.R
import com.myapplication.documents.FetchDocuments
import com.myapplication.model.DocumentModel

class DocsScreen:Screen {
    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    override fun Content() {
DocsScreenLayout()
    }

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun DocsScreenLayout(){
    val selectedOption = remember { mutableStateOf("All") }
    val selectedMimeType = remember { mutableStateOf("") }
    val context = LocalContext.current
    // Create a focus requester to handle keyboard focus
    val focusRequester = remember { FocusRequester() }
    val searchQuery = remember { mutableStateOf("") }
    val list= remember {mutableStateListOf<DocumentModel>()}
    val filteredList = list.filter {
        // Filter logic: Adjust according to how you want to match the query
        it.name.contains(searchQuery.value, ignoreCase = true)
    }


    LaunchedEffect(selectedMimeType.value) {
        list.clear()
        FetchDocuments.fetchFiles(context, list, mimeType = selectedMimeType.value, R.drawable.ic_pdf)
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White)){
        Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
            SearchBar(searchQuery,focusRequester)
            Icon(painter = painterResource(R.drawable.ic_grid),contentDescription = null)
            Icon(painter = painterResource(R.drawable.ic_crown),contentDescription = null)
        }


        Row(modifier = Modifier.background(Color.White)) {
            FilterLayout("All", selectedOption.value) { selectedOption.value = "All"
                selectedMimeType.value = ""}
            FilterLayout("PDF", selectedOption.value) { selectedOption .value= "PDF"
                selectedMimeType.value = "application/pdf"}
            FilterLayout("Word", selectedOption.value) { selectedOption .value= "Word"
                selectedMimeType.value = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}
            FilterLayout("Excel", selectedOption.value) { selectedOption.value = "Excel"
                selectedMimeType.value = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}
            FilterLayout("Text", selectedOption.value) { selectedOption .value= "Text"
                selectedMimeType.value = "text/plain"}
        }
if(searchQuery.value.isEmpty()){
    ViewAllScreen.FilesLayout(list)
}
else{
    ViewAllScreen.FilesLayout(filteredList.toMutableList())
}
    }
}

    @Composable
    fun FilterLayout(text: String, selectedOption: String, onSelect: () -> Unit) {
        val isSelected = text == selectedOption
        val backgroundColor = if (isSelected) Color(0xFFF1E6FF) else Color(0xFFF6F6F6)
        val borderColor = if (isSelected) Color(0xFFA36DFF) else Color.Black

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(50.dp)) // Clip the Box to have rounded corners
                .background(backgroundColor) // Set the background color after clipping
                .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(50.dp))
                .clickable { onSelect() }
                .width(50.dp)
        ) {
            Text(text = text, modifier = Modifier.padding(5.dp))
        }
    }


    @Composable
    fun SearchBar(searchQuery: MutableState<String>, focusRequester: FocusRequester) {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 100.dp)
                .heightIn(min = 40.dp)
                .background(Color(0xFFF6F6F6), shape = RoundedCornerShape(100.dp)) // Apply background color with rounded corners
                .border(1.dp, Color.Black, shape = RoundedCornerShape(100.dp)), // Apply border with rounded corners

            placeholder = { Text("Search") }
        )
    }


}
