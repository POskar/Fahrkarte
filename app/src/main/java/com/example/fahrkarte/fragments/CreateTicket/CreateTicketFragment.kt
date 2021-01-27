package com.example.fahrkarte.fragments.CreateTicket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fahrkarte.R

class CreateTicketFragment : Fragment() {

    //TODO dodać tworzenie ticketów, aby sprawdzić jak wygląda RecyclerView, najlepiej by było z unikatowym id -> eg. 20210127-1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_ticket, container, false)
    }
}