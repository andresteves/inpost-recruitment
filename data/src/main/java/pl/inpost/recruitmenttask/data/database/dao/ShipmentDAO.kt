package pl.inpost.recruitmenttask.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.database.entity.Shipment

@Dao
interface ShipmentDAO {
    @Query("SELECT * FROM shipment WHERE number IN (:userIds)")
    fun loadAllByNumbers(userIds: IntArray): Flow<List<Shipment>>

    @Insert
    suspend fun insert(vararg shipments: Shipment)

    @Delete
    suspend fun delete(shipment: Shipment)
}