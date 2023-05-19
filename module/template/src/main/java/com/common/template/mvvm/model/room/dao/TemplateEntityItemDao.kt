package com.common.template.mvvm.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.common.template.mvvm.model.room.table.TemplateEntityItem

@Dao
interface TemplateEntityItemDao {
    @Query("SELECT * FROM TemplateEntityItem ORDER BY stargazersCount DESC LIMIT :limit OFFSET :offset")
    fun getTemplateEntityItemList(limit: Int, offset: Int): List<TemplateEntityItem>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemplateEntityItemList(list: List<TemplateEntityItem>)
}