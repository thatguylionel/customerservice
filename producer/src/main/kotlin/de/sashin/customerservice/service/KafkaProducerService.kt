package de.sashin.customerservice.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


interface KafkaProducerService {
    fun sendMessage(topic: String, message: String)

    @Service
    class KafkaProducerServiceImpl(private val kafkaTemplate: KafkaTemplate<String, String>) : KafkaProducerService {

        override fun sendMessage(topic: String, message: String) {
            kafkaTemplate.send(topic, message)
        }
    }
}