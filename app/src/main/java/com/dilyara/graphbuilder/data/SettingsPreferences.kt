package com.dilyara.graphbuilder.data

import android.content.Context

class SettingsPreferences(context: Context) {
    private val context: Context = context.applicationContext

    fun saveSettings(settings: GraphSettings){
        val prefs = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(keyName, settings.toString())
        editor.apply()
    }

    fun getSettings() : String? {
        val prefs = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
        return prefs.getString(keyName, null)
    }

    companion object{
        const val storageName = "storage_settings"
        const val keyName = "settings"
    }
}