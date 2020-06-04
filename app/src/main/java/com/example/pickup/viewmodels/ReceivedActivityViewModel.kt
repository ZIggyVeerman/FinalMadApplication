package com.example.pickup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pickup.repository.PackageInfoRepository

class ReceivedActivityViewModel : ViewModel() {
    private val packageInfoRepository = PackageInfoRepository()

    val packages = packageInfoRepository.packageItem

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    //TODO functies voor ophalen van de lijst en inserten in de lijst

}