package com.example.pickup.viewmodels

import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    var ownerPostalCode: String = ""
    var ownerHomeNumber: Int = 0
    var receivedPostalCode: String = ""
    var receivedHomeNumber: Int = 0

    fun storeFields(
        ownerPostalCode: String,
        ownerHomeNumber: Int,
        receivedPostalCode: String,
        receivedHomeNumber: Int
    ) {
        this.ownerPostalCode = ownerPostalCode
        this.ownerHomeNumber = ownerHomeNumber
        this.receivedPostalCode = receivedPostalCode
        this.receivedHomeNumber = receivedHomeNumber
    }


}