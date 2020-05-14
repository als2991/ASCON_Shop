package com.suvorov.ascon_shop.ui

import com.suvorov.ascon_shop.domain.RemoteCategory
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CategoryView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setCategory(list: List<RemoteCategory>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onShowCategoryProducts(category: RemoteCategory)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(text: String?)

}