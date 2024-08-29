package dev.zvaryyka.rebalancerservice.config

import dev.zvaryyka.rebalancerservice.dto.DeviceNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.EmailNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.SmsNotificationDTO
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig {

    private val bootstrapAddress = "\${spring.kafka.bootstrap-servers}"

    @Bean
    fun deviceProducerFactory(): ProducerFactory<String, DeviceNotificationDTO> {
        val configProps = mutableMapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun deviceKafkaTemplate(): KafkaTemplate<String, DeviceNotificationDTO> {
        return KafkaTemplate(deviceProducerFactory())
    }

    @Bean
    fun emailProducerFactory(): ProducerFactory<String, EmailNotificationDTO> {
        val configProps = mutableMapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun emailKafkaTemplate(): KafkaTemplate<String, EmailNotificationDTO> {
        return KafkaTemplate(emailProducerFactory())
    }

    @Bean
    fun smsProducerFactory(): ProducerFactory<String, SmsNotificationDTO> {
        val configProps = mutableMapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun smsKafkaTemplate(): KafkaTemplate<String, SmsNotificationDTO> {
        return KafkaTemplate(smsProducerFactory())
    }

    private fun consumerConfigs(groupId: String): Map<String, Any> {
        return mutableMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(consumerConfigs("default-group"))
        return factory
    }
}
