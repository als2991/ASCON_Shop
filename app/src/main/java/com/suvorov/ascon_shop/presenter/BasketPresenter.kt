package com.suvorov.ascon_shop.presenter

import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.domain.ViewedProductDao
import com.suvorov.ascon_shop.ui.BasketView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.text.FieldPosition

@InjectViewState
class BasketPresenter(
    private val mainApi: MainApi,
    private val viewedProductDao: ViewedProductDao
): BasePresenter<BasketView>() {

    private var productsIdInBasket: MutableList<String> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        productsIdInBasket.addAll(viewedProductDao.getAllProducts())

        launch {
            val remoteProduct = mainApi.allProduct("suvorov")
            val productsInBasket = remoteProduct.filter { productsIdInBasket.contains(it.id) }
            viewState.setData(productsInBasket)
        }
    }

    fun deleteProductInBasket(product: RemoteProduct, position: Int){
        viewedProductDao.deleteProductInBasket(product.id)
        productsIdInBasket.remove(product.id)
        viewState.removeItem(position)
    }

}