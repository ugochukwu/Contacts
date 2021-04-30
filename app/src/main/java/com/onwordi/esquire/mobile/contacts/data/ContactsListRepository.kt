package com.onwordi.esquire.mobile.contacts.data

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ContactsListRepository {
    private var cache: List<Contact>? = null
    private val endpoint = "https://jsonplaceholder.typicode.com/users"
    private val client by lazy {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    suspend fun getContacts(): List<Contact> = withContext(Dispatchers.IO) {
        cache ?: client.use { client -> client.get<List<Contact>>(endpoint).also { cache = it } }
    }

    fun getContact(id: Int): Contact? = cache?.find { it.id == id }

    fun setFavoriteStatusForContact(id: Int, favorite: Boolean): List<Contact>? {
        return cache?.toMutableList()?.map { if (it.id == id) it.copy(favorite = favorite) else it }
            .also { cache = it }
    }

    companion object {
        private var repo: ContactsListRepository? = null
        fun repo(): ContactsListRepository = repo ?: ContactsListRepository().also {
            repo = it
        }
    }
}
