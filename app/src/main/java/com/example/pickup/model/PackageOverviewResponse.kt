package com.example.pickup.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PackageOverviewResponse (
    @SerializedName("postalCode") var postalCode: String,
    @SerializedName("homeNumber") var homeNumber: Int,
    @SerializedName("package") var packages: ArrayList<PackageItem>
) : Parcelable