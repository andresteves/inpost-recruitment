package pl.inpost.recruitmenttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.database.dao.ShipmentDAO
import pl.inpost.recruitmenttask.data.database.entity.Shipment

@Database(entities = [Shipment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDAO
}