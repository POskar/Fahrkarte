package com.example.fahrkarte.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fahrkarte.R
import com.example.fahrkarte.data.models.Ticket

class TicketDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_details, container, false)
    }

    fun ticketDetails(ticket: Ticket) {
        TODO("Not yet implemented")
    }

    fun addUpdateTaskListSuccess() {
        TODO("Not yet implemented")
    }
}