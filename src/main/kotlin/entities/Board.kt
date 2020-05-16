package entities

import kotlin.random.Random.Default.nextInt

class Board(private val rows: Int, private val columns: Int, hacker: Boolean) :
        Iterator<Array<Cell>> {
    private var cells = arrayOf<Array<Cell>>()
    private var iteratorPosition: Int = 0

    init {
        val seeds = if (hacker) {
            hackerSeed()
        } else {
            randomSeed()
        }

        cells = createCells(rows, columns, seeds)
    }

    private fun hackerSeed(): Array<Pair<Int, Int>> = arrayOf(
            Pair(0, 1),
            Pair(1, 2),
            Pair(2, 0),
            Pair(2, 1),
            Pair(2, 2))

    private fun randomSeed(): Array<Pair<Int, Int>> {
        var seeds = arrayOf<Pair<Int, Int>>()

        var times = 0

        while (times == 0) {
            times = nextInt(1, rows * columns)
        }

        for (i in 0 until times) {
            val row = nextInt(0, rows)
            val col = nextInt(0, columns)

            seeds += Pair(row, col)
        }

        return seeds
    }

    fun step() {
        var newCells = arrayOf<Array<Cell>>()

        for (i in 0 until rows) {
            var row = arrayOf<Cell>()

            for (j in 0 until columns) {
                val neighbors = getNeighbors(i, j)

                row += cells[i][j].step(neighbors)
            }

            newCells += row
        }

        this.cells = newCells
        this.iteratorPosition = 0
    }

    private fun getNeighbors(row: Int, column: Int): Array<Cell> {
        var neighbors = arrayOf<Cell>()

        for (i in -1 until 2) {
            for (j in -1 until 2) {
                var newRow = row + i
                var newCol = column + j

                if (newRow >= rows) {
                    newRow = 0
                } else if (newRow < 0) {
                    newRow = rows - 1
                }

                if (newCol >= columns) {
                    newCol = 0
                } else if (newCol < 0) {
                    newCol = columns - 1
                }

                if (!(newRow == row && newCol == column)) {
                    neighbors += cells[newRow][newCol]
                }
            }
        }

        return neighbors
    }

    private fun createCells(rows: Int, columns: Int, seeds: Array<Pair<Int, Int>>?): Array<Array<Cell>> {
        var cells = arrayOf<Array<Cell>>()

        for (i in 0 until rows) {
            var array = arrayOf<Cell>()

            for (j in 0 until columns) {
                array += Cell(false)
            }

            cells += array
        }

        seeds?.forEach { seed ->
            cells[seed.first][seed.second] = Cell(true)
        }

        return cells
    }

    override fun toString(): String {
        var output = arrayOf<String>()

        for (row in cells) {
            output += row.map { cell -> cell.toPrintableChar() }.joinToString("")
        }

        return output.joinToString("\n")
    }

    override fun hasNext(): Boolean = iteratorPosition < rows

    override fun next(): Array<Cell> {
        val row = cells[iteratorPosition]

        iteratorPosition += 1

        return row
    }
}