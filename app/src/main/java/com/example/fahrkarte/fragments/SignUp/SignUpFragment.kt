package com.example.fahrkarte.fragments.SignUp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            registerUser()
        }

        binding.toolbarSingInActivity.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    private fun registerUser(){
        val name: String = binding.etName.text.toString().trim() { it <= ' '}
        val surname: String = binding.etSurname.text.toString().trim() { it <= ' '}
        val email: String = binding.etEmail.text.toString().trim() { it <= ' '}
        val password: String = binding.etPassword.text.toString().trim() { it <= ' '}
        val department: String = binding.spnDepartments.selectedItem.toString()

        val fullname = "$name $surname"

        var whetherAdmin = 0
        if(department == "IT"){
            whetherAdmin = 1
        }

        if(validateForm(fullname, email, password, department)){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!


                    val user = User(firebaseUser.uid, fullname, registeredEmail, department, "", 0, whetherAdmin)
                    Firestore().registerUser(this, user)
                    findNavController().navigate(R.id.action_signUpFragment_to_introFragment)
                } else {
                    Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateForm(name: String, email: String, password: String, department: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                Toast.makeText(requireContext(), "Please enter the name and surname", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(email)->{
                Toast.makeText(requireContext(), "Please enter the e-mail", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password)->{
                Toast.makeText(requireContext(), "Please enter the password", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(department)->{
                Toast.makeText(requireContext(), "Please choose your department", Toast.LENGTH_SHORT).show()
                false
            }else->{
                true
            }

        }
    }
}