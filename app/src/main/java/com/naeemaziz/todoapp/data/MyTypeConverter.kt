package com.naeemaziz.todoapp.data

import androidx.room.TypeConverter
import com.naeemaziz.todoapp.data.model.Priority

class MyTypeConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(str: String): Priority {
        return Priority.valueOf(str)
    }
}