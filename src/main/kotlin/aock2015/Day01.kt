package aock2015

data class Day01(
    private val text: String
) {

    companion object {
        fun parse(text: String): Day01 {
            return Day01(text)
        }
    }

    fun floor(): Int {
        return this.text.count { it == '(' } - this.text.count { it == ')' }
    }

    fun positionTillFirstArrivalAt(floor: Int): Int {
        var currentFloor = 0

        return this.text.map {
            if (it == '(') ++currentFloor else --currentFloor
        }
            .indexOf(floor) + 1
    }

}