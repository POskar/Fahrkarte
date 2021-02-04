package com.example.fahrkarte.fragments.SignUp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!

                    val user = User(firebaseUser.uid, fullname, registeredEmail, department, "", 0, whetherAdmin)
                    Firestore().registerUser(this, user)
                    Toast.makeText(requireContext(), "User registered successfully.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_introFragment)
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        showErrorSnackbar(e.message.toString())
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        showErrorSnackbar(e.message.toString())
                    } catch (e: FirebaseAuthUserCollisionException) {
                        showErrorSnackbar(e.message.toString())
                    } catch (e: Exception) {
                        showErrorSnackbar(e.message.toString())
                    }
                    Log.e("Sign up", task.exception?.message, task.exception)
                }
            }
        }
    }

    private fun validateForm(name: String, email: String, password: String, department: String): Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                showErrorSnackbar("Please enter the name and surname.")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackbar("Please enter the e-mail.")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackbar("Please enter the password.")
                false
            }
            TextUtils.isEmpty(department)->{
                showErrorSnackbar("Please choose your department.")
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