package com.suvorov.ascon_shop.domain

import retrofit2.http.GET
import retrofit2.http.Path

data class RemoteProduct(
    val id: String,
    val tag: String,
    val name: String,
    val price: Double,
    val discountPercent: Int,
    val description: String,
    val imageUrl: String,
    val attributes: List<Attribute>
) {
    data class Attribute(
        val name: String,
        val value: String
    )
}

data class RemoteCategory(
    val id: String,
    val name: String,
    val imageUrl: String
)

interface MainApi {

    @GET("categories/{author}")
    suspend fun allCategory(@Path("author") author: String): List<RemoteCategory>

    @GET("products/{author}")
    suspend fun allProduct(@Path("author") author: String): List<RemoteProduct>

}