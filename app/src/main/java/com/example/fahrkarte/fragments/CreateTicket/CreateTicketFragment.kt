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

class CreateTicketFragment : Fragment() {

    //TODO dodać tworzenie ticketów, aby sprawdzić jak wygląda RecyclerView, najlepiej by było z unikatowym id -> eg. 20210127-1234

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
        var id = 1234
        var user_id = FirebaseAuth.getInstance().currentUser!!.uid

        var name = binding.etTitle.text.toString()
        var description = binding.etDescription.text.toString()

        if(validateForm(name, description)) {
            var ticket = Ticket(
                    current_date_formatted.toString() + "-" + id.toString(),
                    name,
                    user_id,
                    binding.spnRange.selectedItem.toString(),
                    binding.spnPriority.selectedItem.toString(),
                    "Open"
            )

            Firestore().createTicket(this, ticket)
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
