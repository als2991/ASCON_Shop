package com.suvorov.ascon_shop.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.RemoteProduct
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    private val context: Context,
    private val onProductClick: (product: RemoteProduct) -> Unit,
    private val getDiscountPrice: (product: RemoteProduct) -> Double,
    private val onAddBasketClick: (product: RemoteProduct) -> Unit
): RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    private var products: List<RemoteProduct> = listOf()

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(product: RemoteProduct){
            productTv.text = product.name
            productPrice.text = "${product.price} р"

            if (product.discountPercent != 0){
                productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                productDiscountPrice.text = "${getDiscountPrice(product)} р"}

            Glide
                .with(context)
                .load(product.imageUrl)
                .error(R.drawable.ic_no_category)
                .into(productImg)
            containerView.setOnClickListener { onProductClick(product) }
            itemView.productAdd.setOnClickListener { onAddBasketClick(product) }

        }
    }

    fun setData(
        products: List<RemoteProduct>
    ){
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }
}