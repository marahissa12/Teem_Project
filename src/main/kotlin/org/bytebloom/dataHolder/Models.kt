package org.bytebloom.dataHolder

data class packageRow(
    val id: String,
    val weight: Double,
    val destinationHubId: String,
    val priority: Priority
)

enum class Priority {
    URGENT,
    STANDARD,
    LOW
}

data class vehicleRow(
    val vehicleId: String,
    val currentHubId: String,
    val maxCapacityKg: Double,
    val costPerKm: Double
)

data class routeRow(
    val routeId: String,
    val originHubId: String,
    val destinationHubId: String,
    val distanceKm: Double,
    val typicalDelayMin: Int
)

data class warehouseRow(
    val id: String,
    val name: String,
    val regionalZone: String
)