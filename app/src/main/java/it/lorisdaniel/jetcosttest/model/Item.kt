package it.lorisdaniel.jetcosttest.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var kind: String? = null,
    var title: String? = null,
    var htmlTitle: String? = null,
    var link: String? = null,
    var displayLink: String? = null,
    var snippet: String? = null,
    var htmlSnippet: String? = null,
    var mime: String? = null,
    var fileFormat: String? = null
) {
    @Ignore
    var image: Image? = null
}