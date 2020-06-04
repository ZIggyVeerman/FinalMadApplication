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

    suspend fun getPackage() {

    }


    class PackageRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
}