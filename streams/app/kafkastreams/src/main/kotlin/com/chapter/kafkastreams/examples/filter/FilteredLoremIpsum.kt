package com.chapter.kafkastreams.examples.filter

import com.chapter.kafkastreams.model.toSizeLoren
import com.chapter.kafkastreams.serdes.CustomSerdes
import com.chapter.kafkastreams.utils.KafkaStreamHandler
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.stereotype.Component

@Component
class FilteredLoremIpsum(
    private val kafkaStreamHandler: KafkaStreamHandler,
) {
    fun runStreamFilter() {
        // Stateless operation -> Previous values don't need to be remembered or stored
        // Uses a boolean predicate
        kafkaStreamHandler.builtStream { builder ->
            val sizedLoremStream = builder
                .stream(INPUT_TOPIC, Consumed.with(STRING_SERDE, BASIC_LOREN_SERDE))
                .peek { _, value -> println("Before transform: $value") }
                .mapValues { basicLoren -> basicLoren.toSizeLoren() }

            val evenStream = sizedLoremStream
                .filter { _, value -> value.size % 2 == 0 }
                .peek { _, value -> println("After EVEN filter: $value") }
            val oddStream = sizedLoremStream
                .filter { _, value -> value.size % 2 != 0 }
                .peek { _, value -> println("After ODD filter: $value") }

            val evenOutputTopic = "even-$OUTPUT_TOPIC"
            evenStream.to(evenOutputTopic, Produced.with(STRING_SERDE, SIZED_LOREN_SERDE))
            val oddOutputTopic = "odd-$OUTPUT_TOPIC"
            oddStream.to(oddOutputTopic, Produced.with(STRING_SERDE, SIZED_LOREN_SERDE))
        }
    }

    companion object {
        private const val INPUT_TOPIC = "lorem-ipsum"
        private const val OUTPUT_TOPIC = "sized-lorem-ipsum"

        private val STRING_SERDE = Serdes.String()
        private val BASIC_LOREN_SERDE = CustomSerdes.basicLoremSerde()
        private val SIZED_LOREN_SERDE = CustomSerdes.sizedLoremSerde()
    }
}
