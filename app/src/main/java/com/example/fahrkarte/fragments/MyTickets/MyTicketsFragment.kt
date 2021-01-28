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

        //TODO nie wyświetlają się tickety
        Firestore().getTicketsList(this)

        return binding.root
    }

    fun populateTicketsRecyclerView(ticketsList: ArrayList<Ticket>){

        if(ticketsList.size > 0){
            binding.rvTicketsList.visibility = View.VISIBLE
            /* TODO dopóki nie naprawię tego w layoucie to nie zadziała
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            */
            binding.rvTicketsList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvTicketsList.setHasFixedSize(true)

            val adapter = MyTicketsAdapter(ticketsList)
            binding.rvTicketsList.adapter = adapter

            adapter.setOnClickListener(object: MyTicketsAdapter.OnClickListener{
                override fun onClick(position: Int, model: Ticket) {
                    /*
                    val intent = Intent(this@MainActivity, TaskListActivity::class.java)
                    intent.putExtra(Constants.DOCUMENT_ID, model.documentId)
                    startActivity(intent)
                    */

                    findNavController().navigate(R.id.action_myTicketsFragment_to_ticketDetailsFragment)
                    //TODO przekazać dane za pomocą safeargs
                }
            })
        }else{
            binding.rvTicketsList.visibility = View.GONE
            /*
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
             */
        }
    }


}