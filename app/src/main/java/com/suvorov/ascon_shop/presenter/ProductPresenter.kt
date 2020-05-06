package com.suvorov.ascon_shop.presenter

import android.util.Log
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.ui.ProductView
import kotlinx.coroutines.launch
import moxy.InjectViewState

@InjectViewState
class ProductPresenter(
    private val mainApi: MainApi
): BasePresenter<ProductView>() {

    private var tagCategory: String = ""

    override fun attachView(view: ProductView?) {
        super.attachView(view)
        Log.d("myDebug","attachView")
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("myDebug","onFirstViewAttachStart")

        launch {
            Log.d("myDebug","onFirstViewAttachLaunch")
            val remoteProduct = mainApi.allProduct("suvorov")
            remoteProduct.filter { it.tag == "engin" }
            viewState.setProduct(remoteProduct)
        }
    }


    fun getCategory(category: RemoteCategory){
        this.tagCategory = category.tag
    }

    fun getDiscountPrice(product: RemoteProduct): Double =
        product.price * (100 - product.discountPercent) /100

    fun onProductClick(product: RemoteProduct) {

    }
}






