package com.suvorov.ascon_shop.presenter

import android.content.Context
import com.suvorov.ascon_shop.R
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

        if(!hasConnection(context = context)) viewState.showMessage(context.getString(R.string.no_connect_internet))
        else
            launch {
            val remoteProduct = mainApi.allProduct()
            val filterRemoteProduct = remoteProduct.filter { it.tag == tagCategory }
            viewState.setProduct(filterRemoteProduct)
        }
    }

    fun getCategory(category: RemoteCategory){
        this.tagCategory = category.tag
    }

    fun onProductClick(product: RemoteProduct) {
        viewState.onShowAboutProduct(product)
    }

    fun addProductInBasket(product: RemoteProduct){
        viewedProductDao.addProductInBasket(product.id)
        viewState.showMessage("Товар ${product.name} добавлен в корзину")
    }

    override fun onFailure(e: Throwable) {
        super.onFailure(e)
        if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
            viewState.showMessage(e.message)
    }
}






