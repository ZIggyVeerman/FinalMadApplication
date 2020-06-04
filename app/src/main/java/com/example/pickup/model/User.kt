package com.example.pickup.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "postalCode")
    var postalCode: String,

    @ColumnInfo(name = "homeNumber")
    var homeNumber: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
) : Parcelable