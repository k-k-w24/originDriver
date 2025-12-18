package com.transportcompany.app

import android.app.Application
import android.util.Log

class TransportApp : Application() {
    companion object {
        private const val TAG = "TransportApp"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate")
        
        // グローバルな例外ハンドラーを設定
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            Log.e(TAG, "Uncaught exception in thread: ${thread.name}", exception)
            Log.e(TAG, "Exception message: ${exception.message}")
            Log.e(TAG, "Stack trace: ${exception.stackTraceToString()}")
            
            // デフォルトのハンドラーも呼び出す
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            defaultHandler?.uncaughtException(thread, exception)
        }
    }
}

