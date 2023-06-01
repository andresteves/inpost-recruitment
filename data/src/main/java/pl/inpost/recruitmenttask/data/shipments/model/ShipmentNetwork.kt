package pl.inpost.recruitmenttask.data.shipments.model

import java.util.Date

data class ShipmentNetwork(
    val number: String,
    val shipmentType: String,
    val status: String,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: Date?,
    val storedDate: Date?,
    val pickUpDate: Date?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork
)
