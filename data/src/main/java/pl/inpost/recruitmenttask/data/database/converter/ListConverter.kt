package pl.inpost.recruitmenttask.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.inpost.recruitmenttask.data.shipments.model.EventLogNetwork
import java.lang.reflect.Type

@ProvidedTypeConverter
internal class ListConverter {

    @TypeConverter
    fun fromListEvents(events: List<EventLogNetwork>) = Gson().toJson(events)

    @TypeConverter
    fun toListEvents(value: String): List<EventLogNetwork> {
        val listType: Type = object : TypeToken<List<EventLogNetwork>>() {}.type
        return Gson().fromJson(value, listType)
    }

}