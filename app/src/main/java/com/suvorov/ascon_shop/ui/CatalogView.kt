package com.suvorov.ascon_shop.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CatalogView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setCategory(list: List<String>)
}