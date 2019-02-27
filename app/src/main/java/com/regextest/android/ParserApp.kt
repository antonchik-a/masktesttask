package com.regextest.android

import android.app.Application
import android.content.Context
import com.regextest.android.data.ParserRepository
import android.content.Context.CLIPBOARD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService



class ParserApp() : Application() {

    override fun onCreate() {
        super.onCreate()
        ParserRepository.context = this
    }

}