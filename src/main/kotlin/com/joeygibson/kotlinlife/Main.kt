package com.joeygibson.kotlinlife

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import entities.Board

class Main : CliktCommand(help = "Usage: kotlinlife") {
    val columns: Int by option("c", "columns", help = "number of columns of board (default: full screen)").int().default(80)
    val rows: Int by option("r", "rows", help = "number of rows of board (default: full screen)").int().default(24)
    val iterations by option("i", "iterations", help = "# of iterations").int().default(-1)
    val hacker: Boolean by option("H", "hacker", help = "seed with the hacker emblem").flag()
    val wait: Int by option("w", "wait", help = "milliseconds to sleep between iterations (default: 500)").int().default(500)
    val foreground: String by option("f", "foreground", help = "foregound color: black, red, green, yellow, blue, magenta, cyan, white [default]").default("white")
    val background: String by option("b", "background", help = "background color: black [default], red, green, yellow, blue, magenta, cyan, white").default("black")

    override fun run() {
        TODO("Not yet implemented")
    }
}

fun main(args: Array<String>) {
//    val board = Board(24, 80, true)
//    println("$board")
//
//    println("------------------------")
//
//    board.step()
//    println("$board")

    val m = Main().main(args)
}
