package com.example.pickup.model

import com.google.gson.annotations.SerializedName

data class ResultSetWithPackages(
    @SerializedName("packages") var packages: ArrayList<PackageItem>
)