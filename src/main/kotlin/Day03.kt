package day03

import kotlin.math.pow

fun part1() {
    val entries = generateSequence(::readlnOrNull).toList()

    val count = entries
        .fold(mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) { acc, current ->
            current.forEachIndexed { index, c ->
                if (c == '1') {
                    acc[index]++
                }
            }
            acc
        }

    val size = entries.size

    val (hi, lo) = count.reversed().foldIndexed(0L to 0L) { index, acc, value ->
        if (value > size / 2) {
            2.0.pow(index).toLong() + acc.first to acc.second
        } else {
            acc.first to 2.0.pow(index).toLong() + acc.second
        }
    }

    println(hi * lo)
}

fun part2() {
    val entries = generateSequence(::readlnOrNull).toList()

    val oxy = (0..11).fold(entries) { acc, i ->
        if (acc.size == 1) {
            acc
        } else {
            val total = acc.size.toDouble()
            val ones = acc.count { it[i] == '1' }
            val toInclude = if (ones >= total / 2) '1' else '0'
            acc.filter {
                it[i] == toInclude
            }
        }
    }
    val co2 = (0..11).fold(entries) { acc, i ->
        if (acc.size == 1) {
            acc
        } else {
            val total = acc.size.toDouble()
            val ones = acc.count { it[i] == '1' }
            val toInclude = if (ones < total / 2) '1' else '0'
            acc.filter {
                it[i] == toInclude
            }
        }
    }
    println(Integer.parseInt(oxy.first(), 2) * Integer.parseInt(co2.first(), 2))
}

fun main() {
    part2()
}