package com.example.fahrkarte.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.example.fahrkarte.R
import com.example.fahrkarte.data.Firebase.Firestore
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.databinding.ActivityMainBinding
import com.example.fahrkarte.databinding.NavHeaderMainBinding
import com.example.fahrkarte.fragments.CreateTicket.CreateTicketFragment
import com.example.fahrkarte.fragments.MyDesk.MyDeskFragment
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragment
import com.example.fahrkarte.fragments.Settings.SettingsFragment
import com.example.fahrkarte.fragments.Users.UsersFragment
import com.example.fahrkarte.fragments.WaitingQueue.WaitingQueueFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Firestore().loadUserDataForDrawer(this)

        supportFragmentManager.commit {
            add(R.id.fragment_container, MyTicketsFragment())
            setReorderingAllowed(true)
        }

        var nav_view = binding.navView
        nav_view.setNavigationItemSelectedListener(this)

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_create_ticket -> {
                supportFragmentManager.commit {
                    add<CreateTicketFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            R.id.nav_my_tickets -> {
                supportFragmentManager.commit {
                    add<MyTicketsFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            R.id.nav_settings -> {
                supportFragmentManager.commit {
                    add<SettingsFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            R.id.nav_users -> {
                supportFragmentManager.commit {
                    add<UsersFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            R.id.nav_my_desk -> {
                supportFragmentManager.commit {
                    add<MyDeskFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            R.id.nav_waiting_queue -> {
                supportFragmentManager.commit {
                    add<WaitingQueueFragment>(R.id.fragment_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateNavigationUserDetails(user: User) {
        val viewHeader = binding.navView.getHeaderView(0)
        val navHeaderMainBinding : NavHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader)

        Glide
                .with(this)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.round_profile_icon)
                .into(navHeaderMainBinding.navUserImage);

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