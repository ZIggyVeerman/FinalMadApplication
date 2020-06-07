package com.example.pickup.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pickup.repository.PackageInfoRepository

class OwnerActivityViewModel: ViewModel() {
    private val packageInfoRepository = PackageInfoRepository()

    val packages = packageInfoRepository.packageItem

}