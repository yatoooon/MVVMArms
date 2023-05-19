package com.common.template.mvvm.model.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TemplateEntityItem")
data class TemplateEntityItem(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo("stargazersCount") val stargazersCount: Int = 0,
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("description") val description: String = "",
)