package aock2015

data class Year2015Day01(
    private val input: String
) {
    fun floor(): Int = this.input.count { it == '(' } - this.input.count { it == ')' }

    fun positionTillFirstArrivalAt(floor: Int): Int {
        var currentFloor = 0

        return this.input.map {
            if (it == '(') ++currentFloor else --currentFloor
        }
            .indexOf(floor) + 1
    }

}