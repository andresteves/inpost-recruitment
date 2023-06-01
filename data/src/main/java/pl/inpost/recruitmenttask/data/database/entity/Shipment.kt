package pl.inpost.recruitmenttask.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.recruitmenttask.data.shipments.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.shipments.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.shipments.model.OperationsNetwork
import java.util.Date

@Entity
data class Shipment(
    @PrimaryKey val number: String,
    val archived: Boolean = false,
    val shipmentType: String,
    val status: String,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: Date?,
    val storedDate: Date?,
    val pickUpDate: Date?,
    @Embedded(prefix = "receiver_") val receiver: CustomerNetwork?,
    @Embedded(prefix = "sender_")val sender: CustomerNetwork?,
    @Embedded val operations: OperationsNetwork
)