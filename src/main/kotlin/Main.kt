package org.bytebloom

private fun priorityValue(priority: priorityRow): Int {
    return when (priority) {
        priorityRow.URGENT -> 3
        priorityRow.STANDARD -> 2
        priorityRow.LOW -> 1
    }
}

private fun hasHigherPriority(first: packageRow, second: packageRow): Boolean {
    return priorityValue(first.priority) > priorityValue(second.priority)
}

private fun hasGreaterWeight(first: packageRow, second: packageRow): Boolean {
    return first.weight > second.weight
}

private fun shouldComeBefore(first: packageRow, second: packageRow): Boolean {
    if (first.priority != second.priority) {
        return hasHigherPriority(first, second)
    }

    return hasGreaterWeight(first, second)
}

fun selectionSortPackages(packages: MutableList<packageRow>) {

    for (currentIndex in 0 until packages.size - 1) {

        var selectedIndex = currentIndex

        for (candidateIndex in currentIndex + 1 until packages.size) {

            if (shouldComeBefore(packages[candidateIndex], packages[selectedIndex])) {
                selectedIndex = candidateIndex
            }
        }

        if (selectedIndex != currentIndex) {
            val temp = packages[currentIndex]
            packages[currentIndex] = packages[selectedIndex]
            packages[selectedIndex] = temp
        }
    }
}

private fun printTopPackages(packages: List<packageRow>, count: Int) {
    println("\nTop Priority Packages:\n")

    packages.take(count).forEachIndexed { index, packageItem ->
        println("${index + 1}.")
        println("ID: ${packageItem.id}")
        println("Weight: ${packageItem.weight}")
        println("Priority: ${packageItem.priority}")
        println("Destination: ${packageItem.destinationHubId}")
        println()
    }
}

fun main() {
    val packages = readPackages("packages.csv").toMutableList()

    selectionSortPackages(packages)

    printTopPackages(packages, 3)
}