package com.onwordi.esquire.mobile.contacts.contactdetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.coil.rememberCoilPainter
import com.onwordi.esquire.mobile.contacts.contactslist.avatarUrl
import com.onwordi.esquire.mobile.contacts.contactslist.dummyContact
import com.onwordi.esquire.mobile.contacts.data.Contact
import com.onwordi.esquire.mobile.contacts.ui.theme.ContactsTheme

@Composable
fun ContactDetailsScreen(contactId: Int? = 0, contactDetailsViewModel: ContactDetailsViewModel) {

    Scaffold(topBar = { TopAppBar(title = { Text("Details") }) }) {
        val contact = contactDetailsViewModel.getDetails(contactId ?: InvalidContactId)
        if (contact == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Contact Available", style = MaterialTheme.typography.h3)
            }
        } else {
            ContactDetailsContent(contact)
        }
    }
}

@Preview
@Composable
fun ContactDetailsPreview() {
    val contact = dummyContact.copy(
        email = "michel.onwordi@me.com",
        phone = "036498984720",
        website = "www.michelonwordi.com"
    )
    ContactsTheme {
        ContactDetailsContent(contact = contact)
    }
}

@Composable
fun ContactDetailsContent(contact: Contact) {
    Column(
        Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                shape = CircleShape,
                elevation = 8.dp,
                border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                modifier = Modifier.zIndex(4f)
            ) {
                val painter = rememberCoilPainter(request = contact.avatarUrl(300))

                Image(
                    painter = painter,
                    contentDescription = "Contact Profile Photo",
                    modifier = Modifier
                        .background(MaterialTheme.colors.onPrimary, CircleShape)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .size(160.dp),
                )
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width - 80, placeable.height) {
                            placeable.placeRelative(-80, 0)
                        }
                    }
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(
                        start = 40.dp,
                        top = 16.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
        Column(Modifier.padding(top = 24.dp)) {
            DetailLineItem(Icons.Outlined.Email, contact.email)
            DetailLineItem(Icons.Outlined.Phone, contact.phone)
            DetailLineItem(Icons.Outlined.Info, contact.website)
        }
    }
}

@Composable
fun DetailLineItem(icon: ImageVector, data: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colors.primaryVariant,
            contentDescription = "Detail item",
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colors.primary.copy(alpha = .4f), CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(data, style = MaterialTheme.typography.body2.copy(fontSize = 17.sp))
    }
}

@Preview
@Composable
fun ContactDetailsVariant2Preview() {
    Surface { ContactDetailsContent(contact = dummyContact) }
}

const val NavArgKeyContactId = "contactId"
const val InvalidContactId = -99