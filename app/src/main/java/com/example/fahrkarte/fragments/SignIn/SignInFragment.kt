package com.example.fahrkarte.fragments.SignIn

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fahrkarte.activities.IntroActivity
import com.example.fahrkarte.activities.MainActivity
import com.example.fahrkarte.databinding.ActivityIntroBinding.inflate
import com.example.fahrkarte.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }

        return view
    }

    private fun signInUser(){
        val email: String = binding.etEmail.text.toString().trim() { it <= ' '}
        val password: String = binding.etPassword.text.toString().trim() { it <= ' '}

        if(validateForm(email, password)){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign in", "createUserWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign in", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean{
        return when {
            TextUtils.isEmpty(email)->{
                Toast.makeText(requireContext(),"Please enter the e-mail", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password)->{
                Toast.makeText(requireContext(),"Please enter the password", Toast.LENGTH_SHORT).show()
                false
            }else->{
                true
            }

        }
    }

}