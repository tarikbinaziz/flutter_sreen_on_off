package com.example.switch_on_off

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class DoubleTapService : AccessibilityService() {

    private var lastTapTime: Long = 0
    private val doubleTapThreshold: Long = 300 // 300 ms to detect double-tap

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("DoubleTapService", "Service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        // Check if the event type is a touch interaction (tap event)
        if (event.eventType == AccessibilityEvent.TYPE_GESTURE_DETECTION_START) {
            val currentTime = System.currentTimeMillis()

            // Detect if the time between taps is less than the threshold for double-tap
            if (currentTime - lastTapTime <= doubleTapThreshold) {
                Log.d("DoubleTapService", "Double-tap detected")
                toggleScreenState()
            }
            lastTapTime = currentTime
        }
    }

    private fun toggleScreenState() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

        if (isScreenOn(powerManager)) {
            turnOffScreen(powerManager)
        } else {
            turnOnScreen(powerManager)
        }
    }

    private fun isScreenOn(powerManager: PowerManager): Boolean {
        return powerManager.isInteractive
    }

    private fun turnOffScreen(powerManager: PowerManager) {
        val wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "DoubleTapService::TurnOff")
        wakeLock.acquire(10 * 60 * 1000L)
        Log.d("DoubleTapService", "Screen turned off")
    }

    private fun turnOnScreen(powerManager: PowerManager) {
        val wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.FULL_WAKE_LOCK, "DoubleTapService::TurnOn")
        wakeLock.acquire(10 * 60 * 1000L)
        Log.d("DoubleTapService", "Screen turned on")
    }

    override fun onInterrupt() {
        Log.d("DoubleTapService", "Service interrupted")
    }
}
