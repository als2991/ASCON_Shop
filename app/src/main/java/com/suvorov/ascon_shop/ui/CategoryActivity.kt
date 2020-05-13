package com.suvorov.ascon_shop.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.presenter.CategoryPresenter
import com.suvorov.ascon_shop.ui.ProductActivity.Companion.CATEGORY_TAG
import kotlinx.android.synthetic.main.activity_category.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CategoryActivity: MvpAppCompatActivity(), CategoryView {
    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        CategoryPresenter(
            mainApi = service
        )
    }

    private val adapter = CategoryAdapter(this){
            category -> presenter.onCategoryClick(category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoryRv.layoutManager = LinearLayoutManager(this)
        categoryRv.adapter = adapter
    }

    override fun setCategory(list: List<RemoteCategory>) {
        adapter.setData(list)
    }

    override fun onShowCategoryProducts(category: RemoteCategory) {
        startActivity(Intent(this, ProductActivity::class.java).apply {
            putExtra(CATEGORY_TAG, category)
        })
    }
}