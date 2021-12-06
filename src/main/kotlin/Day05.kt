package day05

fun part1(input: List<String>) {
    val notNumber = "[^0-9,]".toRegex()
    val counter: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    val vectors = input.map { line ->
        val (x1, y1, x2, y2) = line.split(notNumber)
            .filter { it.isNotBlank() }
            .flatMap { it.split(",") }
            .map { it.toInt() }
        listOf(x1 to y1, x2 to y2)
            .sortedWith(compareBy({ it.first }, { it.second }))
    }
    val matchingVectors = vectors.filter { (a, b) -> a.first == b.first || a.second == b.second }
    for ((from, to) in matchingVectors) {
        val first = listOf(from.first, to.first).sorted()
        val second = listOf(from.second, to.second).sorted()

        for (i in first[0]..first[1]) {
            for (j in second[0]..second[1]) {
                val key = i to j
                val current = counter.getOrPut(key) { 0 }
                counter[key] = current + 1
            }
        }
    }

    println(counter.values.count { it >= 2 })
}

fun part2(input: List<String>) {
    val notNumber = "[^0-9,]".toRegex()
    val counter: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    val vectors = input.map { line ->
        val (x1, y1, x2, y2) = line.split(notNumber)
            .filter { it.isNotBlank() }
            .flatMap { it.split(",") }
            .map { it.toInt() }
        listOf(x1 to y1, x2 to y2)
            .sortedWith(compareBy({ it.first }, { it.second }))
    }

    println(vectors)

    for ((from, to) in vectors) {
        println("$from -> $to:")
        val first = listOf(from.first, to.first).sorted()
        val second = listOf(from.second, to.second).sorted()

        // When you write cruds you forget math. Ugly code ahead.
        val x1 = from.first * -1
        val x2 = to.first * -1
        val y1 = from.second * -1
        val y2 = to.second * -1

        val a = (y2 - y1).toDouble() / (x2 - x1).toDouble()
        val b = y1 - a * x1

        println("y = ${a}x + $b ")

        for (i in first[0]..first[1]) {
            for (j in second[0]..second[1]) {
                val y = a * (i * -1) + b
                if(y == (j * -1).toDouble() || a == Double.POSITIVE_INFINITY || a == Double.NEGATIVE_INFINITY) {
                    print("$i, $j / ")
                    val key = i to j
                    val current = counter.getOrPut(key) { 0 }
                    counter[key] = current + 1
                }
            }
        }
        println()
    }

    println(counter.values.count { it >= 2 })
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}