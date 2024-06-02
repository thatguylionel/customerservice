package de.sashin.customerservice.service


import de.sashin.customerservice.models.h2.Customer
import de.sashin.customerservice.repositories.CustomerRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

/**
 * Interface for customer service operations.
 */
interface CustomerService {

    /**
     * Adds a new customer.
     *
     * @param customer The customer to add.
     * @return The added customer.
     */
    fun addCustomer(customer: Customer): Customer

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return An Optional containing the customer if found, or empty if not.
     */
    fun getCustomerById(id: Long): Optional<Customer>

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     */
    fun getAllCustomers(): List<Customer>

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to update.
     * @return The updated customer.
     */
    fun updateCustomer(customer: Customer): Customer

    /**
     * Deletes a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     */
    fun deleteCustomer(id: Long)

    /**
     * Implementation of the CustomerService interface.
     *
     * @property customerRepository The repository for customer operations.
     */
    @Service
    class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {

        /**
         * Adds a new customer and sets the update date to the current time.
         *
         * @param customer The customer to add.
         * @return The added customer.
         */
        @Transactional
        @Modifying
        override fun addCustomer(customer: Customer): Customer {
            customer.updateDate = LocalDateTime.now()
            return customerRepository.save(customer)
        }

        /**
         * Retrieves a customer by their ID.
         *
         * @param id The ID of the customer to retrieve.
         * @return An Optional containing the customer if found, or empty if not.
         */
        override fun getCustomerById(id: Long): Optional<Customer> {
            customerRepository.findByEmailOf("test.test") // This line seems to be extraneous or an example
            return customerRepository.findById(id)
        }

        /**
         * Retrieves all customers.
         *
         * @return A list of all customers.
         */
        override fun getAllCustomers(): List<Customer> {
            return customerRepository.findAll()
        }

        /**
         * Updates an existing customer.
         *
         * @param customer The customer to update.
         * @return The updated customer.
         */
        override fun updateCustomer(customer: Customer): Customer {
            return customerRepository.save(customer)
        }

        /**
         * Deletes a customer by their ID.
         *
         * @param id The ID of the customer to delete.
         */
        override fun deleteCustomer(id: Long) {
            customerRepository.deleteById(id)
        }
    }
}




