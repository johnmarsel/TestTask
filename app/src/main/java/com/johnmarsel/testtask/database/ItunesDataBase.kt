package com.johnmarsel.testtask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.johnmarsel.testtask.model.ItunesItem

@Database(entities = [ItunesItem::class], version = 1)
@TypeConverters(ItunesTypeConverter::class)
abstract class ItunesDataBase : RoomDatabase() {
    abstract fun itunesDao(): ItunesDao
}