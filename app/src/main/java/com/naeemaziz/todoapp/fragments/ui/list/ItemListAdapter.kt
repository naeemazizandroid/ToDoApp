package com.naeemaziz.todoapp.fragments.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naeemaziz.todoapp.data.model.ToDoData
import com.naeemaziz.todoapp.databinding.RowLayoutBinding

class ItemListAdapter: RecyclerView.Adapter<ItemListAdapter.MyViewHolder> (){

    var dataList = emptyList<ToDoData>()
    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: ToDoData){
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}