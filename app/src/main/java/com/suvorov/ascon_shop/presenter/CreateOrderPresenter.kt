package com.suvorov.ascon_shop.presenter

import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
import android.widget.EditText
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

    private var totalPrice = ""

    private var editList = listOf<EditText>()

    private val model = CreateOrderModel()

    private fun checkSymbols(text: String):Boolean = text.isEmpty()

    private fun checkEmail(text: String):Boolean {
        val regex = Regex(pattern = "[A-Za-z0-9_-]+@+[A-Za-z0-9_-]+[.]+[a-zA-Z]{2,6}")
        return !regex.containsMatchIn(text)
    }

    fun checkEditText(
        text: String,
        fieldType: FieldType?
    ) {
        when(fieldType){
            FieldType.ORGANIZATION -> model.organization
            FieldType.FIO -> model.fio
            FieldType.PHONE -> model.userPhone
            FieldType.EMAIL -> model.userEmail
            else -> Unit
        }
        if (fieldType == FieldType.EMAIL)
            fieldType.let{viewState.showErrorForEditText(checkEmail(text),it)}
        else
            fieldType?.let { viewState.showErrorForEditText(checkSymbols(text), it) }
    }

    fun onClickOrderPlace() {

        //Проверка на незаполненные поля
        editList.forEach {
            if (checkSymbols(it.text.toString())) {
                viewState.showMessage("Заполните все поля")
                return
            }
            if (it.hint == "Email")
                if (checkEmail(it.text.toString())) {
                    viewState.showMessage("Введите корректный Email")
                    return
                }
        }

        model.totalPrice = totalPrice

        viewedProductDao.clearProductInBasket()

        launch {
             mainApi.addOrder(model)
        }

        viewState.moveMainActivity()
    }

    fun getInfoWithActivity(totalPrice: String, list:List<EditText>){
        this.totalPrice = totalPrice
        this.editList = list
    }
}

enum class FieldType {
    ORGANIZATION,FIO, POSITION, PHONE, EMAIL, NONE
}