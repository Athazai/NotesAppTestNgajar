package com.thaa.notesapptest.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thaa.notesapptest.R
import com.thaa.notesapptest.databinding.FragmentDetailBinding
import com.thaa.notesapptest.viewmodel.NotesViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val navArgs by navArgs<DetailFragmentArgs>()

    private val detailViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.safeArgs = navArgs
//        binding.toolbarDetail.setupActionBar(this, R.id.detailFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    //fun yg menampilkan data sesuai filter yg dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_update -> {
                val action = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(
                    navArgs.currentItem
                )
                findNavController().navigate(action)
            }
            R.id.menu_delete -> deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    //fun untuk memberikan pertanyaan persetujuan menghapus data ketika diklik
    private fun deleteItem() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete '${navArgs.currentItem.title}'?")
            .setMessage("Are you sure want to remove '${navArgs.currentItem.title}'?")
            .setPositiveButton("Yes") { _, _ ->
                detailViewModel.deleteData(navArgs.currentItem)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: ${navArgs.currentItem.title}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

    //callback siklus proses terakhir yang diterima aktivitas.
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}