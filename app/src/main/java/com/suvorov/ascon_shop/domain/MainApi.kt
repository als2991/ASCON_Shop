package com.suvorov.ascon_shop.domain

import android.os.Parcelable
import android.provider.ContactsContract
import com.suvorov.ascon_shop.domain.model.CreateOrderModel
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.net.URL

@Parcelize
data class RemoteProduct(
    val id: String,
    val tag: String,
    val name: String,
    val price: Double,
    val discountPercent: Int,
    val description: String,
    val imageUrl: String,
    val attributes: List<Attribute>
): Parcelable {

    @Parcelize
    data class Attribute(
        val name: String,
        val value: String
    ): Parcelable

}

@Parcelize
data class RemoteCategory(
    val id: String,
    val tag: String,
    val name: String,
    val description: String,
    val imageUrl: String
): Parcelable

interface MainApi {

    @GET("categories/{author}")
    suspend fun allCategory(@Path("author") author: String): List<RemoteCategory>

    @GET("products/{author}")
    suspend fun allProduct(@Path("author") author: String): List<RemoteProduct>

//    @POST("orders/new/{author}")
//    suspend fun create(
//        @Path("author")author: String,
//        @Path("order") order: CreateOrderModel)

}