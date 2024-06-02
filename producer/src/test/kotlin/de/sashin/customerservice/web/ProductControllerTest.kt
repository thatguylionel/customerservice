package de.sashin.customerservice.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.sashin.customerservice.models.mongodb.Product
import de.sashin.customerservice.service.KafkaProducerService
import de.sashin.customerservice.service.ProductService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    @MockBean
    private lateinit var kafkaProducerService: KafkaProducerService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should return all products`() {
        val products = listOf(
            Product(
                id = "1",
                name = "Product 1",
                category = "Category 1",
                description = "Desc 1",
                price = 32.99
            ), Product(id = "2", name = "Product 2", category = "Category 2", description = "Desc 2", price = 499.99)
        )
        given(productService.findAll()).willReturn(products)

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(products.size))
            .andExpect(jsonPath("$[0].id").value(products[0].id))
            .andExpect(jsonPath("$[0].name").value(products[0].name))
    }

    @Test
    fun `should return product by id`() {
        val product =
            Product(id = "1", name = "Product 1", category = "Category 1", description = "Desc 1", price = 32.99)
        given(productService.findById("1")).willReturn(product)

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.id))
            .andExpect(jsonPath("$.name").value(product.name))
    }

    @Test
    fun `should create a new product`() {
        val product =
            Product(id = "1", name = "Product 1", category = "Category 1", description = "Desc 1", price = 32.99)
        given(productService.save(product)).willReturn(product)

        mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(product.id))
            .andExpect(jsonPath("$.name").value(product.name))

        verify(kafkaProducerService).sendMessage("product_created", "Product created with ID: ${product.id}")
    }

    @Test
    fun `should delete a product by id`() {
        val productId = "1"

        mockMvc.perform(delete("/api/products/$productId"))
            .andExpect(status().isOk)

        verify(productService).deleteById(productId)
        verify(kafkaProducerService).sendMessage("product_deleted", "Product deleted with ID: $productId")
    }
}