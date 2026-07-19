package org.bytebloom

private fun priorityValue(priority: Priority): Int {
    return when (priority) {
        Priority.URGENT -> 3
        Priority.STANDARD -> 2
        Priority.LOW -> 1
    }
}

private fun hasHigherPriority(first: Package, second: Package): Boolean {
    return priorityValue(first.priority) > priorityValue(second.priority)
}

private fun hasGreaterWeight(first: Package, second: Package): Boolean {
    return first.weight > second.weight
}

private fun shouldComeBefore(first: Package, second: Package): Boolean {
    if (first.priority != second.priority) {
        return hasHigherPriority(first, second)
    }

    return hasGreaterWeight(first, second)
}

fun selectionSortPackages(packages: MutableList<Package>) {

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

private fun printTopPackages(packages: List<Package>, count: Int) {
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