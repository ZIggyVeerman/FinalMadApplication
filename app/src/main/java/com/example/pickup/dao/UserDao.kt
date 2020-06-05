package com.example.pickup.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pickup.model.User

@Dao
interface UserDao {

    //TODO JUISTE QUERY MAKEN
    @Query("SELECT * FROM user_table ORDER BY id LIMIT 1")
    fun getUser(): LiveData<User>

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}