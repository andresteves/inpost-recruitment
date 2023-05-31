package pl.inpost.recruitmenttask.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shipment(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "number") val number: String,
)