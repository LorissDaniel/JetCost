package it.lorisdaniel.jetcosttest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.lorisdaniel.jetcosttest.R
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item
import it.lorisdaniel.jetcosttest.model.ItemAndImage
import it.lorisdaniel.jetcosttest.ui.bookmarks.BookmarksDiffUtil


class BookmarksAdapter(
    private val context: Context,
    private val bookmarks: ArrayList<ItemAndImage>,
    private val onDeleteBookmark: (item: ItemAndImage) -> Unit
) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imagePreview)
        val title: TextView = view.findViewById(R.id.imageTitle)
        val delete: ImageView = view.findViewById(R.id.delete_bookmark)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_bookmark, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = bookmarks.size

    fun updateData(searchResultToAdd: List<ItemAndImage>) {
        val diffCallback = BookmarksDiffUtil(bookmarks, searchResultToAdd)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        bookmarks.clear()
        bookmarks.addAll(searchResultToAdd)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        Glide.with(context)
            .load(bookmark.image.thumbnailLink)
            .fitCenter()
            .into(holder.image)
        holder.title.text = bookmark.item.title
        holder.delete.setOnClickListener {
            onDeleteBookmark(bookmark)
            val index = bookmarks.indexOf(bookmark)
            bookmarks.removeAt(index)
            notifyItemRemoved(index)
        }
    }

}