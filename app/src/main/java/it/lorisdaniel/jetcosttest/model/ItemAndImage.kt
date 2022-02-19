package it.lorisdaniel.jetcosttest.model

import androidx.room.Embedded
import androidx.room.Relation

data class ItemAndImage(
    @Embedded val item: Item,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val image: Image
)