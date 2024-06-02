package de.sashin.customerservice.web

import de.sashin.customerservice.models.mongodb.Product
import de.sashin.customerservice.service.KafkaProducerService
import de.sashin.customerservice.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for managing products.
 *
 * This controller provides CRUD operations for products.
 */
@RestController
@RequestMapping("/api/products")
class ProductController(private val service: ProductService, private val kafkaProducer: KafkaProducerService) {

    /**
     * Retrieves all products.
     *
     * @return a list of all products.
     */
    @GetMapping
    fun getAllDocuments(): List<Product> = service.findAll()

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return the product with the given ID, or null if no such product exists.
     */
    @GetMapping("/{id}")
    fun getDocumentById(@PathVariable id: String): Product? = service.findById(id)

    /**
     * Creates a new product. Also added Kafka message to notify about the product creation.
     *
     * @param document the product to create.
     * @return the created product.
     */
    @PostMapping
    fun createDocument(@RequestBody document: Product): ResponseEntity<Product> {
        val savedProduct = service.save(document)
        kafkaProducer.sendMessage("product_created", "Product created with ID: ${savedProduct.id}")
        return ResponseEntity.ok(savedProduct)
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     */
    @DeleteMapping("/{id}")
    fun deleteDocument(@PathVariable id: String): ResponseEntity<String> {
        service.deleteById(id)
        kafkaProducer.sendMessage("product_deleted", "Product deleted with ID: $id")
        return ResponseEntity.ok("Product deleted")
    }

}