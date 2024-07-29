package com.spacexlaunch.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spacexlaunch.android.data.BookMarkModel
import com.spacexlaunch.android.data.XModel

@Database(entities = [XModel::class, BookMarkModel::class], version = 1, exportSchema = false)
@TypeConverters(
    LaunchFailureConverter::class,
    LinksConverter::class,
    LaunchSiteConverter::class,
    StringListConverter::class,
    RocketConverter::class,
    TelemetryConverter::class,
    TimelineConverter::class

)
abstract class MyDatabase : RoomDatabase() {
    abstract fun xDao(): SpaceXDao
}