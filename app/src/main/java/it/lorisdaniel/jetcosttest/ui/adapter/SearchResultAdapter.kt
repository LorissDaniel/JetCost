package it.lorisdaniel.jetcosttest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.lorisdaniel.jetcosttest.databinding.ItemSearchresultBinding
import it.lorisdaniel.jetcosttest.model.Image
import it.lorisdaniel.jetcosttest.model.Item

class SearchResultAdapter(private val onSaveBookmarkCallback: (item: Item, image: Image?) -> Unit) :
    PagingDataAdapter<Item, SearchResultAdapter.PassengersViewHolder>(PassengersComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersViewHolder {
        return PassengersViewHolder(
            ItemSearchresultBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindSearchResult(it) }
    }

    inner class PassengersViewHolder(private val binding: ItemSearchresultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSearchResult(item: Item) = with(binding) {
            Glide.with(binding.root.context)
                .load(item.image?.thumbnailLink)
                .fitCenter()
                .into(imagePreview)
            imageTitle.text = item.title
            bookmarkButton.setOnClickListener {
                onSaveBookmarkCallback(item, item.image)
            }
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}