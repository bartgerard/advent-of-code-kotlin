package aock2019.common

enum class ParameterMode {
    POSITION,
    IMMEDIATE,
    RELATIVE;

    companion object {
        fun toModes(code: Number): List<ParameterMode> {
            return code.toString()
                .map { it.digitToInt() }
                .reversed()
                .drop(2)
                .map { entries[it] }
                .toList()
        }
    }
}