package com.suvorov.ascon_shop.ui

import com.suvorov.ascon_shop.presenter.FieldType
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CreateOrderView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorForEditText(visible: Boolean, fieldType: FieldType)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun moveMainActivity ()


}