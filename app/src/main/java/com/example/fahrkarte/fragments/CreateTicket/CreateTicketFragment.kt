package com.example.fahrkarte.fragments.CreateTicket

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.SharedViewModel
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.databinding.FragmentCreateTicketBinding
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class CreateTicketFragment : Fragment() {

    private var _binding: FragmentCreateTicketBinding? = null
    private val binding get() = _binding!!

    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateTicketBinding.inflate(inflater, container, false)

        binding.spnPriority.onItemSelectedListener = mSharedViewModel.listener

        binding.btnCreate.setOnClickListener {
            createTicket()
        }

        return binding.root
    }

    private fun createTicket() {
        val current_date = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val current_date_formatted = current_date.format(formatter)

        // TODO poprawić nadawanie unikatowej wartości ticketowi
        var id = Random.nextInt(1000, 9999)
        var user_id = FirebaseAuth.getInstance().currentUser!!.uid

        var name = binding.etTitle.text.toString().trim() { it <= ' '}
        var description = binding.etDescription.text.toString()
        var range = binding.spnRange.selectedItem.toString()
        var priority = binding.spnPriority.selectedItem.toString()

        if(validateForm(name, description)) {
            var ticket = Ticket(
                "$current_date_formatted-$id",
                    name,
                    user_id,
                    description,
                    range,
                    priority,
                    "Open"
            )

            Firestore().createTicket(this, ticket)

            findNavController().navigate(R.id.action_createTicketFragment_to_myTicketsFragment)
        }
    }

    private fun validateForm(name: String, description: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                Toast.makeText(requireContext(), "Please fill the name of the ticket", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(description)->{
                Toast.makeText(requireContext(), "Please fill the description", Toast.LENGTH_SHORT).show()
                false
            }else->{
                true
            }
        }
    }
}
