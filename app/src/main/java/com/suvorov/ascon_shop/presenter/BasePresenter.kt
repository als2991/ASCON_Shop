package com.suvorov.ascon_shop.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.suvorov.ascon_shop.domain.RemoteProduct
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext


@InjectViewState
abstract class BasePresenter<TView: MvpView>: MvpPresenter<TView>(), CoroutineScope{

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + job + CoroutineExceptionHandler {context_, e->
            onFailure(e)
        }
    open fun onFailure(e: Throwable){}

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun getDiscountPrice(product: RemoteProduct): Double =
        product.price * (100 - product.discountPercent) /100

    //from SDK level < 29
        fun hasConnection(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var result = false

        if(activeNetwork != null) result = activeNetwork.isConnectedOrConnecting
        return result
    }


}