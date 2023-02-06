package com.chapter.kafkastreams.examples.tables

import com.chapter.kafkastreams.utils.KafkaStreamHandler
import java.time.Duration
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.state.KeyValueStore
import org.springframework.stereotype.Component

@Component
class TabledNumbers(
    private val kafkaStreamHandler: KafkaStreamHandler,
) {
    fun runTableCreation() {
        kafkaStreamHandler.builtStream { builder ->
            val ktable = builder.table(
                INPUT_TOPIC,
                Materialized.`as`<String, String, KeyValueStore<Bytes, ByteArray>>("ktable-store")
                    .withKeySerde(STRING_SERDE)
                    .withValueSerde(STRING_SERDE)
                    .withRetention(Duration.ofSeconds(30))
            // O processamento para quando o tamanho do cache ou o tempo de reteção são excedidos
            )

            ktable.toStream()
                .peek { key, value -> println("After tabled: $key -> $value") }
                .to(OUTPUT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE))
        }
    }

    companion object {
        private const val INPUT_TOPIC = "interactive-numbers"
        private const val OUTPUT_TOPIC = "tabled-numbers"

        private val STRING_SERDE = Serdes.String()
    }
}