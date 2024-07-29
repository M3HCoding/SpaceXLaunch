package com.spacexlaunch.android.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spacexlaunch.android.data.LaunchFailureDetails
import com.spacexlaunch.android.data.LaunchSite
import com.spacexlaunch.android.data.Links
import com.spacexlaunch.android.data.Rocket
import com.spacexlaunch.android.data.Telemetry
import com.spacexlaunch.android.data.Timeline


class LaunchFailureConverter {
    @TypeConverter
    fun fromLaunchFailure(launchFailureDetails: LaunchFailureDetails?): String? {
        return launchFailureDetails?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): LaunchFailureDetails? {
        return json?.let {
            val type = object : TypeToken<LaunchFailureDetails>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class LaunchSiteConverter {
    @TypeConverter
    fun fromLaunchFailure(launchSite: LaunchSite?): String? {
        return launchSite?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): LaunchSite? {
        return json?.let {
            val type = object : TypeToken<LaunchSite>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class LinksConverter {
    @TypeConverter
    fun fromLaunchFailure(links: Links?): String? {
        return links?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): Links? {
        return json?.let {
            val type = object : TypeToken<Links>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class StringListConverter {
    @TypeConverter
    fun fromStringList(stringList: List<String>?): String? {
        return stringList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(json: String?): List<String>? {
        return json?.let {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class RocketConverter {
    @TypeConverter
    fun fromLaunchFailure(rocket: Rocket?): String? {
        return rocket?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): Rocket? {
        return json?.let {
            val type = object : TypeToken<Rocket>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class TelemetryConverter {
    @TypeConverter
    fun fromLaunchFailure(telemetry: Telemetry?): String? {
        return telemetry?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): Telemetry? {
        return json?.let {
            val type = object : TypeToken<Telemetry>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

class TimelineConverter {
    @TypeConverter
    fun fromLaunchFailure(timeline: Timeline?): String? {
        return timeline?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toLaunchFailure(json: String?): Timeline? {
        return json?.let {
            val type = object : TypeToken<Timeline>() {}.type
            Gson().fromJson(it, type)
        }
    }
}

