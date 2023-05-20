package com.common.template.mvvm.model.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.common.template.mvvm.model.room.dao.TemplateEntityItemDao
import com.common.template.mvvm.model.room.table.TemplateEntityItem


@Database(entities = [TemplateEntityItem::class], version = 1, exportSchema = false)
abstract class TemplateEntityItemDataBase : RoomDatabase() {
    abstract fun templateEntityItemDao(): TemplateEntityItemDao
}