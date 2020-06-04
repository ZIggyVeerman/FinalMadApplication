package com.example.pickup.model

import com.google.gson.annotations.SerializedName

data class ResultSetWithPackages(
    @SerializedName("package") var packages: ArrayList<PackageItem>
)