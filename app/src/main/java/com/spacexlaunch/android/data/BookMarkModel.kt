package com.spacexlaunch.android.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "bookmark_model")
data class BookMarkModel(
    @PrimaryKey(autoGenerate = true)
    val flight_number: Int?=0,
    val details: String?,
    val is_tentative: Boolean?,
    val launch_date_local: String?,
    val launch_date_unix: Int?,
    val launch_date_utc: String?,
    val launch_failure_details: LaunchFailureDetails?,
    val launch_site: LaunchSite?,
    val launch_success: Boolean?,
    val launch_window: Int?=0,
    val launch_year: String?,
    val links: Links?,
    val mission_id: List<String>?=null,
    val mission_name: String?=null,
    val rocket: Rocket?=null,
    val ships: List<String>?=null,
    val static_fire_date_unix: Int?=null,
    val static_fire_date_utc: String?=null,
    val tbd: Boolean?=false,
    val telemetry: Telemetry?=null,
    val tentative_max_precision: String?=null,
    val timeline: Timeline?=null,
    val upcoming: Boolean?=false
) :Parcelable