package com.suvorov.ascon_shop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suvorov.ascon_shop.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.*

class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    //создает объект ViewHolder для каждой строки списка в Layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )

    private var categories: List<String> = listOf()

    fun setData(categories: List<String>){
          this.categories = categories
        notifyDataSetChanged()
    }

    //общее количество элементов в списке
    override fun getItemCount(): Int = categories.size

    //принимает объект ViewHolder и устанавливает необходимые данные для строки в View компоненте(прокидываем данные, которые необходимо отобразить)
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(text: String){
            categoryTv.text = text
        }
    }
}