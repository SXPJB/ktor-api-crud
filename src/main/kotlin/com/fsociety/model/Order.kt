package com.fsociety.model

import kotlinx.serialization.Serializable

@Serializable
data class Order (
    val id:Int,
    val contents:List<OrderItem>,
)

@Serializable
data class OrderItem (
    val item: String,
    val amount: Int,
    val price: Double
)

val orderStorage:MutableList<Order> = mutableListOf(
    Order(
        1,
        listOf(
            OrderItem("Pencil", 3, 2.0),
            OrderItem("Eraser", 1, 1.0)
        )
    ),
    Order(
        2,
        listOf(
            OrderItem("Pencil", 2, 2.0),
            OrderItem("Eraser", 1, 1.0),
            OrderItem("Notebook", 3, 5.0)
        )
    )
)