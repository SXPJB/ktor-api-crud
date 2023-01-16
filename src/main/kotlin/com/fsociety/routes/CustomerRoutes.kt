package com.fsociety.routes

import com.fsociety.dao.CustomerDAO
import com.fsociety.model.Customer
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(
    customerDAO: CustomerDAO
) {
    route("/customers") {
        get {
            val customers = customerDAO.findAll()
            if (customers.isNotEmpty()){
                call.respond(customers)
            }else{
                call.respondText("No customers found", status = NotFound)
            }
        }
        get("{id?}") {
            val id:Int = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                "Missing id",
                status = BadRequest
            )
            val customer:Customer = customerDAO.findById(id) ?: return@get call.respondText(
                    "No customer with id $id",
                    status = NotFound
                )
            call.respond(customer)
        }
        post{
            val customer:Customer = call.receive()
            val response:Customer = customerDAO.save(customer)
            call.respond(Created,response)
        }
        put ("{id?}"){
            val id:Int = call.parameters["id"]?.toIntOrNull() ?: return@put call.respondText(
                "Missing id",
                status = BadRequest
            )
            val customer:Customer = call.receive()
            val response:Customer = customerDAO.update(id,customer)
            call.respond(response)
        }
        delete("{id?}"){
            val id:Int = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(BadRequest)
            if (customerDAO.delete(id)) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = NotFound)
            }
        }
    }
}