package it.lorisdaniel.jetcosttest.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import it.lorisdaniel.jetcosttest.api.GoogleSearch
import it.lorisdaniel.jetcosttest.database.AppDatabase
import it.lorisdaniel.jetcosttest.dependency.repositoryModule
import it.lorisdaniel.jetcosttest.model.GoogleSearchResponse
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item
import it.lorisdaniel.jetcosttest.model.ItemAndImage
import it.lorisdaniel.jetcosttest.paging.GoogleSearchResultPagingSource

class Repository(application: Application, private val googleSearch: GoogleSearch) {

    private var itemDao = AppDatabase.getAppDataBase(application)!!.itemDao()
    private var imageDao = AppDatabase.getAppDataBase(application)!!.imageDao()

    suspend fun saveBookmark(item: Item, image: Image?) {
        val itemId = itemDao.insertItem(item)
        image?.let {
            it.itemId = itemId
            imageDao.insertImage(it)
        }
    }

    suspend fun getBookmarks(): List<ItemAndImage> {
        return itemDao.getItemsAndImages()
    }

    fun getPagingSource(query: String): GoogleSearchResultPagingSource {
        return GoogleSearchResultPagingSource(googleSearch, query)
    }

    suspend fun deleteBookmark(item: ItemAndImage) {
        itemDao.deleteItem(item.item)
    }
}