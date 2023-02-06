package com.chapter.kafkastreams.examples.aggregate

import com.chapter.kafkastreams.serdes.CustomSerdes
import com.chapter.kafkastreams.utils.KafkaStreamHandler
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.springframework.stereotype.Component

@Component
class AggregatedLoremIpsum(
    private val kafkaStreamHandler: KafkaStreamHandler,
) {
    fun runStreamAggregation() {
        // Stateful operation -> Previous values are remembered
        kafkaStreamHandler.builtStream { builder ->
            builder.stream(INPUT_TOPIC, Consumed.with(STRING_SERDE, BASIC_LOREN_SERDE))
                .peek { _, value -> println("Before transform: $value") }
                .groupByKey() // Repartitioning! -> data with the same key go to the same partition
                .aggregate(
                    { 0 },
                    { _, value, total -> total + value.line.length },
                    Materialized.with(STRING_SERDE, INTEGER_SERDE)
                )
                .toStream()
                .mapValues { v -> v.toString() }
                .peek { key, value -> println("After aggregation: $key -> $value") }
                .to(OUTPUT_TOPIC, Produced.with(STRING_SERDE, STRING_SERDE))
        }
    }

    companion object {
        private const val INPUT_TOPIC = "keyed-lorem-ipsum"
        private const val OUTPUT_TOPIC = "aggregated-lorem-ipsum"

        private val STRING_SERDE = Serdes.String()
        private val INTEGER_SERDE = Serdes.Integer()
        private val BASIC_LOREN_SERDE = CustomSerdes.basicLoremSerde()
    }
}
