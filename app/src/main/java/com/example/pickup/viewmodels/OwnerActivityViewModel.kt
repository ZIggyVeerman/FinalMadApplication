package com.example.pickup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickup.repository.PackageInfoRepository
import kotlinx.coroutines.launch
import java.util.*

class OwnerActivityViewModel : ViewModel() {
    private val packageInfoRepository = PackageInfoRepository()

    val overview = packageInfoRepository.ownerPackageItem
//    val packages = packageInfoRepository.packageItem

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    fun addNewUser(postalCode: String, homeNumber: Int) {
        viewModelScope.launch {
            try {
                packageInfoRepository.addNewUser(postalCode, homeNumber)
            } catch (error: PackageInfoRepository.UserCreateError) {
                _errorText.value = error.message
            }
        }
    }

    fun getPackageForOwner(postalCode: String, homeNumber: Int) {
        viewModelScope.launch {
            try {
                packageInfoRepository.getPackageForOwner(postalCode, homeNumber)
            } catch (error: PackageInfoRepository.PackageRefreshError) {
                _errorText.value = error.message
            }
        }
    }

    fun handleYes(
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        receivedPostalCode: String,
        receivedHomeNumber: Int
    ) {
        viewModelScope.launch {
            try {
                packageInfoRepository.updatePickupYes(
                    ownerPostalCode, ownerHomeNumber, receivedPostalCode, receivedHomeNumber
                )
            } catch (error: PackageInfoRepository.PickupUpdateError) {
                _errorText.value = error.message
            }
        }
    }

    fun handleNo(
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        receivedPostalCode: String,
        receivedHomeNumber: Int,
        newDateTime: Date
    ) {
        viewModelScope.launch {
            try {
                packageInfoRepository.updatePickupNo(
                    ownerPostalCode,
                    ownerHomeNumber,
                    receivedPostalCode,
                    receivedHomeNumber,
                    newDateTime
                )
            } catch (error: PackageInfoRepository.PickupUpdateError) {
                _errorText.value = error.message
            }
        }
    }
}