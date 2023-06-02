package pl.inpost.recruitmenttask.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val PATTERN = "yyyy-MM-dd'T'hh:mm:ss'Z'"

@ProvidedTypeConverter
class DateTimeConverter {

    @TypeConverter
    fun toDate(value: String): Date? {
        if (value.isEmpty()) return null
        val formatter = SimpleDateFormat(PATTERN, Locale.getDefault())
        return formatter.parse(value)
    }

    @TypeConverter
    fun fromDate(date: Date?): String {
        return date?.let {
            val formatter = SimpleDateFormat(PATTERN, Locale.getDefault())
            formatter.format(date)
        } ?: ""
    }

}

