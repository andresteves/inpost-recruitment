package pl.inpost.recruitmenttask.data.shipments

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository
import pl.inpost.recruitmenttask.domain.shipments.model.Shipment
import javax.inject.Inject

internal class ShipmentGateway @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ShipmentRepository {

    override suspend fun shipments(): Flow<List<Shipment>> {
        TODO("Not yet implemented")
    }

}