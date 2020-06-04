package com.example.pickup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pickup.dao.UserDao
import com.example.pickup.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserLocalRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "USER_DATABASE"

        @Volatile
        private var INSTANCE: UserLocalRoomDatabase? = null

        fun getDatabase(context: Context): UserLocalRoomDatabase? {
            if (INSTANCE != null) return INSTANCE

            synchronized(UserLocalRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserLocalRoomDatabase::class.java, DATABASE_NAME
                    )
                        .build()

                }
            }
            return INSTANCE
        }
    }

}