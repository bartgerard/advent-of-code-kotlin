package shared

data class BinaryNumber(
    private val bits: String
) {
    companion object {
        val REGEX = "[01]+".toRegex()

        @JvmStatic
        fun parse(value: String) = BinaryNumber(value)
        fun parse(value: Int) = BinaryNumber(value.toString(2))
        fun parse(value: Int, length: Int) = BinaryNumber(value.toString(2).padStart(length, '0'))

        fun mask(length: Int) = (1 shl length) - 1
        fun forMask(length: Int) = parse(mask(length))
    }

    init {
        require(bits.matches(REGEX)) { "invalid binary number $bits" }
    }

    val intValue: Int get() = bits.toInt(2)

    fun inverse(): BinaryNumber = parse(mask(bits.length) and intValue.inv(), bits.length)

    override fun toString(): String = bits
}