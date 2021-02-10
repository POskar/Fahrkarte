package com.example.fahrkarte.data.Firebase

import android.app.Activity
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fahrkarte.activities.MainActivity
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.fragments.CreateTicket.CreateTicketFragment
import com.example.fahrkarte.fragments.MyDesk.MyDeskFragment
import com.example.fahrkarte.fragments.MyTickets.MyTicketsAdapter
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragment
import com.example.fahrkarte.fragments.Settings.SettingsFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment
import com.example.fahrkarte.fragments.TicketDetails.TicketDetailsAdapter
import com.example.fahrkarte.fragments.TicketDetails.TicketDetailsFragment
import com.example.fahrkarte.fragments.Users.UsersFragment
import com.example.fahrkarte.fragments.WaitingQueue.WaitingQueueFragment
import com.example.fahrkarte.utility.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {
    private val db = FirebaseFirestore.getInstance()

    //region User

    fun registerUser(fragment: SignUpFragment, userInfo: User){
        db.collection(Constants.USERS)
                .document(getCurrentUserId())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    Toast.makeText(fragment.requireContext(), "User successfully registered.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{ e ->
                    Log.e("SignUp", "Error registering the user", e)
                }
    }

    fun updateUserProfileData(fragment: SettingsFragment, userHashMap: HashMap<String, Any>){
        db.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap)
                .addOnSuccessListener {
                    Toast.makeText(fragment.requireContext(), "User has been successfully updated.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{ e ->
                    Log.e("SettingsFragment", "Error updating the user", e)
                }
    }

    fun loadUserData(fragment: Fragment){
        db.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!
                when(fragment){
                    is SettingsFragment ->{
                        fragment.setUserDataInUI(loggedInUser)
                    }
                    is TicketDetailsFragment ->{
                        fragment.checkUserType(loggedInUser)
                    }
                }
            }.addOnFailureListener{ e ->
                when(fragment){
                    is SettingsFragment ->{
                        Log.e("SettingsFragment", "Error writing document", e)
                    }
                    is TicketDetailsFragment ->{
                        Log.e("TicketDetailsFragment", "Error writing document", e)
                    }
                }
            }
    }

    fun loadUserDataForDrawer(activity: Activity){
        db.collection(Constants.USERS)
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener { document ->
                    val loggedInUser = document.toObject(User::class.java)!!
                    if (activity is MainActivity) {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                }.addOnFailureListener{ e ->
                    Log.e("MainActivity", "Error getting data for the drawer.", e)
                }
    }

    fun getUsersList(fragment: UsersFragment){
        db.collection(Constants.USERS)
                .whereNotEqualTo(Constants.ID, getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    val usersList: ArrayList<User> = ArrayList()
                    for(i in document){
                        var user = i.toObject(User::class.java)
                        usersList.add(user)
                    }

                    fragment.populateUsersRecyclerView(usersList)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the users", e)
                }
    }

    fun getCurrentUserId(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    //endregion

    //region Ticket & Tasks
    fun createTicket(fragment: CreateTicketFragment, ticket: Ticket){
        db.collection(Constants.TICKETS)
                .document(ticket.id)
                .set(ticket, SetOptions.merge())
                .addOnSuccessListener {
                    Log.e(fragment.javaClass.simpleName, "Ticket created successfully.")
                    Toast.makeText(fragment.requireActivity(), "Ticket created successfully.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    exception ->
                    Log.e(fragment.javaClass.simpleName, "Error while creating a ticket", exception)
                }
    }

    fun getTicketsList(fragment: MyTicketsFragment){
        db.collection(Constants.TICKETS)
                .whereEqualTo(Constants.CREATEDBY, getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    Log.i(fragment.javaClass.simpleName, document.documents.toString())
                    val ticketsList: ArrayList<Ticket> = ArrayList()
                    for(i in document.documents){
                        var ticket = i.toObject(Ticket::class.java)!!
                        ticket.id = i.id
                        ticketsList.add(ticket)
                    }

                    fragment.populateTicketsRecyclerView(ticketsList)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the tickets", e)
                }
    }

    fun getUnassignedTickets(fragment: WaitingQueueFragment){
        db.collection(Constants.TICKETS)
                .whereEqualTo(Constants.ASSIGNED_TO, "")
                .get()
                .addOnSuccessListener {
                    document ->
                    val ticketsList: ArrayList<Ticket> = ArrayList()
                    for(i in document.documents){
                        var ticket = i.toObject(Ticket::class.java)!!
                        ticketsList.add(ticket)
                    }

                    fragment.populateTicketsRecyclerView(ticketsList)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the tickets in my desk", e)
                }
    }

    fun getTicketsAssignedTo(fragment: MyDeskFragment){
        db.collection(Constants.TICKETS)
                .whereEqualTo(Constants.ASSIGNED_TO, getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    Log.i(fragment.javaClass.simpleName, document.documents.toString())
                    val ticketsList: ArrayList<Ticket> = ArrayList()
                    for(i in document.documents){
                        var ticket = i.toObject(Ticket::class.java)!!
                        ticket.id = i.id
                        ticketsList.add(ticket)
                    }

                    fragment.populateTicketsRecyclerView(ticketsList)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the tickets in my desk", e)
                }
    }

    fun getTicketDetails(fragment: TicketDetailsFragment, documentId: String){
        db.collection(Constants.TICKETS)
                .document(documentId)
                .get()
                .addOnSuccessListener {
                    document ->
                    Log.i(fragment.javaClass.simpleName, document.toString())
                    val ticket = document.toObject(Ticket::class.java)!!
                    ticket.id = document.id

                    fragment.ticketDetails(ticket)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the tickets", e)
                }
    }

    fun updateTaskList(fragment: TicketDetailsFragment, ticket: Ticket){
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = ticket.taskList
        taskListHashMap[Constants.STATUS] = ticket.status
        taskListHashMap[Constants.ASSIGNED_TO] = ticket.assignedToPerson

        db.collection(Constants.TICKETS)
                .document(ticket.id)
                .update(taskListHashMap)
                .addOnSuccessListener {
                    Log.e(fragment.javaClass.simpleName, "TaskList updated successfully.")

                    fragment.addUpdateTaskListSuccess()
                }.addOnFailureListener {
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while updating a TaskList.", e)
                }
    }

    fun updateAssignedPerson(fragment: TicketDetailsFragment, ticket: Ticket){
        val ticketHashMap = HashMap<String, Any>()
        ticketHashMap[Constants.ASSIGNED_TO] = ""

        db.collection(Constants.TICKETS)
                .document(ticket.id)
                .update(ticketHashMap)
                .addOnSuccessListener {
                    Log.e(fragment.javaClass.simpleName, "TaskList updated successfully.")

                    fragment.addUpdateTaskListSuccess()
                }.addOnFailureListener {
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while updating a TaskList.", e)
                }
    }
    //endregion
}