package com.naeemaziz.todoapp.fragments.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.naeemaziz.todoapp.MyApplication
import com.naeemaziz.todoapp.R
import com.naeemaziz.todoapp.data.model.ToDoData
import com.naeemaziz.todoapp.data.viewmodel.ToDoViewModel
import com.naeemaziz.todoapp.data.viewmodel.ToDoViewModelFactory
import com.naeemaziz.todoapp.databinding.FragmentAddTodoBinding
import com.naeemaziz.todoapp.fragments.ui.SharedViewModel
import com.naeemaziz.todoapp.fragments.ui.SharedViewModelFactory


class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

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
        // Inflate the layout for this fragment
        _binding =  FragmentAddTodoBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.prioritiesSP.onItemSelectedListener = mSharedViewModel.listener
        return binding.root
    }
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         val menuHost: MenuHost = requireActivity()
         menuHost.addMenuProvider(object : MenuProvider {
             override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                 menuInflater.inflate(R.menu.add_fragment_menu, menu)
             }

             override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                 if (menuItem.itemId == R.id.menu_add) {
                     insertDataToDataBase()
                 } else if (menuItem.itemId == android.R.id.home) {
                     requireActivity().onBackPressedDispatcher.onBackPressed()
                 }
                 return true
             }
         }, viewLifecycleOwner, Lifecycle.State.RESUMED)
     }

    private fun insertDataToDataBase() {

        val mTitle = binding.titleET.text.toString()
        val mPriority = binding.prioritiesSP.selectedItem.toString()
        val mDescription = binding.descriptioET.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle,mDescription)

        if(validation){
            val newData = ToDoData(0, mTitle, mSharedViewModel.parsePriority(mPriority),mDescription)
            try{
                mToDoViewModel.insertData(newData)
            }catch (e:Exception){
            }

            Toast.makeText(requireContext(),"SuccessFully Added!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTodoFragment_to_listTodoFragment)
        }else{
            Toast.makeText(requireContext(),"Plz fill out all details!", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}