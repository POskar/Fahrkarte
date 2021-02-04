package com.example.fahrkarte.fragments.MyDesk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.FragmentMyDeskBinding
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragmentDirections

class MyDeskFragment : Fragment() {

    private var _binding: FragmentMyDeskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyDeskBinding.inflate(inflater, container, false)

        binding.tvNoData.text = "Sorry, can't find any tickets," + System.lineSeparator() + "maybe you haven't reserved any?"

        Firestore().getTicketsAssignedTo(this)

        return binding.root
    }

    fun populateTicketsRecyclerView(ticketsList: ArrayList<Ticket>){
        var listSize = ticketsList.size
        var quantity = ""
        if(listSize > 0){
            if(listSize > 1){
                quantity = "are $listSize tickets"
            }else{
                quantity = "is $listSize ticket"
            }
            binding.tvQuantity.text = "Currently there $quantity in My Desk"
            binding.rvTicketsList.visibility = View.VISIBLE
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.rvTicketsList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvTicketsList.setHasFixedSize(true)

            ticketsList.sortedBy { it.status }
            val adapter = MyTicketsAdapter(ticketsList)
            binding.rvTicketsList.adapter = adapter

            adapter.setOnClickListener(object: MyTicketsAdapter.OnClickListener{
                override fun onClick(position: Int, model: Ticket) {
                    val action = MyDeskFragmentDirections.actionMyDeskFragmentToTicketDetailsFragment(model)

                    findNavController().navigate(action)
                }
            })
        }else{
            binding.tvQuantity.visibility = View.GONE
            binding.rvTicketsList.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}