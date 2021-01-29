package com.example.fahrkarte.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.fahrkarte.R
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding
import com.example.fahrkarte.databinding.FragmentTicketDetailsBinding

class TicketDetailsFragment : Fragment() {

    private var _binding: FragmentTicketDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: TicketDetailsFragmentArgs by navArgs()
    private lateinit var ticket: Ticket

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)

        ticket = args.ticket



        return binding.root
    }

    fun ticketDetails(ticket: Ticket) {
        TODO("Not yet implemented")
    }

    fun addUpdateTaskListSuccess() {
        TODO("Not yet implemented")
    }
}