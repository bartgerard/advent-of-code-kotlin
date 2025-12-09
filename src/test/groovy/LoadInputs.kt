import shared.downloadInputFile

fun main() = loadDay(2025, 10)

private fun loadDay(year: Int, day: Int) = downloadInputFile(year, day)

private fun loadAllYears() = (2015..2024).forEach { year ->
    (1..25).forEach { day -> downloadInputFile(year, day) }
}