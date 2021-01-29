package com.example.fahrkarte.fragments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.fahrkarte.databinding.ItemTicketBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter

open class TicketDetailsAdapter() : RecyclerView.Adapter<TicketDetailsAdapter.MyViewHolder>() {

    class MyViewHolder(binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)

    //TODO stworzyć recycler adapter dla tasków w ticketcie

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}