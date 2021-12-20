package day12

fun part1(input: Map<String, List<String>>) {
    var counter = 0
    fun visit(current: String, accessible: List<String>, path: List<String>, visited: Set<String>) {
        if (current != "end") {
            val lowercase = current.matches(Regex("^[a-z]+$"))
            val newVisited = if (lowercase) {
                visited + current
            } else visited
            for (toVisit in accessible) {
                if (toVisit !in newVisited) {
                    val next = input.getOrDefault(toVisit, listOf())
                    val foo = path + toVisit
                    visit(toVisit, next, foo, newVisited)
                }
            }
        } else {
            counter++
        }
    }

    visit("start", input.getOrDefault("start", listOf()), listOf("start"), setOf())
    println(counter)
}

fun part2(input: Map<String, List<String>>) {
    var counter = 0

    fun containsMoreThan(collection: List<String>, number: Int): Boolean {
        return collection
            .filter { it.matches(Regex("^[a-z]+$")) }
            .groupingBy { it }
            .eachCount()
            .values.any { it > number }
    }

    fun visit(current: String, accessible: List<String>, visited: Set<String>, path: List<String>) {
        if (current != "end") {
            val lowercase = current.matches(Regex("^[a-z]+$")) && current != "start"
            val anyVisitedTwice = containsMoreThan(path, 1)
            val currentPath = (path + current)

            if (lowercase && anyVisitedTwice && current in path) {
                return
            }

            for (node in accessible - visited) {
                val neighbours = input.getOrDefault(node, listOf()) - visited

                if (lowercase && anyVisitedTwice) {
                    visit(node, neighbours, visited + current, currentPath)
                } else {
                    visit(node, neighbours, visited, currentPath)
                }
            }
        } else {
            counter++
        }
    }

    val neighbours = input.getOrDefault("start", listOf())
    visit("start", neighbours, setOf("start"), listOf())
    println(counter)

}

fun main() {
    val input = generateSequence(::readlnOrNull).toList()
    val connections = input.flatMap {
        val (from, to) = it.split('-')
        listOf(from to to, to to from)
    }.groupBy({ it.first }, { it.second })
    part2(connections)
}