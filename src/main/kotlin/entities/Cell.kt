package entities

class Cell(val alive: Boolean) {
    fun isAlive(): Boolean = alive

    fun step(neighbors: Array<Cell>): Cell {
        val liveCount = neighbors.filter { cell -> cell.isAlive() }.count()

        return if (this.isAlive()) {
            if (liveCount < 2 || liveCount > 3) {
                Cell(false)
            } else {
                Cell(true)
            }
        } else {
            if (liveCount == 3) {
                Cell(true)
            } else {
                Cell(false)
            }
        }
    }

    fun toPrintableChar(): Char =
            if (this.isAlive()) {
                '*'
            } else {
                ' '
            }
}
