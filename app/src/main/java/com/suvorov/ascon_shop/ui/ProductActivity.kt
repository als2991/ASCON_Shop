package com.suvorov.ascon_shop.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.ProductPresenter
import com.suvorov.ascon_shop.ui.AboutProductActivity.Companion.PRODUCT_TAG
import com.suvorov.ascon_shop.ui.Adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_product.back
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductActivity: MvpAppCompatActivity(),
    ProductView {

    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        ProductPresenter(
            mainApi = service,
            viewedProductDao = ViewedProductDaoIml(sharedPreferences),
            context = this
    ) }

    private val adapter = ProductAdapter(
        this,
        { product -> presenter.onProductClick(product) },
        { product -> presenter.getDiscountPrice(product) },
        { product -> presenter.addProductInBasket(product) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val category = intent?.getParcelableExtra<RemoteCategory>(CATEGORY_TAG) ?: return
        presenter.getCategory(category)

        productRv.layoutManager = LinearLayoutManager(this)
        productRv.adapter = adapter

        //Caption activity
        prCategoryName.text = category.name

        back.setOnClickListener { finish() }

        basket.setOnClickListener {
            startActivity(Intent(this, BasketActivity::class.java))
        }
    }

    override fun setProduct(product: List<RemoteProduct>) {
        adapter.setData(product)
    }

    override fun onShowAboutProduct(product: RemoteProduct) {
        startActivity(Intent(this, AboutProductActivity::class.java).apply {
            putExtra(PRODUCT_TAG, product)
        })
    }

    override fun showMessage(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val CATEGORY_TAG = "CATEGORY_TAG"
    }
}

val AppCompatActivity.sharedPreferences: SharedPreferences
    get() = getSharedPreferences("dataBasket", Context.MODE_PRIVATE)