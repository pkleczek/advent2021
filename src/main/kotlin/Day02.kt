package day02

data class Position(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0)
enum class Direction { FORWARD, UP, DOWN }

data class Command (val direction: Direction, val distance: Int) {
    companion object {
        fun parse(input: String): Command {
            val (command, distance) = input.split(' ')
            return Command(Direction.valueOf(command.uppercase()), distance.toInt())
        }
    }
}

fun part1() {
    val position = generateSequence(::readlnOrNull)
        .map { Command.parse(it) }
        .fold(Position()) { acc, command ->
            when (command.direction) {
                Direction.FORWARD -> Position(acc.horizontal + command.distance, acc.depth)
                Direction.DOWN -> Position(acc.horizontal, acc.depth + command.distance)
                Direction.UP -> Position(acc.horizontal, acc.depth - command.distance)
            }
        }
    println(position.horizontal * position.depth)
}

fun part2() {
    val position = generateSequence(::readlnOrNull)
        .map { Command.parse(it) }
        .fold(Position()) { acc, command ->
            when (command.direction) {
                Direction.FORWARD -> Position(
                    acc.horizontal + command.distance,
                    acc.depth + (acc.aim * command.distance),
                    acc.aim
                )
                Direction.DOWN -> Position(acc.horizontal, acc.depth, acc.aim + command.distance)
                Direction.UP -> Position(acc.horizontal, acc.depth, acc.aim - command.distance)
            }
        }
    println(position.horizontal * position.depth)
}

fun main() {
    part2()
}
