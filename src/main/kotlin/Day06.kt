package day06

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

fun part1(input: List<String>) {
    var numbers = input.first().split(",").map { it.toInt() }.toMutableList()

    repeat(18) {
        val lowered = numbers
            .map { it - 1 }

        val negative = lowered.count { it < 0 }

        numbers = lowered
            .map { if (it < 0) 6 else it }
            .toMutableList()

        numbers.addAll(MutableList(negative) { 8 })
    }

    println(numbers)
    println(numbers.size)
}

fun part2(input: List<String>) {
    val counted = arrayOf<BigInteger>(ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO)

    val numbers = input.first().split(",").map { it.toBigInteger() }
    numbers.forEach { counted[it.toInt()] += ONE }

    repeat(256) {
        val negative = counted[0]
        counted[0] = ZERO

        for (i in 1 until counted.size) {
            counted[i - 1] = counted[i]
            counted[i] = ZERO
        }
        counted[6] += negative
        counted[8] += negative
    }

    println(counted.reduce { a, b -> a + b })
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}