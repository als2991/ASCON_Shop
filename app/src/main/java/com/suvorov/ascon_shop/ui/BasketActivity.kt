package com.suvorov.ascon_shop.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.BasketPresenter
import com.suvorov.ascon_shop.ui.Adapter.BasketAdapter
import com.suvorov.ascon_shop.ui.CreateOrderActivity.Companion.TOTALPRICE_TAG
import kotlinx.android.synthetic.main.activity_basket.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasketActivity: MvpAppCompatActivity(),
    BasketView {

    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        BasketPresenter(
            mainApi = service,
            viewedProductDao = ViewedProductDaoIml(sharedPreferences),
            context = this
        ) }

    private val adapter = BasketAdapter(
        { product -> presenter.getDiscountPrice(product) },
        { product, position -> presenter.deleteProductInBasket(product, position) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketRv.layoutManager = LinearLayoutManager(this)
        basketRv.adapter = adapter

        back.setOnClickListener { finish() }

        basketCreateOrder.setOnClickListener {
            if (adapter.getBasketProduct().isEmpty()) Toast.makeText(this,"Ваша корзина пуста", Toast.LENGTH_SHORT).show()
            else
            startActivity(Intent(this, CreateOrderActivity::class.java).apply {
                putExtra(TOTALPRICE_TAG, presenter.getTotalPrice(adapter.getBasketProduct()))
            })
        }
    }

    override fun setBasket(list: List<RemoteProduct>) {
        adapter.setData(list)
    }

    override fun removeItem(product: RemoteProduct, position: Int) {
        adapter.deleteProduct(product)
        adapter.notifyItemRemoved(position)
    }

    override fun showMessage(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}