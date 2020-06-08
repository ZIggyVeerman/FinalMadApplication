package com.example.pickup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickup.repository.PackageInfoRepository
import kotlinx.coroutines.launch
import java.util.*

class ReceivedActivityViewModel : ViewModel() {
    private val packageInfoRepository = PackageInfoRepository()

    val packages = packageInfoRepository.packageItem

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    fun getPackages(postalCode: String, homeNumber: Int) {
        viewModelScope.launch {
            try {
                packageInfoRepository.getAllPackagesReceived(postalCode, homeNumber)
            } catch (error: PackageInfoRepository.PackageRefreshError) {
                _errorText.value = error.message
            }
        }
    }

    fun addNewPackage(
        postalCode: String,
        homeNumber: Int,
        packageName: String,
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        pickUpTime: Date
    ) {
        viewModelScope.launch {
            try {
                packageInfoRepository.addNewPackage(
                    postalCode,
                    homeNumber,
                    packageName,
                    ownerPostalCode,
                    ownerHomeNumber,
                    pickUpTime
                )
            } catch (error: PackageInfoRepository.PackageCreateError) {
                _errorText.value = error.message
            }
        }
    }
}