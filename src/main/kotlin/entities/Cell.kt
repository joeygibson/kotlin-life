package entities

class Cell(private val alive: Boolean) {
    fun step(neighbors: Array<Cell>): Cell {
        val liveCount = neighbors.filter { cell -> cell.alive }.count()

        return if (this.alive) {
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
            if (this.alive) {
                '*'
            } else {
                ' '
            }
}
