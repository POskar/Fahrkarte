package com.example.fahrkarte.fragments.TicketDetails

import android.os.Bundle
import android.text.TextUtils
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.Task
import com.example.fahrkarte.data.models.Ticket
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
        binding.etDescription.text = "Description:${System.lineSeparator()} ${passed_ticket.description} ${System.lineSeparator()}"
        binding.etPriority.text = passed_ticket.priority

        when(passed_ticket.priority){
            "High Priority" -> { binding.etPriority.setTextColor(resources.getColor(R.color.red)) }
            "Medium Priority"-> { binding.etPriority.setTextColor(resources.getColor(R.color.yellow)) }
            "Low Priority"-> { binding.etPriority.setTextColor(resources.getColor(R.color.green)) }
        }

        if(passed_ticket.status == "Closed"){
            binding.tvCreateTask.visibility = View.GONE
            binding.cvCreateTicket.visibility = View.GONE
        }

        binding.tvCreateTask.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.cvCreateTicket, AutoTransition())
            binding.cvCreateTicket.visibility = View.VISIBLE
        }

        binding.ibDoneTicket.setOnClickListener {
            var description = binding.etTaskDescription.text.toString()
            if(TextUtils.isEmpty(description)){
                Toast.makeText(requireContext(), "Fill in the task description.", Toast.LENGTH_SHORT).show()
            }else {
                createNewTask(description)
                TransitionManager.beginDelayedTransition(binding.cvCreateTicket, AutoTransition())
                binding.cvCreateTicket.visibility = View.GONE
                binding.etTaskDescription.text.clear()
                binding.spnStatus.setSelection(0)
            }
        }

        binding.ibCloseTicket.setOnClickListener{
            TransitionManager.beginDelayedTransition(binding.cvCreateTicket, AutoTransition())
            binding.cvCreateTicket.visibility = View.GONE
            binding.etTaskDescription.text.clear()
            binding.spnStatus.setSelection(0)
        }

        return binding.root
    }

    fun ticketDetails(ticket: Ticket) {
        mTicket = ticket

        binding.rvTasksList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTasksList.setHasFixedSize(true)

        val adapter = TicketDetailsAdapter(this, ticket.taskList)
        binding.rvTasksList.adapter = adapter

        binding.etStatus.text = ticket.status
    }

    fun createNewTask(description: String){
        val task = Task(description, Firestore().getCurrentUserId(), mTicket.taskList.size)

        mTicket.taskList.add(0, task)
        mTicket.taskList.removeAt(mTicket.taskList.size - 1)

        when(binding.spnStatus.selectedItem){
            "Open" -> { mTicket.status = "Open" }
            "Waiting" -> { mTicket.status = "Waiting" }
            "Closed" -> {
                mTicket.status = "Closed"
                binding.tvCreateTask.visibility = View.GONE
                binding.cvCreateTicket.visibility = View.GONE
            }
        }

        mTicket.assignedToPerson = Firestore().getCurrentUserId()
        Firestore().addUpdateTaskList(this, mTicket)
    }

    fun addUpdateTaskListSuccess() {
        Firestore().getTicketDetails(this, mTicket.id)
    }
}