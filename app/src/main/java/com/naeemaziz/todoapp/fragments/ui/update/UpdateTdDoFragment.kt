package com.naeemaziz.todoapp.fragments.ui.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.naeemaziz.todoapp.MyApplication
import com.naeemaziz.todoapp.R
import com.naeemaziz.todoapp.data.model.ToDoData
import com.naeemaziz.todoapp.data.viewmodel.ToDoViewModel
import com.naeemaziz.todoapp.data.viewmodel.ToDoViewModelFactory

import com.naeemaziz.todoapp.databinding.FragmentUpdateTodoBinding
import com.naeemaziz.todoapp.fragments.ui.SharedViewModel
import com.naeemaziz.todoapp.fragments.ui.SharedViewModelFactory
import com.naeemaziz.todoapp.fragments.ui.list.ItemListAdapter


class UpdateTdDoFragment : Fragment() {


    val args: UpdateTdDoFragmentArgs by navArgs()
    private var _binding: FragmentUpdateTodoBinding? = null
    private val binding get() = _binding!!
    private val adapter: ItemListAdapter by lazy { ItemListAdapter() }

    private val mToDoViewModel: ToDoViewModel by viewModels {
        ToDoViewModelFactory((requireActivity().applicationContext as MyApplication).repository)
    }

    private val mSharedViewModel: SharedViewModel by viewModels {
        SharedViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Data binding
        _binding = FragmentUpdateTodoBinding.inflate(inflater, container, false)
        binding.args = args

        // Spinner Item Selected Listener
        binding.updatePrioritiesSP.onItemSelectedListener = mSharedViewModel.listener
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_save -> updateItem()
                    R.id.menu_delete -> confirmItemRemoval()
                    android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateTdDoFragment_to_listTodoFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.updateTitleET.text.toString()
        val description = binding.updateDescriptioET.text.toString()
        val getPriority = binding.updatePrioritiesSP.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            // Navigate back
            findNavController().navigate(R.id.action_updateTdDoFragment_to_listTodoFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}