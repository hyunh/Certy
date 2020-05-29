package com.hyunh.certy

import android.app.Application
import android.content.Context
import androidx.annotation.XmlRes
import org.xmlpull.v1.XmlPullParser

class Certy : Application() {

    companion object {
        lateinit var context: Context
            private set

        fun getXmlParser(@XmlRes resId: Int): XmlPullParser {
            if (!Companion::context.isInitialized) {
                throw IllegalStateException()
            }
            return context.resources.getXml(resId)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}