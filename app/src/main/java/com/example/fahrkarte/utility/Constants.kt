package com.example.fahrkarte.utility

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment

object Constants {
    const val USERS: String = "Users"
    const val TICKETS: String = "Tickets"
    const val IMAGE: String = "image"
    const val NAME: String = "name"
    const val DEPARTMENT: String = "department"
    const val MOBILE: String = "mobile"
    const val CREATEDBY: String = "createdBy"
    const val STATUS: String = "status"
    const val ASSIGNED_TO: String = "assignedTo"
    const val ID: String = "id"
    const val TASK_LIST: String = "taskList"

    const val READ_STORAGE_PERMISSION_CODE = 1
    const val PICK_IMAGE_REQUEST_CODE = 2

    fun showImageChooser(fragment: Fragment) {
        var galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        fragment.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}