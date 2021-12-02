package day02

fun part1() {
    data class Position(var horizontal: Int = 0, var depth: Int = 0)

    val position = generateSequence(::readlnOrNull)
        .map { it.split(' ') }
        .map { it[0] to it[1].toInt() }
        .fold(Position()) { acc, pair ->
            when (pair.first) {
                "forward" -> Position(acc.horizontal + pair.second, acc.depth)
                "down" -> Position(acc.horizontal, acc.depth + pair.second)
                "up" -> Position(acc.horizontal, acc.depth - pair.second)
                else -> acc
            }
        }
    println(position.horizontal * position.depth)

}

fun part2() {
    data class Position(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0)

    val position = generateSequence(::readlnOrNull)
        .map { it.split(' ') }
        .map { it[0] to it[1].toInt() }
        .fold(Position()) { acc, pair ->
            when (pair.first) {
                "forward" -> Position(acc.horizontal + pair.second, acc.depth + (acc.aim * pair.second), acc.aim)
                "down" -> Position(acc.horizontal, acc.depth, acc.aim + pair.second)
                "up" -> Position(acc.horizontal, acc.depth, acc.aim - pair.second)
                else -> acc
            }
        }
    println(position.horizontal * position.depth)

}

fun main() {
    part2()
}
