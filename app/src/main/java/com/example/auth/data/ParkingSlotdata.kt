package com.example.auth.data

data class ParkingSlotdata(
    val name: String,
    val total: Int, // Nombre total de places dans le slot
    val reserved: Int // Nombre de places déjà réservées dans le slot
) {
    val available: Int
        get() = total - reserved // Calculer le nombre de places disponibles
}
