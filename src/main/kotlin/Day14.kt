package day14

import java.util.*

data class Spec(val initial: String, val transitions: Map<String, String>)

fun part1(input: Spec) {
    var iteration = input.initial

    repeat(10) { i ->
        println("iteration $i")
        val toInsert = iteration
            .windowed(size = 2)
            .map { input.transitions[it] ?: "-" }

        val chars = iteration.toList()
        val joined = chars
            .take(chars.size - 1)
            .mapIndexed { index, c -> c + toInsert[index] }
            .filterNot { it == "-" }
            .joinToString("") + chars.last()
        iteration = joined
    }

    val counted = iteration.groupingBy { it }.eachCount()
    val max = counted.maxOf { it.value }
    val min = counted.minOf { it.value }
    println(max - min)

}

fun part2(input: Spec) {
    fun expand(input: LinkedList<Char>, mapping: Map<String, String>) {
        val iterator = input.listIterator()
        var previous: Char? = null
        while (iterator.hasNext()) {
            val current = iterator.next()
            if (previous != null) {
                val mapped = mapping["$previous$current"]
                if (mapped != null) {
                    iterator.set(mapped[0])
                    iterator.add(current)
                }

            }
            previous = current
        }
    }

    fun transform(input: LinkedList<Char>, mapping: Map<String, String>, counter: Int): Map<Char, Int> {
        if (counter == 0)
            return input.groupingBy { it }.eachCount()

        expand(input, mapping)

        return input
            .windowed(2)
            .map {
                transform(LinkedList(it), mapping, counter - 1)
            }
            .fold((mapOf())) { a, v -> (a.keys + v.keys).associateWith { (a[it] ?: 0) + (v[it] ?: 0) } }
    }

    val transform = input.initial.windowed(2).map {
        transform(LinkedList(it.toList()), input.transitions, 10)
    }
        .fold((mapOf<Char, Int>())) { a, v ->
            (a.keys + v.keys).associateWith {
                (a[it] ?: 0) + (v[it] ?: 0)
            }
        }

    println(transform)
    val max = transform.maxOf { it.value }
    val min = transform.minOf { it.value }
    println(max - min)

    // TODO: figure out something clever for part 2, brute force takes too much mem
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()

    val initial = input.first()
    val transitions = input
        .filter { it.contains("->") }
        .map { it.split(" -> ") }
        .associate { (from, to) -> from to to }

    val spec = Spec(initial, transitions)
    println(spec)
    part2(spec)
}