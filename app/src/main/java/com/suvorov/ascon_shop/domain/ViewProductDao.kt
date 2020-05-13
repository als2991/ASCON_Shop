package com.suvorov.ascon_shop.domain

interface ViewProductDao {
        /**
         * save this product id as viewed
         */
        fun addProductInBasket(product: RemoteProduct)

        /**
         * get all viewed product ids
         * might be empty
         */
        fun getAllProducts(): List<RemoteProduct>
}