package com.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.model.DocumentModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchBarViewModel:ViewModel() {
    private val _searchText=MutableStateFlow("")
    val searchText=_searchText.asStateFlow()

    private val _isSearching= MutableStateFlow(false)
    val isSearching=_isSearching.asStateFlow()
    private val _documents=MutableStateFlow(listOf<DocumentModel>())
val documents=searchText.combine(_documents){text,documents->
if(text.isBlank()){
    documents
}
    else{
        documents.filter {
        it.doesMatchSearchQuery(text)
        }
    }
}
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _documents.value
    )
    fun onSearchTextChange(text:String){
        _searchText.value=text
    }
}