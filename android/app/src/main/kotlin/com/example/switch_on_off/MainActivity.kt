package com.example.switch_on_off

import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.switch_on_off/service"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "startService" -> {
                    Log.d("MainActivity", "startService called")
                    try {
                        val intent = Intent(this, DoubleTapService::class.java)
                        startService(intent)
                        Log.d("MainActivity", "Service started")
                        result.success("Service started successfully")
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error starting service", e)
                        result.error("SERVICE_ERROR", "Failed to start service", e.localizedMessage)
                    }
                }
                "stopService" -> {
                    Log.d("MainActivity", "stopService called")
                    try {
                        val intent = Intent(this, DoubleTapService::class.java)
                        stopService(intent)
                        Log.d("MainActivity", "Service stopped")
                        result.success("Service stopped successfully")
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error stopping service", e)
                        result.error("SERVICE_ERROR", "Failed to stop service", e.localizedMessage)
                    }
                }
                else -> {
                    Log.d("MainActivity", "Method not implemented")
                    result.notImplemented()
                }
            }
        }
    }
}
