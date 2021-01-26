package com.example.fahrkarte.fragments.MyTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding

class MyTicketsFragment : Fragment() {

    private var _binding: FragmentMyTicketsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyTicketsBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun populateBoardsListtoUI(ticketsList: ArrayList<Ticket>) {

    }


}