package com.suvorov.ascon_shop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.RemoteCategory
import com.suvorov.ascon_shop.domain.RemoteProduct
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_basket.*
import kotlinx.android.synthetic.main.item_category.*

class BasketAdapter: RecyclerView.Adapter<BasketAdapter.ViewHolder>() {


    private var basketProduct: List<RemoteProduct> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        )

    fun setBasket(list: List<RemoteProduct>){
        this.basketProduct = list
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int = basketProduct.size

    override fun onBindViewHolder(holder: BasketAdapter.ViewHolder, position: Int) {
        holder.bind(basketProduct[position])
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(product: RemoteProduct){
           itemNameProduct.text = product.name
            itemPrice.text = "${product.price}"
            itemDiscount.text = "${product.discountPercent}"
//            Glide
//                .with(context)
//                .load(category.imageUrl)
//                .error(R.drawable.ic_no_category)
//                .into(categoryImg)
//            containerView.setOnClickListener { onCategoryClick(category) }
        }
    }
}