package com.suvorov.ascon_shop.presenter

import android.app.Application
import android.content.Context
import android.util.Log
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.Basket
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.domain.ViewedProductDao
import com.suvorov.ascon_shop.ui.ProductView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@InjectViewState
class ProductPresenter(
    private val mainApi: MainApi,
    private val viewedProductDao: ViewedProductDao,
    private val context: Context
): BasePresenter<ProductView>() {

    private var tagCategory: String = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if(!hasConnection(context = context)) viewState.showError(context.getString(R.string.no_connect_internet))

        launch {
            val remoteProduct = mainApi.allProduct("suvorov")
            val filterRemoteProduct = remoteProduct.filter { it.tag == tagCategory }
            viewState.setProduct(filterRemoteProduct)
        }
    }

    fun getCategory(category: RemoteCategory){
        this.tagCategory = category.tag
    }


    fun onProductClick(product: RemoteProduct) {
        viewState.onShowProduct(product)
    }

    fun addProductInBasket(product: RemoteProduct){
        viewedProductDao.addProductInBasket(product.id)
        viewState.onAddProductMessage(product.name)
    }

    override fun onFailure(e: Throwable) {
        super.onFailure(e)
        if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
            viewState.showError(e.message)
    }
}






