package pl.inpost.recruitmenttask.data.shipments

import pl.inpost.recruitmenttask.data.shipments.model.ShipmentNetwork

//On a real scenario I would use Retrofit here
interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}
