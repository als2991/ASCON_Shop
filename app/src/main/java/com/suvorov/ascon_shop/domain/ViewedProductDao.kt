package com.suvorov.ascon_shop.domain

interface ViewedProductDao {
        /**
         * save this product id as viewed
         */
        fun addProductInBasket(id: String)

        /**
         * get all viewed product ids
         * might be empty
         */
        fun getAllProducts(): List<String>

        fun deleteProductInBasket(id: String)
}