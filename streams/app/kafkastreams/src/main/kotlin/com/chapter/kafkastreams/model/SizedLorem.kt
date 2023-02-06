package com.chapter.kafkastreams.model

data class SizedLorem(
    val size: Int,
    val line: String,
)
// { "size": <num>, "line": "sentence" }

fun BasicLorem.toSizeLoren() =
    SizedLorem(
        size = line.length,
        line = line,
    )
