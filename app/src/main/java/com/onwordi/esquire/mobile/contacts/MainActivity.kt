package com.onwordi.esquire.mobile.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.onwordi.esquire.mobile.contacts.contactdetails.ContactDetailsScreen
import com.onwordi.esquire.mobile.contacts.contactdetails.NavArgKeyContactId
import com.onwordi.esquire.mobile.contacts.contactdetails.ContactDetailsViewModel
import com.onwordi.esquire.mobile.contacts.contactslist.ContactsListScreen
import com.onwordi.esquire.mobile.contacts.contactslist.ContactsListViewModel
import com.onwordi.esquire.mobile.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO use hilt instead
        val contactsListViewModel by viewModels<ContactsListViewModel>()
        val contactDetailsViewModel by viewModels<ContactDetailsViewModel>()

        setContent {
            ContactsTheme {

                ContactAppNavigation(
                    listScreen = { navController ->
                        ContactsListScreen(contactsListViewModel, onContactClick = {
                            navController.navigate("${Details}/${it.id}")
                        })
                    },
                    detailScreen = { backStackEntry ->
                        ContactDetailsScreen(backStackEntry.arguments?.getInt(NavArgKeyContactId),
                            contactDetailsViewModel)
                    })

            }
        }
    }
}

@Composable
fun ContactAppNavigation(
    listScreen: @Composable (navController: NavHostController) -> Unit,
    detailScreen: @Composable (backStackEntry: NavBackStackEntry) -> Unit,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Contacts) {
        composable(Contacts) {
            listScreen(navController)
        }
        composable(
            route = "${Details}/{$NavArgKeyContactId}",
            arguments = listOf(navArgument(NavArgKeyContactId) {
                type = NavType.IntType
            })) {
            detailScreen(it)
        }
    }
}

const val Contacts = "ContactList"
const val Details = "Details"

