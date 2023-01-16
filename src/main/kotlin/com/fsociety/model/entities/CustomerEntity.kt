package com.fsociety.model.entities

import org.jetbrains.exposed.sql.Table

object CustomerEntity:Table("customer"){
    val id = integer("id").autoIncrement()
    val firstName = varchar("name", 50)
    val lastName = varchar("lastName", 50)
    val email = varchar("email", 100).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}