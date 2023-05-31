package pl.inpost.recruitmenttask.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.recruitmenttask.data.shipments.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.shipments.model.EventLogNetwork
import pl.inpost.recruitmenttask.data.shipments.model.OperationsNetwork
import java.time.ZonedDateTime

@Entity
data class Shipment(
    @PrimaryKey val number: String,
    val archived: Boolean = false,
    val shipmentType: String,
    val status: String,
    @Embedded val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    @Embedded(prefix = "receiver_") val receiver: CustomerNetwork?,
    @Embedded(prefix = "sender_")val sender: CustomerNetwork?,
    @Embedded val operations: OperationsNetwork
)