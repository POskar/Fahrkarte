package com.example.fahrkarte.fragments.TicketDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fahrkarte.data.models.Task
import com.example.fahrkarte.databinding.ItemTaskBinding

private var _binding: ItemTaskBinding? = null
private val binding get() = _binding!!

open class TicketDetailsAdapter(private val fragment: TicketDetailsFragment, private var list: ArrayList<Task>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

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
            binding.tvTaskNumber.text = "TASK #" + model.number.toString()
            binding.tvCreatedBy.text = model.createdBy
            binding.tvDescriptionTicket.text = model.description
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}