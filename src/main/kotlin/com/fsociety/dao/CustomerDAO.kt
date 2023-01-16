package com.fsociety.dao

import com.fsociety.dao.db.DatabaseFactory.dbQuery
import com.fsociety.model.Customer
import com.fsociety.model.entities.CustomerEntity
import com.fsociety.model.entities.CustomerEntity.email
import com.fsociety.model.entities.CustomerEntity.firstName
import com.fsociety.model.entities.CustomerEntity.id
import com.fsociety.model.entities.CustomerEntity.lastName
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CustomerDAO {
    suspend fun save(customer: Customer):Customer = dbQuery {
            CustomerEntity.insert { c ->
                c[firstName] = customer.firstName
                c[lastName] = customer.lastName
                c[email] = customer.email
            }
        return@dbQuery customer
    }


    suspend fun findAll(): List<Customer> = dbQuery {
        CustomerEntity.selectAll().map (::rowToCustomer)
    }

    suspend fun update(id:Int, customer: Customer):Customer = dbQuery {
        CustomerEntity.update({ CustomerEntity.id eq id }) {
            it[firstName] = customer.firstName
            it[lastName] = customer.lastName
            it[email] = customer.email
        }
        return@dbQuery customer.copy(id = id)
    }

    suspend fun delete(id:Int):Boolean = dbQuery {
        CustomerEntity.deleteWhere { CustomerEntity.id eq id } > 0
    }

    suspend fun findById(id:Int): Customer? = dbQuery {
        CustomerEntity.select { CustomerEntity.id eq id }.mapNotNull { rowToCustomer(it) }.singleOrNull()
    }

    private fun rowToCustomer(row: ResultRow): Customer {
        return Customer(
            id = row[id],
            firstName = row[firstName],
            lastName = row[lastName],
            email = row[email],
        )
    }
}