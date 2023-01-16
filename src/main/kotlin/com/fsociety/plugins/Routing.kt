package com.fsociety.plugins

import com.fsociety.routes.customerRouting
import com.fsociety.routes.getOrderRoute
import com.fsociety.routes.listOrdersRoute
import com.fsociety.routes.totalizeOrderRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.fsociety.dao.CustomerDAO

fun Application.configureRouting(
    customerDAO:CustomerDAO
) {

    routing {
        customerRouting(customerDAO)
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
