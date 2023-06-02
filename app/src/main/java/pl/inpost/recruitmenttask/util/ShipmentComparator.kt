package pl.inpost.recruitmenttask.util

import pl.inpost.recruitmenttask.domain.shipments.model.ShipmentDomain
import java.util.Date

class ShipmentComparator : Comparator<ShipmentDomain> {

    override fun compare(firstS: ShipmentDomain, secondS: ShipmentDomain): Int {

        val statusComp = firstS.status.compareTo(secondS.status)
        if (statusComp != 0) return statusComp

        val pickupDateComp = compareDates(firstS.pickUpDate, secondS.pickUpDate)
        if (pickupDateComp != 0) return pickupDateComp

        val expiryDateComp = compareDates(firstS.expiryDate, secondS.expiryDate)
        if (expiryDateComp != 0) return expiryDateComp

        val storedDateComp = compareDates(firstS.storedDate, secondS.storedDate)
        if (storedDateComp != 0) return storedDateComp

        return firstS.number.compareTo(secondS.number)
    }

    private fun compareDates(date1: Date?, date2: Date?) = when {
        date1 == null && date2 == null -> 0
        date1 != null && date2 != null -> date1.compareTo(date2)
        date1 != null -> 1
        else -> -1
    }

}