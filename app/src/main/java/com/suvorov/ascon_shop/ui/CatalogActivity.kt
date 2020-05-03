package com.suvorov.ascon_shop.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.MainApi
import com.suvorov.ascon_shop.presenter.CatalogPresenter
import kotlinx.android.synthetic.main.activity_catalog.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CatalogActivity: MvpAppCompatActivity(), CatalogView {
    private val presenter by moxyPresenter {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.url_server))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MainApi::class.java)
        CatalogPresenter(
            mainApi = service
        )
    }

    private val adapter = CategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_catalog)

        categoryRv.layoutManager = LinearLayoutManager(this)
        categoryRv.adapter = adapter

    }

    override fun setCategory(list: List<String>) {
        adapter.setData(list)
    }
}