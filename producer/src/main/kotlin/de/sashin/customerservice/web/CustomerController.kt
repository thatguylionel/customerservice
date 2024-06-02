package de.sashin.customerservice.web

import de.sashin.customerservice.models.h2.Customer
import de.sashin.customerservice.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    val customerService: CustomerService
) {
    @PostMapping
    fun add(@RequestBody customer: Customer) : ResponseEntity<String> {
        customerService.addCustomer(customer)
        return ResponseEntity.ok("Done")
    }

    @GetMapping()
    fun getAll(): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(customerService.getAllCustomers())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Customer> {
        return customerService.getCustomerById(id)
            .map { customer -> ResponseEntity.ok(customer) } // If customer is found, return 200 OK
            .orElseGet { ResponseEntity.notFound().build() }  // If not found, return 404 Not Found
    }

}