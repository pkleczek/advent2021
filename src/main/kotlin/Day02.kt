package day02

data class Position(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0)
enum class Direction { FORWARD, UP, DOWN }

data class Command(val direction: Direction, val distance: Int) {
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
                Direction.FORWARD -> acc.copy(horizontal = acc.horizontal + command.distance)
                Direction.DOWN -> acc.copy(depth = acc.depth + command.distance)
                Direction.UP -> acc.copy(depth = acc.depth - command.distance)
            }
        }
    println(position.horizontal * position.depth)
}

fun part2() {
    val position = generateSequence(::readlnOrNull)
        .map { Command.parse(it) }
        .fold(Position()) { acc, command ->
            when (command.direction) {
                Direction.FORWARD -> acc.copy(
                    horizontal = acc.horizontal + command.distance,
                    depth = acc.depth + acc.aim * command.distance
                )
                Direction.DOWN -> acc.copy(aim = acc.aim + command.distance)
                Direction.UP -> acc.copy(aim = acc.aim - command.distance)
            }
        }
    println(position.horizontal * position.depth)
}

fun main() {
    part2()
}
