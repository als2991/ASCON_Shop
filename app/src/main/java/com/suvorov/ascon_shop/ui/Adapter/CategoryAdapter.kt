package com.suvorov.ascon_shop.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suvorov.ascon_shop.R
import com.suvorov.ascon_shop.domain.RemoteCategory
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*

class CategoryAdapter(
    private val context: Context,
    private val onCategoryClick: (category: RemoteCategory) -> Unit
):RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories: List<RemoteCategory> = listOf()

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(category: RemoteCategory){
            categoryTv.text = category.name
            Glide
                .with(context)
                .load(category.imageUrl)
                .error(R.drawable.ic_no_category)
                .into(categoryImg)
            containerView.setOnClickListener { onCategoryClick(category) }
        }
    }

    fun setData(categories: List<RemoteCategory>){
        this.categories = categories
        notifyDataSetChanged()
    }

    //создает объект ViewHolder для каждой строки списка в Layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )

    //общее количество элементов в списке
    override fun getItemCount(): Int = categories.size

    //принимает объект ViewHolder и устанавливает необходимые данные для строки в View компоненте(прокидываем данные, которые необходимо отобразить)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }


}