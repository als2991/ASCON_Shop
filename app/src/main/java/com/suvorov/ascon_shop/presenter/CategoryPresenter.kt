package com.suvorov.ascon_shop.presenter

import android.content.Context
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.ui.CategoryView
import kotlinx.coroutines.launch
import moxy.InjectViewState
import java.time.Year
import java.util.*

@InjectViewState
class CategoryPresenter (
    private val mainApi: MainApi
) : BasePresenter<CategoryView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            val remoteCategory = mainApi.allCategory("suvorov")
            viewState.setCategory(remoteCategory)
        }

    }

    fun onCategoryClick(category: RemoteCategory){
        viewState.onShowCategoryProducts(category)
    }


}