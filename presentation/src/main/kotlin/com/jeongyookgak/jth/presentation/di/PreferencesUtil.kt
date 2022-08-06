package com.jeongyookgak.jth.presentation.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object PreferencesUtil {
    private const val NAME = "jeongYookGak"
    private const val SEARCH_WORD = "search_word"
    private const val FAVORITE = "favorite"

    fun setStringArrayPref(context: Context, values: List<String>) {
        val userLocalData = context.getSharedPreferences(NAME, MODE_PRIVATE)
        val editor = userLocalData!!.edit()
        val gson = GsonBuilder().create()
        val listType = object : TypeToken<MutableList<String>>() {}
        val str = gson.toJson(values, listType.type)
        editor.putString(FAVORITE, str)
        editor.apply()
    }

    fun getStringArrayPref(context: Context) : List<String>?  {
        val gson = GsonBuilder().create()
        val listType = object : TypeToken<MutableList<String>>() {}

        val sp =context.getSharedPreferences(NAME, MODE_PRIVATE)
        val strContact = sp.getString(FAVORITE, "")
        return gson.fromJson(strContact, listType.type)
    }

    fun setSearchText(context: Context?, values: String) {
        val userLocalData = context?.getSharedPreferences(NAME, MODE_PRIVATE)
        val editor = userLocalData?.edit()
        editor?.putString(SEARCH_WORD,values)?.apply()
    }

    fun getSearchText(context: Context?) : String?  {
        val userLocalData =context?.getSharedPreferences(NAME, MODE_PRIVATE)
        return userLocalData?.getString(SEARCH_WORD, "")
    }

}