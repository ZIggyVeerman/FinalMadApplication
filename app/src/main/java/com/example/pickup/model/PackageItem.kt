package com.example.pickup.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//TODO juiste data object vinden
@Parcelize
data class PackageItem (
    @SerializedName("package") var title: String
) : Parcelable