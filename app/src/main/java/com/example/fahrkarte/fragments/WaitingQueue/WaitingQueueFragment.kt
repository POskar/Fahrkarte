package com.example.fahrkarte.fragments.WaitingQueue

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
import com.example.fahrkarte.databinding.FragmentWaitingQueueBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragmentDirections

class WaitingQueueFragment : Fragment() {

    private var _binding: FragmentWaitingQueueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWaitingQueueBinding.inflate(inflater, container, false)

        binding.tvNoData.text = "Sorry, can't find any tickets in the waiting queue," + System.lineSeparator() + "maybe all tickets are taken?"

        Firestore().getUnassignedTickets(this)

        return binding.root
    }

    fun populateTicketsRecyclerView(ticketsList: ArrayList<Ticket>){

        if(ticketsList.size > 0){
            binding.tvQuantity.text = "Found " + ticketsList.size + " tickets in Waiting Queue"
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
                    val action = WaitingQueueFragmentDirections.actionWaitingQueueFragmentToTicketDetailsFragment(model)

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