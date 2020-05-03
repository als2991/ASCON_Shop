package com.suvorov.ascon_shop.presenter

import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.ui.CatalogView
import kotlinx.coroutines.launch
import moxy.InjectViewState

@InjectViewState
class CatalogPresenter (
    private val mainApi: MainApi
) : BasePresenter<CatalogView>(){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            val remoteCategory = mainApi.allCategory("suvorov")
            val categoryName = remoteCategory.map { remoteCategory -> remoteCategory.name }
            viewState.setCategory(categoryName)
        }
    }



}