package it.lorisdaniel.jetcosttest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.lorisdaniel.jetcosttest.model.Image

@Dao
interface ImageDao {

    @Query("SELECT * FROM Image WHERE itemId = :itemId")
    fun getImagesFromItem(itemId: Long): List<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image): Long

}
