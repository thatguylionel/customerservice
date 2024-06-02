package de.sashin.customerservice.service

import de.sashin.customerservice.models.mongodb.Product
import de.sashin.customerservice.repositories.ProductRepository
import org.springframework.stereotype.Service

interface ProductService {
    fun findAll(): List<Product>
    fun findById(id: String): Product?
    fun save(document: Product): Product
    fun deleteById(id: String)

    /**
     * Service class for managing products.
     *
     * This service provides CRUD operations for products.
     */
    @Service
    class ProductServiceImpl(private val repository: ProductRepository) : ProductService {

        /**
         * Retrieves all products.
         *
         * @return a list of all products.
         */
        override fun findAll(): List<Product> = repository.findAll()

        /**
         * Retrieves a product by its ID.
         *
         * @param id the ID of the product to retrieve.
         * @return the product with the given ID, or null if no such product exists.
         */
        override fun findById(id: String): Product? = repository.findById(id).orElse(null)

        /**
         * Creates or updates a product.
         *
         * @param document the product to create or update.
         * @return the created or updated product.
         */
        override fun save(document: Product): Product = repository.save(document)

        /**
         * Deletes a product by its ID.
         *
         * @param id the ID of the product to delete.
         */
        override fun deleteById(id: String) = repository.deleteById(id)
    }
}