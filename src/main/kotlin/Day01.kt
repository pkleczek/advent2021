import java.io.File

fun part1() {
    val count = generateSequence(::readlnOrNull)
        .map { it.toInt() }
        .zipWithNext { a, b ->  b > a}
        .filter { it }
        .count()
    println(count)
}

fun part2() {
    val count = generateSequence(::readlnOrNull)
        .map { it.toInt() }
        .windowed(3)
        .map { it.sum() }
        .zipWithNext { a, b ->  b > a}
        .filter { it }
        .count()
    println(count)
}

fun main() {
    part2()
}