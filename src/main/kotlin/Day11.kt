package day11

private val offsetMatrix = (-1..1).flatMap { i ->
    (-1..1).map { j ->
        i to j
    }
}.filterNot { it.first == 0 && it.second == 0 }

data class Octopus(var energy: Int)

fun part1(input: List<List<Int>>) {
    val state = input.foldIndexed(mapOf<Pair<Int, Int>, Octopus>()) { i, acc, row ->
        acc + row.mapIndexed { j, value -> (i to j) to Octopus(value) }
    }

    var total = 0

    repeat(100) {
        val exploded = mutableSetOf<Pair<Int, Int>>()
        val toVisit = mutableListOf<Pair<Int, Int>>()

        state.values.forEach { it.energy += 1 }

        do {
            toVisit.forEach {
                state[it]!!.energy += 1
            }

            toVisit.clear()

            val newlyExploded = state.entries
                .filterNot { (key, _) -> exploded.contains(key) }
                .filter { (_, value) -> value.energy > 9 }

            newlyExploded.map { it.key }.also { exploded.addAll(it) }
            newlyExploded.forEach { it.value.energy = 0 }

            total += newlyExploded.size

            val neighbours = getNeighbours(newlyExploded, state, exploded)

            toVisit.addAll(neighbours)
        } while (toVisit.isNotEmpty())

        exploded.clear()
        toVisit.clear()
    }
    println(total)
}

fun part2(input: List<List<Int>>) {
    val state = input.foldIndexed(mapOf<Pair<Int, Int>, Octopus>()) { i, acc, row ->
        acc + row.mapIndexed { j, value -> (i to j) to Octopus(value) }
    }

    var total = 0

    do {
        val exploded = mutableSetOf<Pair<Int, Int>>()
        val toVisit = mutableListOf<Pair<Int, Int>>()

        state.values.forEach { it.energy += 1 }

        do {
            toVisit.forEach {
                state[it]!!.energy += 1
            }

            toVisit.clear()

            val newlyExploded = state.entries
                .filterNot { (key, _) -> exploded.contains(key) }
                .filter { (_, value) -> value.energy > 9 }

            newlyExploded.map { it.key }.also { exploded.addAll(it) }
            newlyExploded.forEach { it.value.energy = 0 }

            val neighbours = getNeighbours(newlyExploded, state, exploded)

            toVisit.addAll(neighbours)
        } while (toVisit.isNotEmpty())

        exploded.clear()
        toVisit.clear()
        total += 1

    } while (state.values.any { it.energy != 0 })

    println(total)
}

private fun getNeighbours(
    newlyExploded: List<Map.Entry<Pair<Int, Int>, Octopus>>,
    state: Map<Pair<Int, Int>, Octopus>,
    exploded: MutableSet<Pair<Int, Int>>
) = newlyExploded.fold(listOf<Pair<Int, Int>>()) { acc, entry ->
    acc + offsetMatrix
        .map { (i, j) -> entry.key.first + i to entry.key.second + j }
        .filter { state.containsKey(it) }
        .filterNot { exploded.contains(it) }
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    val digits = input.map { line -> line.toList().map { char -> char.digitToInt() } }
    part2(digits)
}