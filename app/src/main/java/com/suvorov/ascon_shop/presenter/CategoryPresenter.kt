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
import java.time.Year
import java.util.*

@InjectViewState
class CategoryPresenter (
    private val mainApi: MainApi,
    private val context: Context
) : BasePresenter<CategoryView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if(!hasConnection(context = context)) viewState.showError(context.getString(R.string.no_connect_internet))

        launch {
            val remoteCategory = mainApi.allCategory("suvorov")
            viewState.setCategory(remoteCategory)
        }

    }

    fun onCategoryClick(category: RemoteCategory){
        viewState.onShowCategoryProducts(category)
    }

    override fun onFailure(e: Throwable) {
        super.onFailure(e)
        if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
            viewState.showError(e.message)

    }


}