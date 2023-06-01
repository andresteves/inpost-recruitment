package pl.inpost.recruitmenttask.domain.shipments.model

import java.util.Date

data class EventLogDomain(
    val name: String,
    val date: Date?
)
