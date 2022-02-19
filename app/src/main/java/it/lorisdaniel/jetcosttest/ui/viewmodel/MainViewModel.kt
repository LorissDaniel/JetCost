package it.lorisdaniel.jetcosttest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item
import it.lorisdaniel.jetcosttest.model.ItemAndImage
import it.lorisdaniel.jetcosttest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val bookmarks: MutableLiveData<List<ItemAndImage>> = MutableLiveData()

    fun getImages(query: String): Flow<PagingData<Item>> {
        return Pager(PagingConfig(pageSize = 10)) { repository.getPagingSource(query) }.flow
            .cachedIn(viewModelScope)
    }


    fun saveBookmark(item: Item, image: Image?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveBookmark(item, image)
        }
    }

    fun loadBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarks.postValue(repository.getBookmarks())
        }
    }

}