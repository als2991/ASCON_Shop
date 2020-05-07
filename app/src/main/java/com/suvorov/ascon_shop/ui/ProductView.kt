package com.suvorov.ascon_shop.ui

import com.suvorov.ascon_shop.domain.RemoteProduct
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ProductView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProduct(product: List<RemoteProduct>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onShowProduct(product: RemoteProduct)
}