package com.example.fahrkarte.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val department: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val admin: Int = 0
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readInt()){
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(department)
        writeString(image)
        writeLong(mobile)
        writeInt(admin)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}