package com.onwordi.esquire.mobile.contacts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactResult(
    val contacts: List<Contact>
)

@Parcelize
@Serializable
data class Contact(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val company: Company
) : Parcelable

@Parcelize
@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
) : Parcelable

@Parcelize
@Serializable
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
) : Parcelable

@Parcelize
@Serializable
data class Geo(
    val lat: Double,
    val lng: Double
) : Parcelable
