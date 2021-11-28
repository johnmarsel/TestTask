package com.johnmarsel.testtask

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

private const val PREF_LAST_SEARCH_QUERY = "lastSearchQuery"

object QueryPreferences {

    fun getLastStoredQuery(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_LAST_SEARCH_QUERY, "")!!
    }

    fun setLastStoredQuery(context: Context, query: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit {
                putString(PREF_LAST_SEARCH_QUERY, query)
            }
    }
}