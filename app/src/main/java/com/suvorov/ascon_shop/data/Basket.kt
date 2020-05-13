package com.suvorov.ascon_shop.data

import android.app.Application
import com.suvorov.ascon_shop.domain.RemoteProduct

class Basket: Application() {
    var allBasketProduct: MutableList<RemoteProduct> = mutableListOf()

    fun addProductInBasket(product: RemoteProduct){
        allBasketProduct.add(product)
    }
}