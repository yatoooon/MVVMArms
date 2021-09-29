package com.common.res.entity
data class ListEntity<T>(
        val curPage: Int,
        val datas: MutableList<T>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)