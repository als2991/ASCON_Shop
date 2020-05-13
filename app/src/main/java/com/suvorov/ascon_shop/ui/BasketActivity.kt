package com.suvorov.ascon_shop.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.BasketPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_basket.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasketActivity: MvpAppCompatActivity(), BasketView {

    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        BasketPresenter(
            mainApi = service,
            viewedProductDao = ViewedProductDaoIml(sharedPreferences)
        ) }

    private val adapter = BasketAdapter(
        { product -> presenter.getDiscountPrice(product)},
        { product, position -> presenter.deleteProductInBasket(product, position)}
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketRv.layoutManager = LinearLayoutManager(this)
        basketRv.adapter = adapter

        back.setOnClickListener { finish() }
    }

    override fun setData(list: List<RemoteProduct>) {
        adapter.setBasket(list)
    }

    override fun removeItem(position: Int) {
        adapter.notifyItemRemoved(position)
    }
}