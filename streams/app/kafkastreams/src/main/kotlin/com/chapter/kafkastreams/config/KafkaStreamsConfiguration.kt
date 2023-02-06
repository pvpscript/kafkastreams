package com.chapter.kafkastreams.config

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams

@Configuration
@EnableKafka
@EnableKafkaStreams
class KafkaStreamsConfig