package pl.inpost.recruitmenttask.data.mock

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dagger.Reusable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@Reusable
internal class ApiTypeAdapter @Inject constructor() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())

    @FromJson
    fun toDate(value: String): Date? {
        if (value.isEmpty()) return null
        return formatter.parse(value)
    }

    @ToJson
    fun fromDate(date: Date?): String {
        return date?.let {
            formatter.format(date)
        } ?: ""
    }

    /**
     *  The implementation below requires API level 26 or above and minimum is 21 in the project
     */
//    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//
//    @FromJson
//    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)
//
//    @ToJson
//    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
}
