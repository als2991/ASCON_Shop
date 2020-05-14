package com.suvorov.ascon_shop.presenter

import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.ViewedProductDao
import com.suvorov.ascon_shop.domain.model.CreateOrderModel
import com.suvorov.ascon_shop.ui.CreateOrderView
import kotlinx.coroutines.launch
import moxy.InjectViewState

@InjectViewState
class CreateOrderPresenter(
    private val viewedProductDao: ViewedProductDao,
    private val mainApi: MainApi
): BasePresenter<CreateOrderView>() {

    private val model = CreateOrderModel()

    private fun checkSymbols(text: String):Boolean = text.isEmpty()

    private fun checkEmail(text: String):Boolean {
        val regex = Regex(pattern = "[A-Za-z0-9_-]+@+[A-Za-z0-9_-]+[.]+[a-zA-Z]{2,6}")
        return !regex.containsMatchIn(text)
    }

    fun checkEditText(
        text: String,
        fieldType: FieldType
    ) {
        when(fieldType){
            FieldType.ORGANIZATION -> model.organization
            FieldType.FIO -> model.fio
            FieldType.PHONE -> model.userPhone
            FieldType.EMAIL -> model.userEmail
        }
        if (fieldType == FieldType.EMAIL) viewState.showErrorForEditText(checkEmail(text),fieldType)
        else viewState.showErrorForEditText(checkSymbols(text), fieldType)
    }

    fun onClickOrderPlace() {
        viewedProductDao.clearProductInBasket()
//        launch {
//             mainApi.create("suvorov",model)
//        }
        viewState.moveMainActivity()
    }
}

enum class FieldType {
    ORGANIZATION,FIO, POSITION, PHONE, EMAIL, NONE
}