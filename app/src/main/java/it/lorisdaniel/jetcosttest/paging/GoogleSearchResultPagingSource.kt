package it.lorisdaniel.jetcosttest.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import it.lorisdaniel.jetcosttest.api.GoogleSearch
import it.lorisdaniel.jetcosttest.model.Item
import retrofit2.HttpException
import java.io.IOException

class GoogleSearchResultPagingSource(
    private val googleSearchApi: GoogleSearch,
    private val query: String
) :
    PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 0
        return try {
            val startIndex = (page * 10) + 1
            val response = googleSearchApi.getImages(query, startIndex).items
            LoadResult.Page(
                response, prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}