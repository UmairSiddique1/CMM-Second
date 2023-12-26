package com.myapplication.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.myapplication.documents.FetchDocuments
import com.myapplication.R
import com.myapplication.Utils
import com.myapplication.model.DocumentModel

class ViewAllScreen:Screen {
    companion object{
        @RequiresApi(Build.VERSION_CODES.R)
        @Composable
        fun FetchFilesData(context: Context){
            val list= remember { mutableStateListOf<DocumentModel>() }
            FetchDocuments.fetchPdfFiles(context, list)


            FilesLayout(list)
        }


        @RequiresApi(Build.VERSION_CODES.R)
        @Composable
        fun TopBar(context: Context) {
            val navigator= LocalNavigator.currentOrThrow
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
                                contentDescription = null, modifier = Modifier.clickable {
                                    navigator.push(HomeScreen())
                                }
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
        fun FilesLayout(list:MutableList<DocumentModel>){
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(5.dp).background(Color.White)) {
                items(list.size){item->
                    val name= remember { mutableStateOf(list[item].name) }
                    val date= remember { mutableStateOf(list[item].date) }
                    Card(modifier = Modifier.padding(5.dp).padding(top = 5.dp).padding(bottom = 5.dp).fillMaxWidth()) {
                        Row() {
                            Icon(painter = painterResource(R.drawable.ic_pdf),contentDescription = null, modifier = Modifier.padding(5.dp).size(20.dp).align(Alignment.CenterVertically))
                            Column {
                                Text(name.value, modifier = Modifier, color = Color.Black, fontWeight = FontWeight.Bold)
                                Text("12 pages | ${date.value}")
                            }
                        }
                    }
                }
            }
        }
    }
@RequiresApi(Build.VERSION_CODES.R)
@Composable
    override fun Content() {
    val context= LocalContext.current
 TopBar(context)
    }
}