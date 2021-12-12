package day09

fun part1(input: List<String>) {
    val numbers = input.map { line ->
        line.toList().map { c -> c.digitToInt() }
    }

    val lower = mutableListOf<Int>()

    numbers.forEachIndexed { i, _ ->
        numbers[i].forEachIndexed { j, value ->
            val up = numbers.getOrNull(i - 1)?.getOrNull(j)
            val down = numbers.getOrNull(i + 1)?.getOrNull(j)
            val left = numbers.getOrNull(i)?.getOrNull(j - 1)
            val right = numbers.getOrNull(i)?.getOrNull(j + 1)

            val valid = listOfNotNull(up, down, left, right)
                .all { value < it }

            if (valid)
                lower.add(value)

        }
    }

    println(lower.sumOf { it + 1 })

}

fun part2(input: List<String>) {
    val numbers = input.map { line ->
        line.toList().map { c -> c.digitToInt() }
    }

    val lower = mutableListOf<Pair<Int, Int>>()

    numbers.forEachIndexed { i, _ ->
        numbers[i].forEachIndexed { j, value ->
            val valid = listOf(i to j + 1, i to j - 1, i + 1 to j, i - 1 to j)
                .mapNotNull { (i, j) -> numbers.value(i, j) }
                .all { value < it }

            if (valid)
                lower.add(i to j)

        }
    }

    val sizes = lower.map { point ->
        val toVisit = mutableListOf(point)
        val visited = mutableSetOf<Pair<Int, Int>>()
        var size = 1

        do {
            val next = toVisit.removeFirst()
            val (i, j) = next

            val visitable =
                listOf(i to j + 1, i to j - 1, i + 1 to j, i - 1 to j)
                    .filter { (i, j) -> numbers.value(i, j) != null }
                    .filter { (i, j) -> numbers[i][j] < 9 }
                    .filterNot { (i, j) -> toVisit.contains(i to j) }
                    .filterNot { (i, j) -> visited.contains(i to j) }

            toVisit.addAll(visitable)
            visited.add(next)
            size += visitable.size

        } while (toVisit.isNotEmpty())
        size
    }

    val largest = sizes
        .sortedDescending()
        .take(3)
        .also { println(it) }
        .reduce { i, j -> i * j }

    println(largest)
}

fun List<List<Int>>.value(i: Int, j: Int): Int? = this.getOrNull(i)?.getOrNull(j)

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}