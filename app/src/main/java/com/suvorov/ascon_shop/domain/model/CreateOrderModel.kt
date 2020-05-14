package com.suvorov.ascon_shop.domain.model

data class CreateOrderModel(
    /*
** Модель для создания заказа
 */
    var organization: String = "",
    var fio: String = "",
    var userPhone: String = "",
    var userEmail: String = "",
    var productsId: List<String> = listOf()
)