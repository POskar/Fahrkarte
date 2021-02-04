package com.example.fahrkarte.fragments.SignOut

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fahrkarte.activities.IntroActivity
import com.example.fahrkarte.databinding.FragmentSignOutBinding
import com.google.firebase.auth.FirebaseAuth

class SignOutFragment : Fragment() {

    private var _binding: FragmentSignOutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignOutBinding.inflate(inflater, container, false)

        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()

        return binding.root
    }
}