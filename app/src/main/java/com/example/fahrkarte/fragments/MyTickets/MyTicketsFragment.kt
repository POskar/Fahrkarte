package com.example.fahrkarte.fragments.MyTickets

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

        Firestore().getTicketsList(this)

        binding.fabCreate.setOnClickListener {
            findNavController().navigate(R.id.action_myTicketsFragment_to_createTicketFragment)
        }

        return binding.root
    }

    fun populateTicketsRecyclerView(ticketsList: ArrayList<Ticket>){

        if(ticketsList.size > 0){
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
                    val action = MyTicketsFragmentDirections.actionMyTicketsFragmentToTicketDetailsFragment(model)

                    findNavController().navigate(action)
                }
            })
        }else{
            binding.rvTicketsList.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}