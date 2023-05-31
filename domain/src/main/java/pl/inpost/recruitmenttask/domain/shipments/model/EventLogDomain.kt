package pl.inpost.recruitmenttask.domain.shipments.model

import java.time.ZonedDateTime

data class EventLogDomain(
    val name: String,
    val date: ZonedDateTime
)
