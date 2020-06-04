package com.example.pickup.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pickup.dao.UserDao
import com.example.pickup.database.UserLocalRoomDatabase
import com.example.pickup.model.User

class UserRepository(context: Context) {

    private var userDao: UserDao?

    init {
        val database = UserLocalRoomDatabase.getDatabase(context)
        userDao = database?.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return userDao?.getAllGames() ?: MutableLiveData(emptyList())
    }

    suspend fun insertUser(user: User) {
        userDao?.insterUser(user)
    }

}