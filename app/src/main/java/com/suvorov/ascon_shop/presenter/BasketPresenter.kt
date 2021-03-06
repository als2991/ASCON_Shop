package com.suvorov.ascon_shop.presenter

import android.content.Context
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.domain.ViewedProductDao
import com.suvorov.ascon_shop.ui.BasketView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@InjectViewState
class BasketPresenter(
    private val mainApi: MainApi,
    private val viewedProductDao: ViewedProductDao,
    private val context: Context
): BasePresenter<BasketView>() {

    private var productsIdInBasket: MutableList<String> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        productsIdInBasket.addAll(viewedProductDao.getAllProducts())

        if(!hasConnection(context = context)) viewState.showMessage(context.getString(R.string.no_connect_internet))
        else
            launch {
            val remoteProduct = mainApi.allProduct()
            val productsInBasket = remoteProduct.filter { productsIdInBasket.contains(it.id) }
            viewState.setBasket(productsInBasket)
        }
    }

    fun deleteProductInBasket(product: RemoteProduct, position: Int){
        viewedProductDao.deleteProductInBasket(product.id)
        viewState.removeItem(product, position)
    }

    fun getTotalPrice(basketProduct: List<RemoteProduct>): String {
        var totalPrice = 0.0

        basketProduct.forEach {
            totalPrice += getDiscountPrice(it)
        }
    return "$totalPrice"
    }

    override fun onFailure(e: Throwable) {
        super.onFailure(e)
        if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
            viewState.showMessage(e.message)
    }
}