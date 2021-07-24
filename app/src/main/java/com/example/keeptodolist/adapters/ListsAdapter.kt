package com.example.keeptodolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.utilities.HandleListOnClick
import com.example.keeptodolist.R
import com.example.keeptodolist.data.TasksList
import de.hdodenhof.circleimageview.CircleImageView

class ListsAdapter(private val lists: List<TasksList>,private val handleList: HandleListOnClick) : RecyclerView.Adapter<ListsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listTitleTextView: TextView = view.findViewById(R.id.listTitle_textView)
        val listIconImage: CircleImageView = view.findViewById(R.id.listIcon_imageView)
        val tasksCount:TextView = view.findViewById(R.id.tasksCount_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(
            view = view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listTitleTextView.text = lists[position].name
        holder.listIconImage.borderColor = ContextCompat.getColor(holder.itemView.context,lists[position].color)
        holder.tasksCount.text = lists[position].count.toString()
        holder.itemView.setOnClickListener {
            handleList.onClickItemListener(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.count()
    }
}