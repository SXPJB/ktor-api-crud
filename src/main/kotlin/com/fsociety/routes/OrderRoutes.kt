package com.fsociety.routes

import com.fsociety.model.orderStorage
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrdersRoute(){
    get("/orders"){
        if(orderStorage.isNotEmpty()){
            call.respond(orderStorage)
        }else{
            call.respondText("No orders found", status = NotFound)
        }
    }
}

fun Route.getOrderRoute() {
    get("/order/{id?}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText("Bad Request", status = BadRequest)
        val order = orderStorage.find { it.id == id } ?: return@get call.respondText(
            "Not Found",
            status = NotFound
        )
        call.respond(order)
    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{id?}/total") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText("Bad Request", status = BadRequest)
        val order = orderStorage.find { it.id == id } ?: return@get call.respondText(
            "Not Found",
            status = NotFound
        )
        val total = order.contents.sumOf { it.price * it.amount }
        call.respond(mapOf("total" to total))
    }
}