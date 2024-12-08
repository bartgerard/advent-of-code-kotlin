import shared.downloadInputFile

fun main() = loadDay(2024, 9)

private fun loadDay(year: Int, day: Int) = downloadInputFile(year, day)

private fun loadAllYears() = (2015..2023).forEach { year ->
    (1..25).forEach { day ->
        downloadInputFile(year, day)
    }
}