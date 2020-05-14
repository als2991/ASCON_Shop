package com.suvorov.ascon_shop.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.domain.ViewedProductDao
import kotlinx.coroutines.launch


class ViewedProductDaoIml(
    //private val mainApi: MainApi,
    private val sharedPreferences: SharedPreferences
): ViewedProductDao {


    private var savedProductsId: List<String>

        get() = sharedPreferences.getString(PRODUCT, null)
            ?.split("|")
            ?.map { it } ?: emptyList()

        set(value) {
            sharedPreferences.edit {
                putString(PRODUCT, value.joinToString("|"))
            }
        }

    override fun addProductInBasket(id: String) {
        val productIds = mutableListOf<String>().apply {
            addAll(savedProductsId)
            if (!savedProductsId.contains(id)) add(id)
        }
        savedProductsId =  productIds
    }

    override fun deleteProductInBasket(id: String) {
        val productIds = mutableListOf<String>().apply {
            addAll(savedProductsId.filter { it != id })
        }
        savedProductsId =  productIds
    }

    override fun getAllProducts(): List<String> {
        return savedProductsId
    }

    override fun clearProductInBasket() {
        sharedPreferences.edit().remove(PRODUCT).apply()
    }

    companion object  {
        private const val PRODUCT = "PRODUCT"
    }
}



