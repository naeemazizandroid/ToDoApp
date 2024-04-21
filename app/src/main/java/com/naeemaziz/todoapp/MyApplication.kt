package com.naeemaziz.todoapp

import android.app.Application
import com.naeemaziz.todoapp.data.ToDoDatabase
import com.naeemaziz.todoapp.data.repository.ToDoRepository

class MyApplication: Application() {

    private val database by lazy { ToDoDatabase.getDatabase(this) }
    val repository by lazy { ToDoRepository(database.toDoDao()) }
}