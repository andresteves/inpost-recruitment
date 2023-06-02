package pl.inpost.recruitmenttask.domain.shipments

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain

interface ShipmentRepository {
    suspend fun shipments(): Flow<Result<List<ShipmentDomain>>>

    suspend fun archive(shipment: ShipmentDomain)

    suspend fun refresh()
}