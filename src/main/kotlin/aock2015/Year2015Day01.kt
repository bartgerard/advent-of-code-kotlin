package aock2015

data class Year2015Day01(
    private val text: String
) {

    companion object {
        fun parse(text: String): Year2015Day01 {
            return Year2015Day01(text)
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