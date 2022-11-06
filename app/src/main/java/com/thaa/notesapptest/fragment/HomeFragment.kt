package com.thaa.notesapptest.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thaa.notesapptest.R
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.databinding.FragmentHomeBinding
import com.thaa.notesapptest.home.HomeAdapter
import com.thaa.notesapptest.home.SwipeToDelete
import com.thaa.notesapptest.utils.ExtensionsFunction.observeOnce
import com.thaa.notesapptest.utils.HelperFunctions
import com.thaa.notesapptest.utils.HelperFunctions.checkIfDatabaseEmpty
import com.thaa.notesapptest.viewmodel.NotesViewModel
//                               untuk melakukan pencarian notes
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeAdapter by lazy { HomeAdapter() }

    private val homeViewModel by viewModels<NotesViewModel>()

    private var currentData = emptyList<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* menginisialisasi variable binding kita dengan fragment home binding
        agar dapat memanggil id xml kita kedalam logicnya. */
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*menginisialisasikan data binding agar bisa memanggil fun dari
        object HelperFunctions, yang mana berguna untuk mengecek apakah
        databasenya kosong atau tidak. */
        binding.lifecycleOwner = this
        binding.mHelperFunctions = HelperFunctions


        setHasOptionsMenu(true)
//        binding.toolbarHome.setupActionBar(this, null)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        binding.rvNotes.apply {
            //mendapatkan semua data
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                //mengecheck apakah datanya kosong atau tidak
                checkIfDatabaseEmpty(it)
                homeAdapter.setData(it)
                scheduleLayoutAnimation()
                currentData = it
            }
            //untuk mengatur rv jika mengalami perubahan data
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            swipeToDelete(this)
        }
    }

    //fun untuk membuat menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)

        /*jika icon search diklik maka ia lngsng berubah menjadi searchview
        dan bisa menerima inputan data*/
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    //ketika diklik akan menampilkan data yg dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllData()
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority().observe(this) {
                //mengatur tampilan rv jika ada data yg berubah
                homeAdapter.setData(it)
            }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority().observe(this) {
                homeAdapter.setData(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        //jika datanya kosong maka akan menjalankan code ini
        if (currentData.isEmpty()) {
            AlertDialog.Builder(requireContext()).setTitle("No Data Found.")
                .setMessage("No data found for deletes.")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            //jika ada datanya maka akan menjalankan code ini
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Everything?")
                .setMessage("Are you sure want to remove everything?")
                .setPositiveButton("Yes") { _, _ ->
                    //fun yg menghapus data
                    homeViewModel.deleteAllData()
                    Toast.makeText(
                        requireContext(),
                        "Successfully Removed Everything",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = homeAdapter.listNotes[viewHolder.adapterPosition]
                //Delete Item
                homeViewModel.deleteData(deletedItem)
                homeAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                //retore deleted item
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    //fun untuk merestore data yg sudah di delete
    private fun restoreDeletedData(view: View, deletedItem: Notes) {
        Snackbar.make(view, "Deleted: '${deletedItem.title}'", Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(view.context, R.color.black))
            .setAction("Undo") {
                homeViewModel.insertData(deletedItem)
            }
            .setActionTextColor(ContextCompat.getColor(view.context, R.color.black))
            .show()
    }

    //jika datanya tidak null / ada isinya maka ia akan menjalankan fun searchNote
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    //jika datanya tidak null / ada isinya maka ia akan menjalankan fun searchNote
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query"

        homeViewModel.searchNoteByQuery(searchQuery).observeOnce(this) {
            homeAdapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}