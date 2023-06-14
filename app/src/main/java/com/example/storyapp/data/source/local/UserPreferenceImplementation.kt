package com.example.storyapp.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyapp.domain.entities.UserEntity
import com.example.storyapp.domain.interfaces.UserPrefRepository
import com.example.storyapp.utils.Constant.PREF_ID
import com.example.storyapp.utils.Constant.PREF_NAME
import com.example.storyapp.utils.Constant.PREF_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserPreferenceImplementation(private val dataStore: DataStore<Preferences>) :
    UserPrefRepository {
    private object Keys {
        val id = stringPreferencesKey(PREF_ID)
        val name = stringPreferencesKey(PREF_NAME)
        val token = stringPreferencesKey(PREF_TOKEN)
    }

    private inline val Preferences.id
        get() = this[Keys.id] ?: ""
    private inline val Preferences.name
        get() = this[Keys.name] ?: ""
    private inline val Preferences.token
        get() = this[Keys.token] ?: ""

    override val userData: Flow<UserEntity> = dataStore.data.map {
        UserEntity(
            id = it.id, name = it.name, token = it.token
        )
    }.distinctUntilChanged()

    override suspend fun saveUser(userEntity: UserEntity) {
        dataStore.edit {
            it[Keys.id] = userEntity.id
            it[Keys.name] = userEntity.name
            it[Keys.token] = userEntity.token
        }
    }

    override suspend fun clearUser() {
        dataStore.edit {
            it.clear()
        }
    }
}