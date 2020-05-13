package com.suvorov.ascon_shop.data

import android.content.SharedPreferences
import android.os.Build.PRODUCT
import androidx.core.content.edit
import com.google.gson.Gson
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.domain.ViewProductDao
import java.util.*


class ViewProductDaoIml(
    private val sharedPreferences: SharedPreferences
): ViewProductDao {


    private var savedProduct: MutableList<String>

        get() = sharedPreferences.getString(PRODUCT, null)
            ?.split("|")
            ?.map { it.toString() } as MutableList<String>

        set(value) {
            sharedPreferences.edit {
                putString(PRODUCT, value.joinToString("|"))
            }
        }

    override fun addProductInBasket(product: RemoteProduct) {
        val json = Gson().toJson(product)
        savedProduct.add(json)
        }

    override fun getAllProducts(): List<RemoteProduct> {
        //Gson().fromJson(PRODUCT,)
        TODO("")

    }

    companion object  {
        private const val PRODUCT = "PRODUCT"
    }
}



