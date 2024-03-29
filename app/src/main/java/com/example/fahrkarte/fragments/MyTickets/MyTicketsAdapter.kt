package com.example.fahrkarte.fragments.MyTickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fahrkarte.R
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.ItemTicketBinding


private var _binding: ItemTicketBinding? = null
private val binding get() = _binding!!

open class MyTicketsAdapter(private var ticketsList: ArrayList<Ticket>): RecyclerView.Adapter<MyTicketsAdapter.MyViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = ticketsList[position]

        binding.tvCreatedBy.text = model.createdBy
        binding.tvTicketId.text = model.id
        binding.tvName.text = model.name


        parsePriorityColor(model.priority, model.status)

        holder.itemView.setOnClickListener{
            if(onClickListener != null){
                onClickListener!!.onClick(position, model)
            }
        }
    }

    override fun getItemCount(): Int {
        return ticketsList.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Ticket)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    private fun parsePriorityColor(priority: String, status: String){
        when(priority){
            "High Priority" -> {
                binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.red))
            }
            "Medium Priority" -> {
                binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.yellow))
            }
            "Low Priority" -> {
                binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.green))
            }
        }
        if(status == "Closed"){
            binding.priorityIndicator.setCardBackgroundColor(binding.priorityIndicator.context.getColor(R.color.black))
        }
    }

    class MyViewHolder(binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)
}