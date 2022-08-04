package com.jeongyookgak.jth.presentation.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object PreferencesUtil {
    private const val name = "jeongYookGak"

    fun setStringArrayPref(context: Context, values: List<String>) {
        val userLocalData = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val editor = userLocalData!!.edit()
        editor.clear()
        editor.apply()

        val gson = GsonBuilder().create()
        val listType = object : TypeToken<MutableList<String>>() {}
        val str = gson.toJson(values, listType.type)
        editor.putString(name, str)
        editor.apply()
    }

    fun getStringArrayPref(context: Context) : List<String>?  {
        val gson = GsonBuilder().create()
        val listType = object : TypeToken<MutableList<String>>() {}

        val sp =context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val strContact = sp.getString(name, "")
        return gson.fromJson(strContact, listType.type)
    }

}