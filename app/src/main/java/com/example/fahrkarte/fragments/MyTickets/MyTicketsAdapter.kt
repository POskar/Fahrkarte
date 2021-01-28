package com.example.fahrkarte.fragments.MyTickets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.ItemTicketBinding

private var _binding: ItemTicketBinding? = null
private val binding get() = _binding!!

open class MyTicketsAdapter(private val context: Context, private var list: ArrayList<Ticket>): RecyclerView.Adapter<MyTicketsAdapter.MyViewHolder>(){

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        if(holder is MyViewHolder){
            binding.tvTicketId.text = model.id
            binding.tvName.text = model.name
            binding.tvCreatedBy.text = model.createdBy

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: Ticket)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    class MyViewHolder(binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)
}