package com.example.fahrkarte.fragments.Users

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
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding
import com.example.fahrkarte.databinding.FragmentUsersBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragmentDirections

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)

        Firestore().getUsersList(this)

        return binding.root
    }

    fun populateUsersRecyclerView(usersList: ArrayList<User>){

        if(usersList.size > 0){
            binding.rvUsersList.visibility = View.VISIBLE
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.rvUsersList.layoutManager = LinearLayoutManager(requireContext())
            binding.rvUsersList.setHasFixedSize(true)

            usersList.sortedBy { it.name }
            val adapter = UsersAdapter(this, usersList)
            binding.rvUsersList.adapter = adapter

            adapter.setOnClickListener(object: UsersAdapter.OnClickListener{
                override fun onClick(position: Int, model: Ticket) {
                    //TODO obsluga kliknięcia jednego z uzytkownikow
                }
            })
        }else{
            binding.rvUsersList.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}