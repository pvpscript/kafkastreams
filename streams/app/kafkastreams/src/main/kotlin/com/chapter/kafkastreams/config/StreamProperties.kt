package com.chapter.kafkastreams.config

import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsConfig
import org.springframework.stereotype.Component

@Component
class StreamProperties {
    fun props() = Properties()
        .also {
            it[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:29092"
            it[StreamsConfig.APPLICATION_ID_CONFIG] = "kafka-streams-presentation"
            it[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        }
}

