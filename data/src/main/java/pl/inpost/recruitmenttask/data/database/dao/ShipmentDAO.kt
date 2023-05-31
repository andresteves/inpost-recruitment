package pl.inpost.recruitmenttask.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.database.entity.Shipment

@Dao
interface ShipmentDAO {

    @Query("SELECT * FROM shipment WHERE archived = 0")
    fun loadAll(): Flow<List<Shipment>>

    @Query("SELECT archived FROM shipment WHERE number = :number")
    fun isArchived(number: String): Boolean?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(shipment: Shipment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg shipments: Shipment)

    @Delete
    suspend fun delete(shipment: Shipment)

}
