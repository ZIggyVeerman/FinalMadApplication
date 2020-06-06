package com.example.pickup.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.pickup.model.User
import com.example.pickup.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application.applicationContext)
    private val ioScope = CoroutineScope(Dispatchers.IO)

    val user: LiveData<User>? = userRepository.getUser()

    fun insertUser(user: User) {
        ioScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: User) {
        ioScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        ioScope.launch {
            userRepository.deleteAllUsers()
        }
    }

}

