package com.example.simpledraggableshape.data.source.local

import com.example.simpledraggableshape.domain.setting.AppSetting
import com.example.simpledraggableshape.util.DateUtil
import javax.inject.Inject

class AppSettingTestImpl @Inject constructor() : AppSetting {
    companion object {
        var lastVisit = DateUtil.getTodayDate()
    }

    override fun saveLastVisit(lastVisit: String) {
        println("saveLastVisit => $lastVisit")
        AppSettingTestImpl.lastVisit = lastVisit
    }

    override fun getLastVisit(): String {
        return lastVisit
    }


}