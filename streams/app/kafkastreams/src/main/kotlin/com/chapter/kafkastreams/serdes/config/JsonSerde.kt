package com.chapter.kafkastreams.serdes.config

import com.google.gson.GsonBuilder
import java.nio.charset.StandardCharsets
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer

internal class JsonSerializer<T> : Serializer<T> {
    private val gson = GsonBuilder().create()

    override fun serialize(topic: String?, data: T?) =
        gson.toJson(data).toByteArray(StandardCharsets.UTF_8)
}

internal class JsonDeserializer<T>(
    private val type: Class<T>,
) : Deserializer<T> {
    private val gson = GsonBuilder().create()

    override fun deserialize(topic: String?, data: ByteArray?): T? =
        runCatching { gson.fromJson(data?.decodeToString(), type) }
            .getOrNull()
}