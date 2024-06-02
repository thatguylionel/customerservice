package de.sashin.customerservice.services

import de.sashin.customerservice.models.h2.Customer
import de.sashin.customerservice.service.CustomerService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = ["spring.mongodb.embedded.version=4.0.10"])
class CustomerServiceTest {

    @Autowired
    lateinit var customerService: CustomerService

    companion object {
        @Container
        val mongoDBContainer = MongoDBContainer("mongo:4.0.10").apply {
            withExposedPorts(27017)
        }

        @DynamicPropertySource
        @JvmStatic
        fun mongoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }

    @BeforeEach
    fun setUp() {
        mongoDBContainer.start()
    }

    @AfterEach
    fun tearDown() {
        mongoDBContainer.stop()
    }

    @Test
    fun `test add and retrieve customer`() {
        val customer = Customer(1, "acme industries", "bugs@acme.com", LocalDateTime.now())

        val savedCustomer = customerService.addCustomer(customer)
        val retrievedCustomer = customerService.getCustomerById(savedCustomer.id)

        assert(retrievedCustomer.isPresent)
        assert(retrievedCustomer.get().email == customer.email)
    }
}
