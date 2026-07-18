package org.bytebloom

fun priorityValue(priority: Priority): Int {
        return when (priority) {
                Priority.URGENT -> 3
                Priority.STANDARD -> 2
                Priority.LOW -> 1
        }
}

fun compareByPriority(first: Package, second: Package): Boolean {
        val firstPriority = priorityValue(first.priority)
        val secondPriority = priorityValue(second.priority)
        return firstPriority > secondPriority
}

fun compareByWeight(first: Package, second: Package): Boolean {
        return first.weight > second.weight
}

fun isBetter(first: Package, second: Package): Boolean {
        if (first.priority != second.priority) {
                return compareByPriority(first, second)
        }

        return compareByWeight(first, second)
}


fun main() {
    

        // Test the CSV reading function
        val packages = readPackages("packages.csv")

        // Print all loaded items to verify they were parsed correctly
        println("\n--- Loaded Packages Data ---")
        println(packages.size)
        packages.forEach { pkg ->
            println("ID: ${pkg.id}, Weight: ${pkg.weight}, Dest: ${pkg.destinationHubId}, Priority: ${pkg.priority}")
        }

}

