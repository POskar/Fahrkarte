package com.example.fahrkarte.data.models

import android.os.Parcel
import android.os.Parcelable

data class Task (
        var description: String = "",
        val createdBy: String = "",
        val number: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)= with(dest) {
        writeString(description)
        writeString(createdBy)
        writeInt(number)
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}