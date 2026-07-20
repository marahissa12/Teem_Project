package org.bytebloom.app

import org.bytebloom.logic.printTopPackages
import org.bytebloom.readers.readPackages
import org.bytebloom.logic.selectionSortPackagesByPriorityAndWeight

fun main() {
    val packages = readPackages("packages.csv").toMutableList()

    selectionSortPackagesByPriorityAndWeight(packages)

    printTopPackages(packages, 3)
}