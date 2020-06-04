package com.example.pickup.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pickup.api.PackageApi
import com.example.pickup.api.PackageApiService
import com.example.pickup.model.PackageItem

class PackageInfoRepository {
    private val packageApi: PackageApiService = PackageApi.createApi()

    private val _package: MutableLiveData<ArrayList<PackageItem>> = MutableLiveData()

    val packageItem: LiveData<ArrayList<PackageItem>>
        get() = _package

    suspend fun getAllPackagesReceived(postalCode: String, homeNumber: Number) {
        try {
            var result = packageApi.getAllPackagesReceived(postalCode, homeNumber)

            _package.value = result.packages
        } catch (error: Throwable) {
            throw PackageRefreshError(
                "Unable to fetch packages", error
            )
        }

    }


    class PackageRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}