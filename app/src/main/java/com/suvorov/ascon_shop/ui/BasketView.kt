package com.suvorov.ascon_shop.ui

import com.suvorov.ascon_shop.domain.RemoteProduct
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BasketView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(list: List<RemoteProduct>){}
}