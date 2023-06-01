package pl.inpost.recruitmenttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.database.converter.DateTimeConverter
import pl.inpost.recruitmenttask.data.database.converter.ListConverter
import pl.inpost.recruitmenttask.data.database.dao.ShipmentDAO
import pl.inpost.recruitmenttask.data.database.entity.Shipment

@Database(entities = [Shipment::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class, ListConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDAO
}