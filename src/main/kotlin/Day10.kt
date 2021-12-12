package day10

import java.util.*

fun part1(input: List<String>) {
    val matching = mapOf('[' to ']', '(' to ')', '{' to '}', '<' to '>')
    val invalid = mutableListOf<Char>()
    for (line in input) {
        val stack = Stack<Char>()

        for (char in line.toList()) {
            val (success, last) = when (char) {
                in matching.keys -> {
                    stack.push(char)
                    true to char
                }
                in matching.values -> {
                    val last = stack.pop()
                    (matching[last] == char) to char
                }
                else -> true to char
            }
            if (!success) {
                invalid.add(last)
            }
        }
    }
    val sum = invalid.sumOf {
        when (it) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }.toInt()
    }
    println(sum)
}

fun part2(input: List<String>) {
    val matching = mapOf('[' to ']', '(' to ')', '{' to '}', '<' to '>')

    val stacks = input.map { line ->
        val stack = Stack<Char>()
        var invalid = false
        for (char in line.toList()) {
            val success = when (char) {
                in matching.keys -> {
                    stack.push(char)
                    true
                }
                in matching.values -> {
                    val last = stack.pop()
                    matching[last] == char
                }
                else -> true
            }
            if (!success) {
                invalid = true
                break
            }
        }
        if (invalid) Stack<Char>() else stack
    }

    val allSums = stacks
        .filter { it.isNotEmpty() }
        .map { stack ->
            val closing = stack.reversed().map { matching[it] }
            closing.fold(0L) { acc, char ->
                acc * 5 + when (char) {
                    ')' -> 1
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> 0
                }
            }
        }.sorted()
    println(allSums[allSums.size / 2])
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}