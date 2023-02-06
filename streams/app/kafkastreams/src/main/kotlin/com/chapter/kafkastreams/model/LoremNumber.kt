package com.chapter.kafkastreams.model

data class LoremNumber(
    val line: String,
    val number: Int,
)
// { "line": "sentence", "number": <num> }

fun BasicLorem.toLoremNumber(number: Int) =
    LoremNumber(
        line = line,
        number = number
    )
