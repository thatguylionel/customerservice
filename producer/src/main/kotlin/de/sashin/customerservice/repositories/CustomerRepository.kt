package de.sashin.customerservice.repositories

import de.sashin.customerservice.models.h2.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    @Query(name = "findByEmail", nativeQuery = true, value = "select * from T_CUSTOMER where email = :email")
    fun findByEmailOf(email: String): Optional<Customer>
}