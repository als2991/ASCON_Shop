package com.suvorov.ascon_shop.presenter

import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.ui.CategoryView
import kotlinx.coroutines.launch
import moxy.InjectViewState

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



}