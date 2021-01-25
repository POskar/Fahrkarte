package com.example.fahrkarte.data.Firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fahrkarte.activities.MainActivity
import com.example.fahrkarte.data.models.User
import com.example.fahrkarte.fragments.Settings.SettingsFragment
import com.example.fahrkarte.fragments.SignIn.SignInFragment
import com.example.fahrkarte.fragments.SignUp.SignUpFragment
import com.example.fahrkarte.utility.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Firestore {
    private val db = FirebaseFirestore.getInstance()

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
}