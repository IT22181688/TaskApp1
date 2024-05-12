package com.example.taskapp1

import android.content.Context
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RemoteViews.RemoteCollectionItems
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.taskapp1.database.Todo
import com.example.taskapp1.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TodoAdapter(items:List<Todo>,repository: TodoRepository
,viewModel:MainActivityData):Adapter<TodoViewHolder>() {



     var context: Context? = null
    val items=items
    val repository=repository
    val viewModel=viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)
        context=parent.context

        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {

        return items.size

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.cbTodo.text=items.get(position).item
        holder.ivDelete.setOnClickListener{
            val isChecked = holder.cbTodo.isChecked

            if(isChecked){
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(items.get(position))
                    val data=repository.getAllTodoItems()
                    withContext(Dispatchers.Main){
                        viewModel.setData(data)
                    }
                }

            }else{
                Toast.makeText(context,"select the item to be deleted",Toast.LENGTH_LONG).show()
            }
        }

    }
}