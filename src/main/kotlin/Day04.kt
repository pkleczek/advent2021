package day04

import board.Cell
import board.GameBoard
import board.createGameBoard

fun part1(input: List<String>) {
    val numbers = input[0].split(",").map { it.toInt() }

    val boards = input
        .filterIndexed { index, line -> index > 0 && line.isNotBlank() }
        .windowed(size = 5, step = 5)

    val games = boards.map { board ->
        val game = createGameBoard<Int>(5)
        val matches = createGameBoard<Boolean>(5)

        board.map { line ->
            line.split("\\s+".toRegex())
                .filter { s -> s.isNotBlank() }
                .map { s -> s.toInt() }
        }.mapIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, i ->
                game[Cell(rowIndex + 1, columnIndex + 1)] = i
            }
        }

        game to matches
    }

    for (number in numbers) {
        games.forEach { (game, matches) ->
            val cell = game.find { it == number }
            if (cell != null) {
                matches[cell] = true
            }
        }
        val won = games.filter { (_, matches) ->
            val anyRow = (1..5).map {
                matches.getRow(it, (1..5)).all { cell -> matches[cell] == true }
            }.any { it }
            val anyCol = (1..5).map {
                matches.getColumn((1..5), it).all { cell -> matches[cell] == true }
            }.any { it }
            anyRow || anyCol
        }
        if (won.isNotEmpty()) {
            println("Winning number: $number")
            val points = won.map { (game, matches) ->
                matches.filter { it != true }
                    .map { game[it] }
                    .sumOf { it ?: 0 } * number
            }
            println(points)
            break
        }
    }


}

fun part2(input: List<String>) {
    val numbers = input[0].split(",").map { it.toInt() }

    val boards = input
        .filterIndexed { index, line -> index > 0 && line.isNotBlank() }
        .windowed(size = 5, step = 5)

    val games = boards.map { board ->
        val game = createGameBoard<Int>(5)
        val matches = createGameBoard<Boolean>(5)

        board.map { line ->
            line.split("\\s+".toRegex())
                .filter { s -> s.isNotBlank() }
                .map { s -> s.toInt() }
        }.mapIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, i ->
                game[Cell(rowIndex + 1, columnIndex + 1)] = i
            }
        }

        game to matches
    }

    val alreadyWon = mutableListOf<GameBoard<Boolean>>()
    for (number in numbers) {
        games.forEach { (game, matches) ->
            val cell = game.find { it == number }
            if (cell != null) {
                matches[cell] = true
            }
        }
        val won = games
            .filterNot { (_, matches) -> alreadyWon.contains(matches) }
            .filter { (_, matches) ->
            val anyRow = (1..5).map {
                matches.getRow(it, (1..5)).all { cell -> matches[cell] == true }
            }.any { it }
            val anyCol = (1..5).map {
                matches.getColumn((1..5), it).all { cell -> matches[cell] == true }
            }.any { it }
            anyRow || anyCol
        }
        if (won.isNotEmpty()) {
            println("Winning number: $number")
            val points = won.map { (game, matches) ->
                alreadyWon.add(matches)
                matches.filter { it != true }
                    .map { game[it] }
                    .sumOf { it ?: 0 } * number
            }
            println(points)
        }
    }


}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    part2(input)
}