package com.chapter.kafkastreams.utils

import com.chapter.kafkastreams.config.StreamProperties
import java.time.Duration
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.springframework.stereotype.Component

@Component
class KafkaStreamHandler(
    private val streamProperties: StreamProperties,
) {
    fun builtStream(func: (StreamsBuilder) -> Unit) =
        StreamsBuilder()
            .also(func)
            .let { KafkaStreams(it.build(), streamProperties.props()) }
            .also { it.start() }
}