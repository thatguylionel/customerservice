package de.sashin.customerservice.models.mongodb

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
data class Product(
    @Id val id: String?,
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)
