package com.suvorov.ascon_shop.presenter

import android.content.Context
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.ui.CategoryView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@InjectViewState
class CategoryPresenter (
    private val mainApi: MainApi,
    private val context: Context
) : BasePresenter<CategoryView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if(!hasConnection(context = context)) viewState.showMessage(context.getString(R.string.no_connect_internet))
        else
        launch {
            val remoteCategory = mainApi.allCategory()
            viewState.setCategory(remoteCategory)
        }
    }

    fun onCategoryClick(category: RemoteCategory){
        viewState.onShowCategoryProducts(category)
    }

    override fun onFailure(e: Throwable) {
        super.onFailure(e)
        if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
            viewState.showMessage(e.message)
    }

}