package com.example.sehatin.utils

import kotlinx.datetime.LocalDateTime
import network.chaintech.kmp_date_time_picker.utils.now
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun formatDateToISO8601(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(date)
}

fun parseDate(dateString: String): Date? {
    val truncatedDateString = dateString.substringBeforeLast(".")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.parse(truncatedDateString)
}
fun convertToHoursAndMinutes(utcTimestamp: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Input is in UTC

    val outputFormat = SimpleDateFormat("HH:mm") // Output format for hours and minutes
    outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Adjust to local time zone

    val date: Date = inputFormat.parse(utcTimestamp) // Parse the input timestamp
    return outputFormat.format(date) // Format to hours and minutes
}

fun getSevenDaysFormattedDates(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val today = ZonedDateTime.now(ZoneOffset.UTC).withHour(10).withMinute(0).withSecond(0).withNano(0)

    return (-3..3).map { offset ->
        today.plusDays(offset.toLong()).format(formatter)
    }
}
fun getTodayUtcDate(): Date {
    val currentDateTime = ZonedDateTime.now(ZoneOffset.UTC) // Get current ZonedDateTime in UTC
    return Date.from(currentDateTime.toInstant()) // Convert ZonedDateTime to Date in UTC
}