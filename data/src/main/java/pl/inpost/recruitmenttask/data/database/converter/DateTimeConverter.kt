package pl.inpost.recruitmenttask.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ProvidedTypeConverter
class DateTimeConverter {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())

    @TypeConverter
    fun toDate(value: String): Date? {
        if (value.isEmpty()) return null
        return formatter.parse(value)
    }

    @TypeConverter
    fun fromDate(date: Date?): String {
        return date?.let {
            formatter.format(date)
        } ?: ""
    }

}

