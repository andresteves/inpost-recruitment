package pl.inpost.recruitmenttask.data.base

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dagger.Reusable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@Reusable
internal class ApiTypeAdapter @Inject constructor() {

    //private val formatter = SimpleDateFormat("dd.MM.yyyy | hh:mm")
//    private val formatter = SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ")
//
//    @FromJson
//    fun toDate(value: String): Date? = formatter.parse(value)
//
//    @ToJson
//    fun fromDate(date: Date?): String? = formatter.format(date) ?: null

    /**
     *  The implementation below requires API level 26 or above and minimum is 21 in the project
     */
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)

    @ToJson
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
}
