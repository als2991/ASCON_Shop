package com.suvorov.ascon_shop.presenter

import com.suvorov.ascon_shop.ui.BasketView
import moxy.InjectViewState

@InjectViewState
class BasketPresenter: BasePresenter<BasketView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setData(allBasketProduct)
    }

}