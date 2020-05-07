package com.suvorov.ascon_shop.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.BasketPresenter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_basket.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class BasketActivity: MvpAppCompatActivity(), BasketView {

    private val presenter by moxyPresenter { BasketPresenter() }
    private val adapter = BasketAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketRv.layoutManager = LinearLayoutManager(this)
        basketRv.adapter = adapter
    }

    override fun setData(list: List<RemoteProduct>) {
        adapter.setBasket(list)
    }

}