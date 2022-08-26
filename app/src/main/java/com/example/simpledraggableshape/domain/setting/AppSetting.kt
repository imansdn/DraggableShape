package com.example.simpledraggableshape.domain.setting

import com.example.simpledraggableshape.util.DateUtil

interface AppSetting {

    fun saveLastVisit(lastVisit: String = DateUtil.getTodayDate())

    fun getLastVisit(): String

}