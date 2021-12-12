package day07

import kotlin.math.absoluteValue

fun part1(input: List<String>) {
    val positions = input.first().split(",").map { it.toInt() }
    val counted = positions.groupingBy { it }.eachCount()

    val min = counted.keys.minOf { it }
    val max = counted.keys.maxOf { it }

    val minimal = (min..max).map { position ->
        counted.entries.fold(0) { acc, (key, count) ->
            acc + (position - key).absoluteValue * count
        }
    }.minOf { it }

    println(minimal)
}

fun part2(input: List<String>) {
    val positions = input.first().split(",").map { it.toInt() }
    val counted = positions.groupingBy { it }.eachCount()

    val min = counted.keys.minOf { it }
    val max = counted.keys.maxOf { it }

    val minimal = (min..max).map { position ->
        counted.entries.fold(0) { acc, (key, count) ->
            val distance = (position - key).absoluteValue
            val consumedFuel = (1 + distance) * distance / 2
            acc + consumedFuel * count
        }
    }.minOf { it }

    println(minimal)
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}