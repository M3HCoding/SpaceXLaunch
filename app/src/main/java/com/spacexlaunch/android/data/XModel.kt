package com.spacexlaunch.android.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "x_model")
data class XModel(
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

@Parcelize
data class Timeline(
    val webcast_liftoff: Int
):Parcelable

@Parcelize
data class Telemetry(
    val flight_club:String?=null
):Parcelable

@Parcelize
data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
):Parcelable

@Parcelize
data class Rocket(
    val fairings: Fairings,
    val first_stage: FirstStage,
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val second_stage: SecondStage
):Parcelable

@Parcelize
data class Payload(
    val customers: List<String>,
    val manufacturer: String,
    val nationality: String,
    val orbit: String,
    val orbit_params: OrbitParams,
    val payload_id: String,
    val payload_mass_kg: Float,
    val payload_mass_lbs: Float,
    val payload_type: String,
    val reused: Boolean
):Parcelable


@Parcelize
data class OrbitParams(
    val apoapsis_km: Float,
):Parcelable

@Parcelize
data class Links(
    val article_link: String,
    val video_link: String,
    val wikipedia: String,
    val youtube_id: String
):Parcelable

@Parcelize
data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
):Parcelable

@Parcelize
data class LaunchFailureDetails(
    val reason: String,
    val time: Int
):Parcelable

@Parcelize
data class FirstStage(
    val cores: List<Core>
):Parcelable

@Parcelize
data class Fairings(
    val recovered: Boolean,
    val recovery_attempt: Boolean,
    val reused: Boolean,
):Parcelable

@Parcelize
data class Core(
    val core_serial: String,
    val flight: Int,
    val gridfins: Boolean,
    val legs: Boolean,
    val reused: Boolean
):Parcelable