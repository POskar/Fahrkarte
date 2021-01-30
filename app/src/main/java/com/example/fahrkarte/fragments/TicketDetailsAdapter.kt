package com.example.fahrkarte.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.fahrkarte.data.models.Task
import com.example.fahrkarte.databinding.ItemTaskBinding
import com.example.fahrkarte.databinding.ItemTicketBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter

private var _binding: ItemTaskBinding? = null
private val binding get() = _binding!!

open class TicketDetailsAdapter(private val fragment: TicketDetailsFragment, private var list: ArrayList<Task>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    //TODO stworzyć recycler adapter dla tasków w ticketcie

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        _binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if(holder is MyViewHolder){
            /*
            if(position == list.size - 1){
                binding.tvAddTaskList.visibility = View.VISIBLE
                binding.llTaskItem.visibility = View.GONE
            }else{
                binding.tvAddTaskList.visibility = View.GONE
                binding.llTaskItem.visibility = View.VISIBLE
            }

            binding.tvAddTaskList.setOnClickListener {
                binding.tvAddTaskList.visibility = View.GONE
                binding.cvAddTaskListName.visibility = View.VISIBLE
            }

            binding.ibCloseListName.setOnClickListener{
                binding.tvAddTaskList.visibility = View.VISIBLE
                binding.cvAddTaskListName.visibility = View.GONE
            }

            binding.ibDoneListName.setOnClickListener{
                val listName = binding.etDescription.text.toString()

                if(listName.isNotEmpty()){
                    fragment.createTaskList(listName)
                }else{
                    Toast.makeText(fragment.context, "Please enter list name.", Toast.LENGTH_LONG).show()
                }
            }
             */
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}