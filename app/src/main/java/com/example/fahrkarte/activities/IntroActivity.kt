package com.example.fahrkarte.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.databinding.ActivityIntroBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragment
import com.example.fahrkarte.fragments.SignIn.SignInFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return super.onSupportNavigateUp() || return navController.navigateUp()
    }
}