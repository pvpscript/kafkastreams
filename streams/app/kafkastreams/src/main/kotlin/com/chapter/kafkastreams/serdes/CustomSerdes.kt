package com.chapter.kafkastreams.serdes

import com.chapter.kafkastreams.model.BasicLorem
import com.chapter.kafkastreams.model.LoremNumber
import com.chapter.kafkastreams.model.SizedLorem
import com.chapter.kafkastreams.serdes.config.JsonDeserializer
import com.chapter.kafkastreams.serdes.config.JsonSerializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes

object CustomSerdes {
    fun basicLoremSerde(): Serde<BasicLorem> = Serdes.serdeFrom(
        JsonSerializer<BasicLorem>(),
        JsonDeserializer(BasicLorem::class.java)
    )

    fun sizedLoremSerde(): Serde<SizedLorem> = Serdes.serdeFrom(
        JsonSerializer<SizedLorem>(),
        JsonDeserializer(SizedLorem::class.java)
    )

    fun loremNumberSerde(): Serde<LoremNumber> = Serdes.serdeFrom(
        JsonSerializer<LoremNumber>(),
        JsonDeserializer(LoremNumber::class.java)
    )
}