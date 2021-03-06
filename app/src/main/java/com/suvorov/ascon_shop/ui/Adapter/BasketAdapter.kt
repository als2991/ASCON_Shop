package com.suvorov.ascon_shop.ui.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.RemoteProduct
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_basket.*
import kotlinx.android.synthetic.main.item_basket.view.*

class BasketAdapter(
    private val getDiscountPrice: (product: RemoteProduct) -> Double,
    private val onDeleteProduct: (product: RemoteProduct, position: Int) -> Unit
): RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private var basketProduct: List<RemoteProduct> = listOf()

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        @SuppressLint("SetTextI18n")
        fun bind(product: RemoteProduct){
            itemNameProduct.text = product.name
            itemPrice.text = "${getDiscountPrice(product)} р"
            itemDiscount.text = "${product.discountPercent} %"
            itemView.deleteIv.setOnClickListener { onDeleteProduct(product,adapterPosition) }
        }
    }

    fun setData(list: List<RemoteProduct>){
        this.basketProduct = list
        notifyDataSetChanged()
    }

    fun deleteProduct(product: RemoteProduct){
        val filterBasketProduct = mutableListOf<RemoteProduct>().apply {
            addAll(basketProduct.filter { it != product })
        }
        basketProduct = filterBasketProduct
    }

    fun getBasketProduct(): List<RemoteProduct>{
        return basketProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        )
    override fun getItemCount(): Int = basketProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(basketProduct[position])
    }
}