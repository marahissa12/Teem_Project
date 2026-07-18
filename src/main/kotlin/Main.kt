package org.bytebloom

fun main() {
    

        // Test the CSV reading function
        val packages = readPackages("packages.csv")

        // Print all loaded items to verify they were parsed correctly
        println("\n--- Loaded Packages Data ---")
        packages.forEach { pkg ->
            println("ID: ${pkg.id}, Weight: ${pkg.weight}, Dest: ${pkg.destinationHubId}, Priority: ${pkg.priority}")
        }

}

