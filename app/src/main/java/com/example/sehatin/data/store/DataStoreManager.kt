package com.example.sehatin.data.store


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sehatin.data.model.response.Detail
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }

    val onboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[ONBOARDING_COMPLETED_KEY] ?: false }

    suspend fun setUserLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_LOGGED_IN_KEY] = loggedIn
        }
    }

    val isUserLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[USER_LOGGED_IN_KEY] ?: false }

    suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }



    val userToken: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[USER_TOKEN_KEY] }


    suspend fun setPersonalizedFilled(filled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PERSONALIZED_KEY] = filled
        }
    }

    val personalizedFilled: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PERSONALIZED_KEY] ?: false }

    suspend fun saveUserDetail(detail: Detail) {
        val gson = Gson()
        val detailJson = gson.toJson(detail)
        context.dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = detailJson
        }
    }

    suspend fun getUserDetail(): Detail? {
        val preferences = context.dataStore.data.first()
        val detailJson = preferences[USER_DATA_KEY] ?: return null
        return Gson().fromJson(detailJson, Detail::class.java)
    }

    suspend fun isPersonalizeCompleted(): Boolean {
        val detail = getUserDetail()
        return detail?.isProfileComplete() ?: false
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun clearUserToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }
    suspend fun logOut() {



        context.dataStore.edit { preferences ->
            preferences.remove(USER_LOGGED_IN_KEY)
            preferences.remove(USER_TOKEN_KEY)
            preferences.remove(USER_DATA_KEY)
//            preferences.remove(USER_DATA_KEY)
        }

    }


    companion object {
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        private val USER_LOGGED_IN_KEY = booleanPreferencesKey("user_logged_in")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_DATA_KEY = stringPreferencesKey("user_data")
        private val PERSONALIZED_KEY = booleanPreferencesKey("personalized_filled")
    }
}