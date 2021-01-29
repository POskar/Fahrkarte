package com.example.fahrkarte.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.SharedViewModel
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.ActivityMainBinding
import com.example.fahrkarte.databinding.NavHeaderMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mSharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Firestore().loadUserDataForDrawer(this)

        setUpNavigation()

        val toolbar: Toolbar = binding.toolbarMainActivity
        setSupportActionBar(toolbar)

        val drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(binding.navView , navHostFragment!!.navController)
    }

    fun updateNavigationUserDetails(user: User) {
        val viewHeader = binding.navView.getHeaderView(0)
        val navHeaderMainBinding : NavHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader)

        Glide
                .with(this)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.round_profile_icon)
                .into(navHeaderMainBinding.navUserImage)

        navHeaderMainBinding.tvUsername.text = user.name
        navHeaderMainBinding.tvDepartment.text = user.department
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}