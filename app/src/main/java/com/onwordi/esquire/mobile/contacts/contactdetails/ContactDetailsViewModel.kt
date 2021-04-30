package com.onwordi.esquire.mobile.contacts.contactdetails

import androidx.lifecycle.ViewModel
import com.onwordi.esquire.mobile.contacts.data.ContactsListRepository

class ContactDetailsViewModel(private val repo: ContactsListRepository = ContactsListRepository.repo()) :
    ViewModel() {
    fun getDetails(id: Int) = repo.getContact(id)
}