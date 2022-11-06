package com.thaa.notesapptest.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thaa.notesapptest.R
import com.thaa.notesapptest.data.entity.Notes
import com.thaa.notesapptest.databinding.FragmentUpdateBinding
import com.thaa.notesapptest.utils.HelperFunctions.dateTodaySimpleFormat
import com.thaa.notesapptest.utils.HelperFunctions.parseToPriority
import com.thaa.notesapptest.utils.HelperFunctions.spinnerListener
import com.thaa.notesapptest.utils.HelperFunctions.verifyDataFromUser
import com.thaa.notesapptest.viewmodel.NotesViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val updateViewModel by viewModels<NotesViewModel>()

    private val navArgs by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.safeArgs = navArgs

        setHasOptionsMenu(true)

//        binding.apply {
//            toolbarUpdate.setupActionBar(this@UpdateFragment, R.id.updateFragment)
//            spinnerPrioritiesUpdate.onItemSelectedListener =
//                spinnerListener(context, binding.priorityIndicator)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        val item = menu.findItem(R.id.menu_save)
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        binding.apply {
            val title = edtTitleUpdate.text.toString()
            val desc = edtDescriptionUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()

            val validation = verifyDataFromUser(title, desc)
            if (validation) {
                val updatedItem = Notes(
                    navArgs.currentItem.id,
                    title,
                    parseToPriority(context, priority),
                    desc,
                    getString(R.string.txt_edited_on) + "\n" + dateTodaySimpleFormat()
                )
                updateViewModel.updateData(updatedItem)
                Toast.makeText(context, "Successfully updated.", Toast.LENGTH_SHORT)
                    .show()
                val action = UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(updatedItem)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please input title and description.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}