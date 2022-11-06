package com.thaa.notesapptest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.databinding.RowItemsNoteBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    //val yg mengambil data dari Notes
    val listNotes = ArrayList<Notes>()

    //mengambil data dari RowItemNotes yang sudah kita buat.
    class ViewHolder(val binding: RowItemsNoteBinding): RecyclerView.ViewHolder(binding.root)

    //mengisi data ke RowItem menggunalan layout inflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        RowItemsNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    //menampilkan data sesuai dengan view-nya
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listNotes[position]
        holder.binding.apply {
            mNotes = data
            executePendingBindings()
        }
    }

    //menampilkan item sesuai dengan datanya
    override fun getItemCount() = listNotes.size

    //fun yang mengatur tampilan recylerView ketika menerima perubahan data.
    fun setData(data: List<Notes>?) {
        if (data == null) return
        val notesDiffutil = DiffCallback(listNotes, data)
        val notesDiffutilResult = DiffUtil.calculateDiff(notesDiffutil)
        listNotes.clear()
        listNotes.addAll(data)
        notesDiffutilResult.dispatchUpdatesTo(this)
    }
}