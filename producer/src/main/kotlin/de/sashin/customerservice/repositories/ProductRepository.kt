package de.sashin.customerservice.repositories

import de.sashin.customerservice.models.mongodb.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Product, String> {
}