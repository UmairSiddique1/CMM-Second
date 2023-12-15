package com.myapplication.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myapplication.documents.FetchDocuments
import com.myapplication.R
import com.myapplication.model.DocumentModel

class ViewAllScreen {
    companion object{
        @RequiresApi(Build.VERSION_CODES.R)
        @Composable
        fun FetchFilesData(context: Context){
            val list= remember { mutableStateListOf<DocumentModel>() }
            FetchDocuments.fetchPdfFiles(context, list)

            LazyColumn(modifier = Modifier.fillMaxWidth().padding(10.dp).background(Color.White)) {
                items(list.size){item->
                    val name= remember { mutableStateOf(list[item].name) }
                    val date= remember { mutableStateOf(list[item].date) }
                    Card(modifier = Modifier.padding(10.dp).fillMaxWidth(), border = BorderStroke(1.dp, Color.Black)) {
                        Row {
                            Icon(painter = painterResource(R.drawable.ic_gallery),contentDescription = null, modifier = Modifier.padding(5.dp).size(20.dp))
                            Column {
                                Text(name.value, modifier = Modifier, color = Color.Black, fontWeight = FontWeight.Bold)
                                Text("12 pages | ${date.value}")
                            }
                        }
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.R)
        @Composable
        fun TopBar(context: Context) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_backarrow),
                                contentDescription = null
                            )
                            Text("All", color = Color(0xFF0D0D0D))
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_searchicon),
                            contentDescription = null
                        )
                    }
                }
                FetchFilesData(context)
            }
        }

        @Composable
        fun SearchBar(
            modifier: Modifier = Modifier
        ) {
            val searchText = remember { mutableStateOf("") }

            TextField(
                value = searchText.value,
                onValueChange = { text ->
                    searchText.value = text
                    // Add any additional logic here if needed
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF6F6F6), // #F6F6F6
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    textColor = Color.Black
                ),
                placeholder = {
                    Text("Search")
                },
                shape = RoundedCornerShape(16.dp), // Adjust the corner radius as needed
                modifier = modifier
                    .heightIn(min = 56.dp)
            )
        }



    }


}