package com.example.fahrkarte.data.models

import android.os.Parcel
import android.os.Parcelable

data class Ticket(
    var id: String = "",
    val name: String = "",
    val createdBy: String = "",
    var description: String = "",
    val range: String = "",
    var priority: String = "",
    var status: String = "",
    var assignedToPerson: String = "",
    var taskList: ArrayList<Task> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Task.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(createdBy)
        parcel.writeString(description)
        parcel.writeString(range)
        parcel.writeString(priority)
        parcel.writeString(status)
        parcel.writeString(assignedToPerson)
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