package com.hyunh.certy

const val TAG = "Certy"

fun logd(tag: String, message: String) = android.util.Log.d(TAG, "$tag::$message")

fun logi(tag: String, message: String) = android.util.Log.i(TAG, "$tag::$message")

fun loge(tag: String, message: String) = android.util.Log.e(TAG, "$tag::$message")
