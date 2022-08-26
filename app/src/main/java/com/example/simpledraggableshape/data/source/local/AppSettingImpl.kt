package com.example.simpledraggableshape.data.source.local

import com.example.simpledraggableshape.domain.setting.AppSetting
import com.example.simpledraggableshape.util.DateUtil.getTodayDate
import javax.inject.Inject

class AppSettingImpl @Inject constructor(private val sharedPrefHelper: SharedPrefHelper) :
    AppSetting {

    override fun saveLastVisit(lastVisit: String) {
        sharedPrefHelper.write(KEY_LAST_VISIT, lastVisit)
    }

    override fun getLastVisit(): String {
        return sharedPrefHelper.read(KEY_LAST_VISIT, getTodayDate()) ?: getTodayDate()
    }

    companion object {
        const val KEY_LAST_VISIT = "KEY_LAST_VISIT"
    }
}