package com.thaa.notesapptest.home

import androidx.recyclerview.widget.DiffUtil
import com.thaa.notesapptest.data.entity.Notes

/* DiffCallback digunakan untuk memudahkan dalam mengupdate RecyclerView kita
berdasarkan perubahan pada dataset yang hendak kita tampilkan, tanpa harus
menggunakan method notifyDataSetChanged. */
class DiffCallback(private val oldList: List<Notes>, private val newList: List<Notes>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = oldList[newItemPosition]
        return oldData.id == newData.id
                && oldData.title == newData.title
                && oldData.description == newData.description
                && oldData.priority == newData.priority
    }

}
