package it.lorisdaniel.jetcosttest.model

import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("itemId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Image(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var contextLink: String? = null,
    var height: Int? = null,
    var width: Int? = null,
    var byteSize: Int? = null,
    var thumbnailLink: String? = null,
    var thumbnailHeight: Int? = null,
    var thumbnailWidth: Int? = null,
    @ColumnInfo(index = true)
    var itemId: Long? = null
)