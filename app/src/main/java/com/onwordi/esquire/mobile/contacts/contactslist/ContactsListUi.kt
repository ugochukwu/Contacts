package com.onwordi.esquire.mobile.contacts.contactslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.onwordi.esquire.mobile.contacts.data.Company
import com.onwordi.esquire.mobile.contacts.data.Contact
import com.onwordi.esquire.mobile.contacts.shared.LoadingIndicator

@Composable
fun ContactsListScreen(viewModel: ContactsListViewModel, onContactClick: (Contact) -> Unit) {
    val uiState by viewModel.getContacts().observeAsState(UiState.Loading)
    Scaffold(topBar = { TopAppBar(title = { Text("Contacts") }) }) {
        ContactListContent(uiState, onContactClick = onContactClick)
    }
}

@Composable
private fun ContactListContent(uiState: UiState, onContactClick: (Contact) -> Unit) {
    when (uiState) {
        UiState.Error -> Box(Modifier.fillMaxSize()) { Text("Error") }
        UiState.Loading -> LoadingIndicator(Modifier.fillMaxSize())
        is UiState.Success -> ContactsList(uiState.contacts, Modifier.fillMaxSize(), onContactClick)
    }
}

@Composable
fun ContactsList(contacts: List<Contact>, modifier: Modifier, onContactClick: (Contact) -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(contacts, key = { it.id }) {
            ContactListItem(it, onContactClick)
            Divider()
        }
    }
}

@Preview
@Composable
fun ContactItemPreview() {
    ContactListItem(dummyContact, onContactClick = {})
}

@Composable
fun ContactListItem(
    contact: Contact,
    onContactClick: (Contact) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberCoilPainter(
                request = contact.avatarUrl(80),
                fadeIn = true),
            contentDescription = "Avatar image",
            modifier = Modifier
                .padding(end = 16.dp)
                .background(MaterialTheme.colors.primary, CircleShape)
                .padding(2.dp)
                .clip(CircleShape)
                .size(48.dp),
        )
        Text(contact.name, style = MaterialTheme.typography.body1)
    }
}

val dummyContact = Contact(
    id = 0,
    name = "James",
    email = "james.bar@foo.com",
    phone = "036498984720",
    website = "www.michelonwordi.com",
    company = Company("MB.io", "bla bla bla", bs = "foobar"),
    username = "jammic"
)

fun Contact.avatarUrl(size: Int) = "https://i.pravatar.cc/${size}?img=${id}"