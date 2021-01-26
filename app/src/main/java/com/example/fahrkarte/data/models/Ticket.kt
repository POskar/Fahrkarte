package com.example.fahrkarte.data.models

import android.os.Parcel
import android.os.Parcelable

data class Ticket(
    val name: String = "",
    val createdBy: String = "",
    val processType: String = "",
    val assignedToPerson: String = "",
    val assignedToDepartment: String = "",
    var documentId: String = "",
    var taskList: ArrayList<Task> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Task.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(createdBy)
        parcel.writeString(processType)
        parcel.writeString(assignedToPerson)
        parcel.writeString(assignedToDepartment)
        parcel.writeString(documentId)
        parcel.writeTypedList(taskList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }
}