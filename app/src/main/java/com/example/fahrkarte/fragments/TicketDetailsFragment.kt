package com.example.fahrkarte.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.Task
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.FragmentMyTicketsBinding
import com.example.fahrkarte.databinding.FragmentTicketDetailsBinding

class TicketDetailsFragment : Fragment() {

    private var _binding: FragmentTicketDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: TicketDetailsFragmentArgs by navArgs()
    private lateinit var mTicket: Ticket

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)

        var passed_ticket : Ticket = args.ticket

        Firestore().getTicketDetails(this, passed_ticket.id)

        binding.etTicketid.text = "ID: ${passed_ticket.id}"
        binding.etStatus.text = passed_ticket.status
        binding.etName.text = "Name: ${passed_ticket.name}"
        binding.etRequester.text = "Requester: ${passed_ticket.createdBy}"
        binding.etRange.text = "Range: ${passed_ticket.range}"
        binding.etDescription.text = "Description:${System.lineSeparator()} ${passed_ticket.description} ${System.lineSeparator()} ${System.lineSeparator()}"
        binding.etPriority.text = passed_ticket.priority

        when(passed_ticket.priority){
            "High Priority" -> { binding.etPriority.setTextColor(resources.getColor(R.color.red)) }
            "Medium Priority"-> { binding.etPriority.setTextColor(resources.getColor(R.color.yellow)) }
            "Low Priority"-> { binding.etPriority.setTextColor(resources.getColor(R.color.green)) }
        }

        return binding.root
    }

    fun ticketDetails(ticket: Ticket) {
        mTicket = ticket

        val addTaskList = Task("Add Task")
        ticket.taskList.add(addTaskList)

        binding.rvTasksList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTasksList.setHasFixedSize(true)

        val adapter = TicketDetailsAdapter(this, ticket.taskList)
        binding.rvTasksList.adapter = adapter
    }

    fun createTaskList(taskListName: String){
        val task = Task(taskListName, Firestore().getCurrentUserId())

        mTicket.taskList.add(0, task)
        mTicket.taskList.removeAt(mTicket.taskList.size - 1)

        Firestore().addUpdateTaskList(this, mTicket)
    }

    fun addUpdateTaskListSuccess() {
        Firestore().getTicketDetails(this, mTicket.id)
    }
}