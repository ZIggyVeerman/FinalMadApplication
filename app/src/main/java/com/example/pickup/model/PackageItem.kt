package com.example.pickup.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PackageItem(
    @SerializedName("packageId") var packageId: Int,
    @SerializedName("packageName") var packageName: String,
    @SerializedName("ownerPostalCode") var ownerPostalCode: String,
    @SerializedName("ownerHomeNumber") var ownerHomeNumber: Int,
    @SerializedName("pickUpTime") var pickUpTime: Date,
    @SerializedName("willPickUp") var willPickUp: Boolean
) : Parcelable