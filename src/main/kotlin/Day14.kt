package day14

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

fun part2(input: List<String>) {
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
    part1(spec)
}