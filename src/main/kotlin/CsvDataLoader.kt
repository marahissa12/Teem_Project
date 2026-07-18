package org.bytebloom
import java.io.File
import java.io.IOException

fun parsePriority(value: String): Priority {

    return when(value.trim().uppercase()) {

        "URGENT" -> Priority.URGENT

        "STANDARD" -> Priority.STANDARD

        "LOW" -> Priority.LOW

        else -> {
            println(
                "Warning: Unknown priority '$value'. " +
                        "Using LOW as default."
            )

            Priority.LOW
        }
    }
}

fun readPackages(fileName: String): List<Package> {

    val requiredColumns = 4
    val file = File("src/resources/$fileName")

    if (!file.exists()) {
        println("Warning: File '$fileName' was not found!")
        return emptyList()
    }

    val packages = mutableListOf<Package>()

    try {

        file.useLines { lines ->

            lines
                .drop(1) // Skip CSV header
                .forEachIndexed { index, line ->

                    val lineNumber = index + 2 // +2 because header is line 1


                    // Requirement 1: Ignore blank lines
                    if (line.trim().isEmpty()) {
                        return@forEachIndexed
                    }


                    val columns = line.split(",")
                        .map { it.trim() }


                    if (columns.size != requiredColumns) {

                        println(
                            "Warning: Skipping line $lineNumber. " +
                                    "Expected $requiredColumns columns but found ${columns.size}"
                        )

                        return@forEachIndexed
                    }


// Check required string fields
                    if (columns[0].isBlank() || columns[2].isBlank()) {

                        println(
                            "Warning: Skipping line $lineNumber. " +
                                    "Missing required data (ID or Destination)"
                        )

                        return@forEachIndexed
                    }


                    // Requirement 3: Safe numeric conversion
                    val weight = columns[1].toDoubleOrNull()

                    if (weight == null) {

                        println("Warning: Invalid weight at line $lineNumber. " +
                                "Invalid weight")
                        return@forEachIndexed

                    }


                    // Requirement 4: Safe priority parsing
                    val priority = parsePriority(columns[3])


                    val packageItem = Package(
                        id = columns[0],
                        weight = weight,
                        destinationHubId = columns[2],
                        priority = priority
                    )


                    packages.add(packageItem)
                }
        }


    } catch (e: IOException) {

        println("Error reading file '$fileName': ${e.message}")
        return emptyList()
    }


    println("Successfully parsed packages: ${packages.size}")

    return packages
}