package de.sashin.customerservice.models.h2

import jakarta.persistence.*
import lombok.Data
import java.time.LocalDateTime

@Data
@Entity
@Table(name = "T_CUSTOMER")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var email: String,
    @Column(name = "updateDate", nullable = true)
    var updateDate: LocalDateTime?,
)
