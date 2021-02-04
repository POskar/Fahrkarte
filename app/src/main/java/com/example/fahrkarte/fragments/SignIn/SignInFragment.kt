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
import androidx.core.content.ContextCompat
import com.example.fahrkarte.R
import com.example.fahrkarte.activities.MainActivity
import com.example.fahrkarte.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
        
        binding.toolbarSingInActivity.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }

    private fun signInUser(){
        val email: String = binding.etEmail.text.toString().trim() { it <= ' '}
        val password: String = binding.etPassword.text.toString().trim() { it <= ' '}

        if(validateForm(email, password)){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.d("Sign in", "createUserWithEmail:success")
                    //val user = auth.currentUser
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        showErrorSnackbar(e.message.toString())
                    }
                    Log.e("Sign in", task.exception?.message, task.exception)
                }
            }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean{
        return when {
            TextUtils.isEmpty(email)->{
                showErrorSnackbar("Please enter the e-mail")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackbar("Please enter the password.")
                false
            }else->{
                true
            }

        }
    }

    private fun showErrorSnackbar(message: String){
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.snackbar_error_color))
        snackBar.show()
    }
}