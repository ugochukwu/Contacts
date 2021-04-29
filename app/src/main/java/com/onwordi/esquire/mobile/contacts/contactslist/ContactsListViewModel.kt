package com.onwordi.esquire.mobile.contacts.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onwordi.esquire.mobile.contacts.data.Contact
import com.onwordi.esquire.mobile.contacts.data.ContactsListRepository
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.launch

class ContactsListViewModel(private val repo: ContactsListRepository = ContactsListRepository.repo()) :
    ViewModel() {

    private var uiState: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)

    fun getContacts(): LiveData<UiState> {
        viewModelScope.launch {
            uiState.value = try {
                UiState.Success(repo.getContacts())
            } catch (ioException: IOException) {
                UiState.Error
            }
        }
        return uiState
    }
}

sealed class UiState {
    object Loading : UiState()
    object Error : UiState()
    data class Success(val contacts: List<Contact>) : UiState()
}

class ContactDetailsViewModel(private val repo: ContactsListRepository = ContactsListRepository.repo()) :
    ViewModel() {
    fun getDetails(id: Int) = repo.getContact(id)
}