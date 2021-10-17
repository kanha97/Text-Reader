package com.devkanhaiya.bookreader.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.ui.Const
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by hlink21 on 31/5/16.
 */
@Singleton
class AppPreferences @Inject
constructor(context: Context) {
    companion object {
        const val SHARED_PREF_NAME = "app_preference"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun putString(name: String, value: String) {
        val editor = sharedPreferences.edit()
        editor!!.putString(name, value)
        editor.apply()
    }


    @SuppressLint("CommitPrefEdits")
    fun putBoolean(name: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor!!.putBoolean(name, value)
        editor.apply()
    }

    fun getBoolean(name: String): Boolean {
        return sharedPreferences.getBoolean(name, false)
    }

    fun firstLogin(): Boolean {
        return sharedPreferences.getBoolean(Const.LOG_IN_FIRST, true)
    }

    fun firstLoginStories(): Boolean {
        return sharedPreferences.getBoolean(Const.LOG_IN_FIRST_STORIES, true)
    }

    fun getString(name: String): String {
        return sharedPreferences.getString(name, "") ?: ""
    }


    @SuppressLint("CommitPrefEdits")
    fun putInt(name: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor!!.putInt(name, value)
        editor.apply()
    }

    fun getInt(name: String): Int {
        return sharedPreferences.getInt(name, 0)
    }


    fun clearAll() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    fun putFloat(name: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor!!.putFloat(name, value)
        editor.apply()
    }

    fun getFloat(name: String): Float {
        return sharedPreferences.getFloat(name, 0f)
    }

    fun saveArrayList(list: ArrayList<Transport?>, key: String?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): ArrayList<Transport?>? {
        val gson = Gson()
        val json: String? = sharedPreferences.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<Transport?>?>() {}.getType()
        return gson.fromJson(json, type)
    }
}
