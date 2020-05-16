package com.joeygibson.kotlinlife

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import com.googlecode.lanterna.Symbols
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import entities.Board


class KotlinLife : CliktCommand() {
    private val columns: Int? by option("-c", "--columns", help = "number of columns of board (default: full screen)").int()
    private val rows: Int? by option("-r", "--rows", help = "number of rows of board (default: full screen)").int()
    private val iterations: Int? by option("-i", "--iterations", help = "# of iterations").int()
    private val hacker: Boolean by option("-H", "--hacker", help = "seed with the hacker emblem").flag()
    private val wait: Long? by option("-w", "--wait", help = "milliseconds to sleep between iterations (default: 500)").long()
    private val foreground: String? by option("-f", "--foreground", help = "foregound color: black, red, green, yellow, blue, magenta, cyan, white [default]")
    private val background: String? by option("-b", "--background", help = "background color: black [default], red, green, yellow, blue, magenta, cyan, white")

    override fun run() {
        val foregroundColor = TextColor.Factory.fromString(foreground ?: "white")
        val backgroundColor = TextColor.Factory.fromString(background ?: "black")

        val terminal = setupScreen(foregroundColor, backgroundColor)

        val columns = columns ?: terminal.terminalSize.columns
        val rows = rows ?: terminal.terminalSize.rows

        val iterations = iterations ?: -1
        val wait = wait ?: 500L

        play(terminal, columns - 2, rows - 2, iterations, hacker, wait)
    }

    private fun play(terminal: Terminal, columns: Int, rows: Int, iterations: Int,
                     hacker: Boolean, wait: Long) {
        val board = Board(rows, columns, hacker)

        displayBoard(terminal, board, columns, rows)

        if (iterations > 0) {
            for (i in 0..iterations) {
                board.step()
                displayBoard(terminal, board, columns, rows)

                terminal.pollInput()?.let {
                    return
                }

                Thread.sleep(wait)
            }
        } else {
            while (true) {
                board.step()
                displayBoard(terminal, board, columns, rows)

                terminal.pollInput()?.let {
                    return
                }
                
                Thread.sleep(wait)
            }
        }
    }

    private fun displayBoard(terminal: Terminal, board: Board, columns: Int, rows: Int) {
        terminal.clearScreen()

        // top line
        terminal.setCursorPosition(0, 0)
        terminal.putCharacter(Symbols.SINGLE_LINE_TOP_LEFT_CORNER)

        for (i in 0 until columns) {
            terminal.putCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
        }

        terminal.putCharacter(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER)

        var count = 0

        // board data
        for (row in board.iterator().withIndex()) {
            terminal.setCursorPosition(0, row.index + 1)
            terminal.putCharacter(Symbols.SINGLE_LINE_VERTICAL)

            for (cell in row.value.iterator().withIndex()) {
                terminal.putCharacter(cell.value.toPrintableChar())
            }

            terminal.putCharacter(Symbols.SINGLE_LINE_VERTICAL)
            count++
        }

        // bottom line
        terminal.setCursorPosition(0, rows)
        terminal.putCharacter(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER)

        for (i in 0 until columns) {
            terminal.putCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
        }

        terminal.putCharacter(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER)

        terminal.flush()
    }

    private fun setupScreen(foreground: TextColor, background: TextColor): Terminal {
        val defaultTerminalFactory = DefaultTerminalFactory()
        val terminal = defaultTerminalFactory.createTerminal()

        terminal.setCursorVisible(false)
        terminal.setForegroundColor(foreground)
        terminal.setBackgroundColor(background)

        return terminal
    }
}

fun main(args: Array<String>) {
    KotlinLife().main(args)
}
