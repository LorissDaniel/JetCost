package it.lorisdaniel.jetcosttest.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import it.lorisdaniel.jetcosttest.model.Item
import it.lorisdaniel.jetcosttest.model.ItemAndImage

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item")
    fun getAll(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item): Long

    @Transaction
    @Query("SELECT * FROM Item")
    suspend fun getItemsAndImages(): List<ItemAndImage>

}
