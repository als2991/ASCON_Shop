package com.suvorov.ascon_shop.ui

import android.os.Bundle
import android.util.Log
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.ProductPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductActivity: MvpAppCompatActivity(), ProductView {

    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        ProductPresenter(
            mainApi = service
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("myDebug","onCreateBeforeSetContentView")
        setContentView(R.layout.activity_product)
        Log.d("myDebug","onCreateAfterSetContentView")

        val category = intent?.getParcelableExtra<RemoteCategory>(CATEGORY_TAG) ?: return
        presenter.getCategory(category)
        Log.d("myDebug","onCreateAftergetCategory")
    }

    override fun setProduct(product: List<RemoteProduct>) {
        TODO("Not yet implemented")
    }

    companion object {
        const val CATEGORY_TAG = "CATEGORY_TAG"
    }
}