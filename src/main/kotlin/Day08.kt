package day08

fun part1(input: List<String>) {
    val right = input.map { it.split("|")[1] }
    val total = right.flatMap { it.split(" ").filter { it.isNotBlank() } }.count { it.length in listOf(2, 3, 4, 7) }
    println(total)

}

fun part2(input: List<String>) {
    val countToDigit = mapOf(
        2 to setOf(1),
        3 to setOf(7),
        4 to setOf(4),
        5 to setOf(2, 3, 5),
        6 to setOf(0, 6, 9),
        7 to setOf(8),
    )

    val digitToSegments = mapOf(
        0 to setOf(1, 2, 3, 4, 5, 6),
        1 to setOf(2, 3),
        2 to setOf(1, 2, 4, 5, 7),
        3 to setOf(1, 2, 3, 4, 7),
        4 to setOf(2, 3, 6, 7),
        5 to setOf(1, 3, 4, 6, 7),
        6 to setOf(1, 3, 4, 5, 6, 7),
        7 to setOf(1, 2, 3),
        8 to setOf(1, 2, 3, 4, 5, 6, 7),
        9 to setOf(1, 2, 3, 4, 6, 7),
    )

    val allPairs = input.map { it.split("|") }.map { (l, r) -> clean(l) to clean(r) }

    val case = allPairs[0]

    val display = buildMap<Int, MutableList<MutableList<Char>>> {
        for (i in 1..7) {
            put(i, mutableListOf())
        }
    }

    for (signal in case.first) {
        val possibleDigits = countToDigit.getOrDefault(signal.length, setOf())
        for (digit in possibleDigits) {
            val segments = digitToSegments.getOrDefault(digit, setOf())
            for (segment in segments) {
                display[segment]?.add(signal.toMutableList())
            }
        }
    }

    println(display)

}

fun clean(s: String): List<String> {
    return s.split(" ").filter { it.isNotBlank() }
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}
