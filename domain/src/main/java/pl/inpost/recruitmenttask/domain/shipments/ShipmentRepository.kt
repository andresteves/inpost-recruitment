package pl.inpost.recruitmenttask.domain.shipments

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.domain.shipments.model.Shipment

interface ShipmentRepository {
    suspend fun shipments(): Flow<List<Shipment>>
}