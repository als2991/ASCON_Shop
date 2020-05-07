package com.suvorov.ascon_shop.presenter

import android.util.Log
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.ui.AboutProductView
import kotlinx.coroutines.launch
import moxy.InjectViewState

@InjectViewState
class AboutProductPresenter: BasePresenter<AboutProductView>(){

    fun setProduct(product: RemoteProduct){

    }

}