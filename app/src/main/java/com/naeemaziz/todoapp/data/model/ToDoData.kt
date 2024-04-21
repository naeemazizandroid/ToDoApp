package com.naeemaziz.todoapp.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority: Priority,
    var description:String
) : Parcelable
