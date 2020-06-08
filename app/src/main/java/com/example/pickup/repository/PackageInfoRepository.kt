package com.example.pickup.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pickup.api.PackageApi
import com.example.pickup.api.PackageApiService
import com.example.pickup.model.PackageItem
import com.example.pickup.model.PackageOverviewResponse
import java.util.*
import kotlin.collections.ArrayList

class PackageInfoRepository {
    private val packageApi: PackageApiService = PackageApi.createApi()

    private val _package: MutableLiveData<ArrayList<PackageItem>> = MutableLiveData()

    val packageItem: LiveData<ArrayList<PackageItem>>
        get() = _package

    private val _ownerPackage: MutableLiveData<ArrayList<PackageOverviewResponse>> =
        MutableLiveData()

    val ownerPackageItem: LiveData<ArrayList<PackageOverviewResponse>>
        get() = _ownerPackage

    suspend fun addNewUser(postalCode: String, homeNumber: Int) {
        try {
            packageApi.addNewUser(postalCode, homeNumber)
        } catch (error: Throwable) {
            throw UserCreateError(
                "Unable to create user", error
            )
        }
    }

    suspend fun addNewPackage(
        postalCode: String,
        homeNumber: Int,
        packageName: String,
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        pickupTime: Date
    ) {
        try {
            packageApi.addNewPackage(
                postalCode,
                homeNumber,
                packageName,
                ownerPostalCode,
                ownerHomeNumber,
                pickupTime
            )
        } catch (error: Throwable) {
            throw PackageCreateError(
                "Unable to create package", error
            )
        }
    }

    suspend fun getAllPackagesReceived(postalCode: String, homeNumber: Int) {
        try {
            val result = packageApi.getAllPackagesReceived(postalCode, homeNumber)

            _package.value = result.packages
        } catch (error: Throwable) {
            throw PackageRefreshError(
                "Unable to fetch packages", error
            )
        }

    }

    suspend fun getPackageForOwner(ownerPostalCode: String, ownerHomeNumber: Int) {
        try {
            val result = packageApi.getPackageForOwner(ownerPostalCode, ownerHomeNumber)

            _ownerPackage.value = result

        } catch (error: Throwable) {
            throw PackageRefreshError(
                "Unable to fetch packages", error
            )
        }
    }

    suspend fun updatePickupYes(
        postalCode: String,
        homeNumber: Int,
        ownerPostalCode: String,
        ownerHomeNumber: Int
    ) {
        try {
            packageApi.updatePickUpYes(
                postalCode,
                homeNumber,
                ownerPostalCode,
                ownerHomeNumber
            )
        } catch (error: Throwable) {
            throw PickupUpdateError(
                "Unable to update package", error
            )
        }
    }

    suspend fun updatePickupNo(
        postalCode: String,
        homeNumber: Int,
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        newDateTime: Date
    ) {
        try {
            packageApi.updatePickUpNo(
                postalCode,
                homeNumber,
                ownerPostalCode,
                ownerHomeNumber,
                newDateTime
            )
        } catch (error: Throwable) {
            throw PickupUpdateError(
                "Unable to update package", error
            )
        }
    }

    suspend fun agreeNewDate(
        postalCode: String,
        homeNumber: Int,
        ownerPostalCode: String,
        ownerHomeNumber: Int
    ) {
        try {
            packageApi.agreeNewDate(
                postalCode,
                homeNumber,
                ownerPostalCode,
                ownerHomeNumber
            )
        } catch (error: Throwable) {
            throw PickupUpdateError(
                "Unable to agree new date", error
            )
        }
    }

    suspend fun packageReceived(
        postalCode: String,
        homeNumber: Int,
        packageId: Int
    ) {
        try {
            packageApi.packageReceived(
                postalCode, homeNumber, packageId
            )
        } catch (error: Throwable) {
            throw PackageDeleteError(
                "Unable to delete package", error
            )
        }
    }

    class PackageRefreshError(message: String, cause: Throwable) : Throwable(message, cause)
    class UserCreateError(message: String, cause: Throwable) : Throwable(message, cause)
    class PackageCreateError(message: String, cause: Throwable) : Throwable(message, cause)
    class PickupUpdateError(message: String, cause: Throwable) : Throwable(message, cause)
    class PackageDeleteError(message: String, cause: Throwable) : Throwable(message, cause)
}