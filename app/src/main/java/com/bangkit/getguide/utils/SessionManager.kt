package com.bangkit.getguide.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private var editor = prefs.edit()

    val isLogin = "isLogin"

    fun Login(){
        editor.putBoolean(isLogin, true)
        editor.apply()
    }

    fun checkLogin(): Boolean {
        return prefs.getBoolean(isLogin, false)
    }


}