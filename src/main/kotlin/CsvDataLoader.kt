package org.bytebloom

import java.io.File
import java.io.IOException

private const val RESOURCE_PATH = "src/resources"

// Packages Reader

fun parsePriority(value: String): Priority {
    return when (value.trim().uppercase()) {
        "URGENT" -> Priority.URGENT
        "STANDARD" -> Priority.STANDARD
        "LOW" -> Priority.LOW
        else -> {
            println("Warning: Unknown priority '$value'. Using LOW as default.")
            Priority.LOW
        }
    }
}

private fun parsePackage(line: String, lineNumber: Int): Package? {
    val columns = line.split(",").map(String::trim)

    if (columns.size != 4) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Expected 4 columns but found ${columns.size}."
        )
        return null
    }

    val id = columns[0]
    val weightValue = columns[1]
    val destinationHubId = columns[2]
    val priorityValue = columns[3]

    if (id.isBlank() || destinationHubId.isBlank()) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Missing required data (ID or Destination)."
        )
        return null
    }

    val weight = weightValue.toDoubleOrNull()

    if (weight == null) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Invalid weight '$weightValue'."
        )
        return null
    }

    return Package(
        id = id,
        weight = weight,
        destinationHubId = destinationHubId,
        priority = parsePriority(priorityValue)
    )
}

fun readPackages(fileName: String): List<Package> {
    val packages = mutableListOf<Package>()

    readCsvFile(fileName) { line, lineNumber ->
        parsePackage(line, lineNumber)?.let(packages::add)
    }

    println("Successfully parsed packages: ${packages.size}")
    return packages
}

// Vehicles Reader

private fun parseVehicle(line: String, lineNumber: Int): Vehicle? {
    val columns = line.split(",").map(String::trim)

    if (columns.size != 4) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Expected 4 columns but found ${columns.size}."
        )
        return null
    }

    val vehicleId = columns[0]
    val currentHubId = columns[1]
    val maxCapacityValue = columns[2]
    val costPerKmValue = columns[3]

    if (vehicleId.isBlank() || currentHubId.isBlank()) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Missing required data (Vehicle ID or Current Hub ID)."
        )
        return null
    }

    val maxCapacityKg = maxCapacityValue.toDoubleOrNull()

    if (maxCapacityKg == null) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Invalid maximum capacity '$maxCapacityValue'."
        )
        return null
    }

    val costPerKm = costPerKmValue.toDoubleOrNull()

    if (costPerKm == null) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Invalid cost per kilometer '$costPerKmValue'."
        )
        return null
    }

    return Vehicle(
        vehicleId = vehicleId,
        currentHubId = currentHubId,
        maxCapacityKg = maxCapacityKg,
        costPerKm = costPerKm
    )
}

fun readVehicles(fileName: String): List<Vehicle> {
    val vehicles = mutableListOf<Vehicle>()

    readCsvFile(fileName) { line, lineNumber ->
        parseVehicle(line, lineNumber)?.let(vehicles::add)
    }

    println("Successfully parsed vehicles: ${vehicles.size}")
    return vehicles
}

// Routes Reader

private fun parseRoute(line: String, lineNumber: Int): Route? {
    val columns = line.split(",").map(String::trim)

    if (columns.size != 5) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Expected 5 columns but found ${columns.size}."
        )
        return null
    }

    val routeId = columns[0]
    val originHubId = columns[1]
    val destinationHubId = columns[2]
    val distanceValue = columns[3]
    val typicalDelayValue = columns[4]

    if (
        routeId.isBlank() ||
        originHubId.isBlank() ||
        destinationHubId.isBlank()
    ) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Missing required data (Route ID, Origin Hub ID, or Destination Hub ID)."
        )
        return null
    }

    val distanceKm = distanceValue.toDoubleOrNull()

    if (distanceKm == null) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Invalid distance '$distanceValue'."
        )
        return null
    }

    val typicalDelayMin = typicalDelayValue.toIntOrNull()

    if (typicalDelayMin == null) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Invalid typical delay '$typicalDelayValue'."
        )
        return null
    }

    return Route(
        routeId = routeId,
        originHubId = originHubId,
        destinationHubId = destinationHubId,
        distanceKm = distanceKm,
        typicalDelayMin = typicalDelayMin
    )
}

fun readRoutes(fileName: String): List<Route> {
    val routes = mutableListOf<Route>()

    readCsvFile(fileName) { line, lineNumber ->
        parseRoute(line, lineNumber)?.let(routes::add)
    }

    println("Successfully parsed routes: ${routes.size}")
    return routes
}

// Warehouses Reader

private fun parseWarehouse(line: String, lineNumber: Int): Warehouse? {
    val columns = line.split(",").map(String::trim)

    if (columns.size != 3) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Expected 3 columns but found ${columns.size}."
        )
        return null
    }

    val id = columns[0]
    val name = columns[1]
    val regionalZone = columns[2]

    if (id.isBlank() || name.isBlank() || regionalZone.isBlank()) {
        println(
            "Warning: Skipping line $lineNumber. " +
                    "Missing required warehouse data."
        )
        return null
    }

    return Warehouse(
        id = id,
        name = name,
        regionalZone = regionalZone
    )
}

fun readWarehouses(fileName: String): List<Warehouse> {
    val warehouses = mutableListOf<Warehouse>()

    readCsvFile(fileName) { line, lineNumber ->
        parseWarehouse(line, lineNumber)?.let(warehouses::add)
    }

    println("Successfully parsed warehouses: ${warehouses.size}")
    return warehouses
}

// Common CSV Reader

private fun readCsvFile(
    fileName: String,
    processLine: (String, Int) -> Unit
) {
    val file = File(RESOURCE_PATH, fileName)

    if (!file.exists()) {
        println("Warning: File '$fileName' was not found!")
        return
    }

    try {
        file.useLines { lines ->
            lines.drop(1).forEachIndexed { index, line ->
                if (line.isNotBlank()) {
                    processLine(line, index + 2)
                }
            }
        }
    } catch (e: IOException) {
        println("Error reading file '$fileName': ${e.message}")
    }
}