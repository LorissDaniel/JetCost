package it.lorisdaniel.jetcosttest.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.lorisdaniel.jetcosttest.R
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item

class SearchResultAdapterOld(
    private val context: Context,
    private val searchResults: ArrayList<Item>,
    private val onSaveBookmarkCallback: (item: Item, image: Image?) -> Unit
) :
    RecyclerView.Adapter<SearchResultAdapterOld.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imagePreview)
        val title: TextView = view.findViewById(R.id.imageTitle)
        val bookmark: ImageView = view.findViewById(R.id.bookmark_button)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_searchresult, viewGroup, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = searchResults.size

    fun addData(searchResultToAdd: List<Item>) {
        searchResults.addAll(searchResultToAdd)
        notifyItemRangeInserted(searchResults.size, searchResultToAdd.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(searchResultToAdd: List<Item>) {
        searchResults.clear()
        searchResults.addAll(searchResultToAdd)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = searchResults[position]
        Glide.with(context)
            .load(item.image?.thumbnailLink)
            .fitCenter()
            .into(holder.image)
        holder.title.text = item.title
        holder.bookmark.setOnClickListener {
            onSaveBookmarkCallback(item, item.image)
        }
    }

}