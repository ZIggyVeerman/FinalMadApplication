package com.example.pickup.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserWithPackageItem (
    @SerializedName("postalCode") var postalCode: String,
    @SerializedName("homeNumber") var homeNumber: Int
    ): Parcelable