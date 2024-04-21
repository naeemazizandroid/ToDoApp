package com.naeemaziz.todoapp.data.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.naeemaziz.todoapp.data.model.ToDoData
import com.naeemaziz.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(private val mrepository: ToDoRepository): ViewModel() {

    val getAllData: LiveData<List<ToDoData>> = mrepository.getAllData
    val sortByHighPriority: LiveData<List<ToDoData>> = mrepository.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> = mrepository.sortByLowPriority
    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            mrepository.insertData(toDoData)
        }
    }

    fun updateData(updatedItem: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            mrepository.updateData(updatedItem)
        }
    }

    fun deleteItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            mrepository.deleteItem(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            mrepository.deleteAll()
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return mrepository.searchDatabase(searchQuery)
    }
}

class ToDoViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java))
            return ToDoViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}