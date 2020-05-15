package com.joeygibson.kotlinlife

import entities.Board

class App {
    val greeting: String
        get() {
            return "Hello world."
        }
}

fun main(args: Array<String>) {
    val board = Board(24, 80, true)
    println("$board")

    println("------------------------")

    board.step()
    println("$board")
}
