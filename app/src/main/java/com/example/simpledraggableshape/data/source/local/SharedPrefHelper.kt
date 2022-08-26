package com.example.simpledraggableshape.data.source.local

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import javax.inject.Inject


class SharedPrefHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun setOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unRegisterSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun read(key: String?, defValue: String?): String? {
        return sharedPreferences.getString(key, defValue)
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun read(key: String?, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun read(key: String?, defValue: Float): Float {
        return sharedPreferences.getFloat(key, defValue)
    }

    fun read(key: String?, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    fun write(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun write(key: String?, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun write(key: String?, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun write(key: String?, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun write(key: String?, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

}