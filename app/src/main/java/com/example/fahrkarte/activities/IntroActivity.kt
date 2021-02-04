package com.example.fahrkarte.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.databinding.ActivityIntroBinding
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragment
import com.example.fahrkarte.fragments.SignIn.SignInFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var currentUserID = Firestore().getCurrentUserId()
        if(currentUserID.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return super.onSupportNavigateUp() || return navController.navigateUp()
    }
}