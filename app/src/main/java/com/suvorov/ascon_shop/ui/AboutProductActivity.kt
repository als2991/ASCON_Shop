package com.suvorov.ascon_shop.ui

import android.graphics.Paint
import android.os.Bundle
import com.bumptech.glide.Glide
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.data.ViewedProductDaoIml
import com.suvorov.ascon_shop.domain.RemoteProduct
import com.suvorov.ascon_shop.presenter.AboutProductPresenter
import kotlinx.android.synthetic.main.activity_about_product.*
import moxy.MvpAppCompatActivity

class AboutProductActivity: MvpAppCompatActivity(), AboutProductView {

    val presenter = AboutProductPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_product)

        val product = intent?.getParcelableExtra<RemoteProduct>(PRODUCT_TAG) ?: return

        aboutProductHeader.text = product.name
        Glide
            .with(this)
            .load(product.imageUrl)
            .error(R.drawable.ic_no_category)
            .into(aboutProductImage)
        aboutProductPrice.text = "${product.price} р"
        if(product.discountPercent != 0){
            aboutProductPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            aboutProductDiscountPrice.text = "${presenter.getDiscountPrice(product)} р"
        }
        descriptionText.text = "${product.attributes[0].name}:"
        aboutProductDescription.text = product.attributes[0].value
        opportunitiesText.text = "${product.attributes[1].name}:"
        aboutProductOpportunities.text = product.attributes[1].value

        back.setOnClickListener { finish() }
    }

    companion object {
        const val PRODUCT_TAG = "PRODUCT_TAG"
    }
}