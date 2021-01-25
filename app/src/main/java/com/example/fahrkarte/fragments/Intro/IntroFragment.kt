package com.example.fahrkarte.fragments.Intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.databinding.FragmentIntroBinding
import com.example.fahrkarte.databinding.FragmentSignUpBinding
import com.example.fahrkarte.fragments.SignIn.SignInFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroBinding.inflate(inflater, container, false)

        binding.btSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_signInFragment)
        }

        binding.btSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_signUpFragment)
        }

        return binding.root
    }
}