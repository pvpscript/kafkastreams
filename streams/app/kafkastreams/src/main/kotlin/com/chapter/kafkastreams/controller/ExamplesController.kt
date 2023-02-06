package com.chapter.kafkastreams.controller

import com.chapter.kafkastreams.examples.aggregate.AggregatedLoremIpsum
import com.chapter.kafkastreams.examples.filter.FilteredLoremIpsum
import com.chapter.kafkastreams.examples.tables.TabledNumbers
import com.chapter.kafkastreams.examples.join.WindowedJoin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("kafka-streams")
class ExamplesController(
    private val filteredLoremIpsum: FilteredLoremIpsum,
    private val aggregatedLoremIpsum: AggregatedLoremIpsum,
    private val tabledNumbers: TabledNumbers,
    private val windowedJoin: WindowedJoin,
) {
    @GetMapping("filter")
    fun filter() = filteredLoremIpsum.runStreamFilter()

    @GetMapping("aggregate")
    fun aggregate() = aggregatedLoremIpsum.runStreamAggregation()

    @GetMapping("table")
    fun table() = tabledNumbers.runTableCreation()

    @GetMapping("join")
    fun join() = windowedJoin.runStreamJoin()
}