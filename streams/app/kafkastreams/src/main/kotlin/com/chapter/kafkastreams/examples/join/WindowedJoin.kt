package com.chapter.kafkastreams.examples.join

import com.chapter.kafkastreams.model.toLoremNumber
import com.chapter.kafkastreams.serdes.CustomSerdes
import com.chapter.kafkastreams.utils.KafkaStreamHandler
import java.time.Duration
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.JoinWindows
import org.apache.kafka.streams.kstream.Joined
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.StreamJoined
import org.springframework.stereotype.Component

@Component
class WindowedJoin(
    private val kafkaStreamHandler: KafkaStreamHandler,
) {
    fun runStreamJoin() {
        kafkaStreamHandler.builtStream { builder ->
            val leftStream = builder.stream(LEFT_INPUT_TOPIC, Consumed.with(STRING_SERDE, BASIC_LOREN_SERDE))
                .peek { key, value -> println("Left stream: $key -> $value") }
            val rightStream = builder.stream(RIGHT_INPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE))
                .peek { key, value -> println("Right stream: $key -> $value") }

            val joined = leftStream.join(
                rightStream,
                { leftValue, rightValue -> leftValue.toLoremNumber(rightValue.toInt()) },
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1)),
                StreamJoined.with(STRING_SERDE, BASIC_LOREN_SERDE, STRING_SERDE)
            )

            joined
                .peek { key, value -> println("After joining: $key -> $value") }
                .to(OUTPUT_TOPIC, Produced.with(STRING_SERDE, LOREM_NUMBER_SERDE))
        }
    }

    companion object {
        private const val LEFT_INPUT_TOPIC = "interactive-keyed-lorem-ipsum"
        private const val RIGHT_INPUT_TOPIC = "interactive-just-numbers"
        private const val OUTPUT_TOPIC = "joined-lorem-ipsum"

        private val STRING_SERDE = Serdes.String()
        private val BASIC_LOREN_SERDE = CustomSerdes.basicLoremSerde()
        private val LOREM_NUMBER_SERDE = CustomSerdes.loremNumberSerde()
    }
}