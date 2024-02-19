package com.bsel.remitngo.data.api

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, data: String) {
        sharedPreferences.edit().putString(key, data).apply()
    }

    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveSessionTimeOut(key: String, data: Long) {
        sharedPreferences.edit().putLong(key, data).apply()
    }

    fun loadSessionTimeOut(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

}