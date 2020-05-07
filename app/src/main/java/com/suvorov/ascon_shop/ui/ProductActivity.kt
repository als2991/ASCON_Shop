package com.suvorov.ascon_shop.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.ProductPresenter
import com.suvorov.ascon_shop.ui.AboutProductActivity.Companion.PRODUCT_TAG
import kotlinx.android.synthetic.main.activity_product.*
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

    private val adapter = ProductAdapter(
        this,
     { product -> presenter.onProductClick(product)},
        {product -> presenter.getDiscountPrice(product)}
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("myDebug","onCreateBeforeSetContentView")
        setContentView(R.layout.activity_product)
        Log.d("myDebug","onCreateAfterSetContentView")

        val category = intent?.getParcelableExtra<RemoteCategory>(CATEGORY_TAG) ?: return
        presenter.getCategory(category)
        Log.d("myDebug","onCreateAftergetCategory")

        productRv.layoutManager = LinearLayoutManager(this)
        productRv.adapter = adapter

        prCategoryName.text =  category.name

        back.setOnClickListener { finish() }
    }

    override fun setProduct(
        product: List<RemoteProduct>
    ) {
        adapter.setData(product)
    }

    override fun onShowProduct(product: RemoteProduct) {
        startActivity(Intent(this, AboutProductActivity::class.java).apply {
            putExtra(PRODUCT_TAG, product)
        })
    }

    companion object {
        const val CATEGORY_TAG = "CATEGORY_TAG"
    }
}