import shared.downloadInputFile

fun main() {
    loadDay(2024, 2)
}

private fun loadDay(
    year: Int,
    day: Int
) {
    downloadInputFile(year, day)
}

private fun loadAllYears() {
    for (year in 2015..2023) {
        for (day in 1..25) {
            downloadInputFile(year, day)
        }
    }
}