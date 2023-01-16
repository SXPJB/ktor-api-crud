package com.fsociety

import com.fsociety.dao.CustomerDAO
import com.fsociety.dao.db.DatabaseFactory
import io.ktor.server.application.*
import com.fsociety.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.cors.routing.CORS

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    val customerDAO = CustomerDAO()
    configureSerialization()
    configureRouting(customerDAO)
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }
}

private fun CORSConfig.allowHostWithAllSubDomain(host:String, schemes:List<String> = listOf("http", "https")) {
    allowHost(host, schemes)
    allowHost("*.${host}", schemes)
}