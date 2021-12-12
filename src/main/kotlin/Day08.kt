package day08

fun part1(input: List<String>) {
    val right = input
        .map { it.split("|")[1] }
    val total = right
        .flatMap { it.split(" ").filter { it.isNotBlank() } }
        .count { it.length in listOf(2, 3, 4, 7) }
    println(total)

}

fun part2(input: List<String>) {
    TODO("Not yet implemented")
}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part1(input)
}
