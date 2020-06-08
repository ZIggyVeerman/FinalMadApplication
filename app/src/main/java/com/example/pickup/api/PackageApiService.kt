package com.example.pickup.api

import com.example.pickup.model.PackageOverviewResponse
import com.example.pickup.model.ResultSetWithPackages
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*
import kotlin.collections.ArrayList

interface PackageApiService {
    @FormUrlEncoded
    @POST("/api/v1/newUser")
    suspend fun addNewUser(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int
    )

    @FormUrlEncoded
    @POST("api/v1/newPackage")
    suspend fun addNewPackage(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int,
        @Field("packageName") packageName: String,
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Int,
        @Field("pickupTime") pickUpTime: Date
    )

    @FormUrlEncoded
    @POST("/api/v1/getAllPackagesReceived")
    suspend fun getAllPackagesReceived(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int
    ): ResultSetWithPackages

    @FormUrlEncoded
    @POST("/api/v1/getPackageForUser")
    suspend fun getPackageForOwner(
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Int
    ): ArrayList<PackageOverviewResponse>

    @FormUrlEncoded
    @POST("/api/v1/updatePickupYes")
    suspend fun updatePickUpYes(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int,
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Int
    )

    @FormUrlEncoded
    @POST("/api/v1/updatePickupNo")
    suspend fun updatePickUpNo(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int,
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Int,
        @Field("newDateTime") newDateTime: Date
    )

    @FormUrlEncoded
    @POST("/api/v1/agreeNewDate")
    suspend fun agreeNewDate(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int,
        @Field("ownerPostalCode") ownerPostalCode: String,
        @Field("ownerHomeNumber") ownerHomeNumber: Int
    )

    @FormUrlEncoded
    @DELETE("/packageReceived")
    suspend fun packageReceived(
        @Field("postalCode") postalCode: String,
        @Field("homeNumber") homeNumber: Int,
        @Field("packageId") packageId: Int
    )
}