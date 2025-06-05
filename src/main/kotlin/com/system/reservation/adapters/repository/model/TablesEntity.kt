package com.system.reservation.adapters.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tables")
data class TablesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val capacity: Int,
    @Enumerated(value = EnumType.STRING)
    val status: StatusTable,
)

enum class StatusTable { AVAILABLE, RESERVED, INACTIVE }
