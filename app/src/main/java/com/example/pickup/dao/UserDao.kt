package com.example.pickup.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pickup.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insterUser(user: User)

    //TODO JUISTE QUERY MAKEN
    @Query("SELECT * FROM user_table")
    fun getAllGames(): LiveData<List<User>>

}