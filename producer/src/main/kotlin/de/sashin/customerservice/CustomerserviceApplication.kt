package de.sashin.customerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerserviceApplication

fun main(args: Array<String>) {
	runApplication<CustomerserviceApplication>(*args)
}
