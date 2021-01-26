package com.example.fahrkarte.data.Firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fahrkarte.activities.MainActivity
import com.example.fahrkarte.data.models.Ticket
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.fragments.CreateTicket.CreateTicketFragment
import com.example.fahrkarte.fragments.MyTickets.MyTicketsFragment
import com.example.fahrkarte.fragments.Settings.SettingsFragment
import com.example.fahrkarte.fragments.SignIn.SignInFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment
import com.example.fahrkarte.fragments.TicketDetailsFragment
import com.example.fahrkarte.utility.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {
    private val db = FirebaseFirestore.getInstance()

    //region User

    fun registerUser(fragment: SignUpFragment, userInfo: User){
        db.collection(Constants.USERS)
                .document(getCurrentUserId())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    //TODO Progress dialog / bar
                    Toast.makeText(fragment.requireContext(), "User successfully registered !", Toast.LENGTH_SHORT).show()
                }
    }

    fun updateUserProfileData(userHashMap: HashMap<String, Any>){ //update danych o użytkowniku np w settings
        db.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap)
                .addOnSuccessListener {

                }.addOnFailureListener{ e ->
                    // TODO ???
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
                }
            }.addOnFailureListener{ e ->
                when(fragment){
                    is SettingsFragment ->{
                        // TODO hideProgressDialog() ???
                    }
                }
                Log.e("SignInUser", "Error writing document", e)
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
                .document()
                .set(ticket, SetOptions.merge())
                .addOnSuccessListener {
                    Log.e(fragment.javaClass.simpleName, "Board created successfully.")
                    Toast.makeText(fragment.requireActivity(), "Board created successfully.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    exception ->
                    Log.e(fragment.javaClass.simpleName, "Error while creating a board", exception)
                }
    }

    fun getTicketsList(fragment: MyTicketsFragment){
        db.collection(Constants.TICKETS)
                .whereArrayContains(Constants.CREATEDBY, getCurrentUserId())
                .get()
                .addOnSuccessListener {
                    document ->
                    Log.i(fragment.javaClass.simpleName, document.documents.toString())
                    val ticketsList: ArrayList<Ticket> = ArrayList()
                    for(i in document.documents){
                        var ticket = i.toObject(Ticket::class.java)!!
                        ticket.documentId = i.id
                        ticketsList.add(ticket)
                    }

                    fragment.populateBoardsListtoUI(ticketsList)
                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the boards", e)
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
                    ticket.documentId = document.id
                    fragment.boardDetails(ticket)

                }.addOnFailureListener{
                    e ->
                    Log.e(fragment.javaClass.simpleName, "Error while displaying the boards", e)
                }
    }

    fun addUpdateTaskList(fragment: TicketDetailsFragment, ticket: Ticket){
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = ticket.taskList

        db.collection(Constants.TICKETS)
                .document(ticket.documentId)
                .update(taskListHashMap)
                .addOnSuccessListener {
                    Log.e(fragment.javaClass.simpleName, "TaskList updated successfully.")

                    fragment.addUpdateTaskListSuccess()
                }.addOnFailureListener {
                    exception ->
                    Log.e(fragment.javaClass.simpleName, "Error while updating a TaskList.")
                }
    }
    //endregion
}