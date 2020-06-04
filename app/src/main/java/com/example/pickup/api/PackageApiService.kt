package com.example.pickup.api

import com.example.pickup.model.ResultSetWithPackages
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface PackageApiService {
    @FormUrlEncoded
    @POST("/newPackage")
    suspend fun addNewPackage(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Number,
        @Field("packageName") packageName: String,
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Number,
        @Field("pickupTime") pickUpTime: Date
        )
    @FormUrlEncoded
    @POST("/getAllPackagesReceived")
    suspend fun getAllPackagesReceived(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Number
    ) : ResultSetWithPackages

//    @FormUrlEncoded
//    @POST("/addNewUser")
//    suspend fun addNewUser(
//
//    )
//    @FormUrlEncoded
//    @POST("")

}