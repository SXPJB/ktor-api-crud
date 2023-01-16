package com.fsociety

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class OrderRouteTests {
    @Test
    fun testGetOrder() = testApplication {
        val response = client.get("/order/1")
        assertEquals("""{"id":1,"contents":[{"item":"Pencil","amount":3,"price":2.0},{"item":"Eraser","amount":1,"price":1.0}]}""".trimIndent(),
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
}