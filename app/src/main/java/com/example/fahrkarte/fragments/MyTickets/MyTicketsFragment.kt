package com.example.fahrkarte.fragments.MyTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.SharedViewModel
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding
import com.example.fahrkarte.fragments.CreateTicket.CreateTicketFragment
import com.example.fahrkarte.fragments.Settings.SettingsFragment
import com.example.fahrkarte.fragments.TicketDetailsFragment

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
            activity?.supportFragmentManager?.commit {
                add<CreateTicketFragment>(R.id.fragment_container)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
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

            val adapter = MyTicketsAdapter(ticketsList)
            binding.rvTicketsList.adapter = adapter

            adapter.setOnClickListener(object: MyTicketsAdapter.OnClickListener{
                override fun onClick(position: Int, model: Ticket) {
                    activity?.supportFragmentManager?.commit {
                        add<TicketDetailsFragment>(R.id.fragment_container)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    //TODO przekazać dane za pomocą safeargs
                }
            })
        }else{
            binding.rvTicketsList.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }


}