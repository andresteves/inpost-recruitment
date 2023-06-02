package pl.inpost.recruitmenttask.data.shipments

import kotlinx.coroutines.flow.flow
import pl.inpost.recruitmenttask.data.database.dao.ShipmentDAO
import pl.inpost.recruitmenttask.data.database.entity.Shipment
import pl.inpost.recruitmenttask.domain.shipments.ShipmentRepository
import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import javax.inject.Inject

internal class ShipmentGateway @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDAO: ShipmentDAO
) : ShipmentRepository {

    override suspend fun shipments() = flow {
        shipmentDAO.loadAll().collect { listShipments ->
            val domainList = listShipments.map { ship ->
                ship.toDomain()
            }
            emit(domainList)
            refresh()
        }
    }

    override suspend fun archive(shipment: ShipmentDomain) {
        shipmentDAO.update(shipment.toDB())
    }

    override suspend fun refresh() {
        shipmentApi.getShipments().forEach { shipmentNetwork ->
            if (shipmentDAO.isArchived(shipmentNetwork.number) == true) {
                shipmentDAO.insert(shipmentNetwork.toDB(true))
                return@forEach
            }
            shipmentDAO.insert(shipmentNetwork.toDB(false))
        }
    }

}