package it.lorisdaniel.jetcosttest.ui.bookmarks

import androidx.recyclerview.widget.DiffUtil
import it.lorisdaniel.jetcosttest.model.ItemAndImage


class BookmarksDiffUtil(
    oldEmployeeList: List<ItemAndImage>,
    newEmployeeList: List<ItemAndImage>
) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<ItemAndImage> = oldEmployeeList
    private val mNewEmployeeList: List<ItemAndImage> = newEmployeeList

    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].item.id === mNewEmployeeList[newItemPosition].item.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee: ItemAndImage = mOldEmployeeList[oldItemPosition]
        val newEmployee: ItemAndImage = mNewEmployeeList[newItemPosition]
        return oldEmployee.item.link.equals(newEmployee.item.link)
    }

}