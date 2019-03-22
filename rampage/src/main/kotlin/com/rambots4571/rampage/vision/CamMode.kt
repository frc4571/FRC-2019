package com.rambots4571.rampage.vision

enum class CamMode(val value: Int) {
    /**
     * Used for vision processing.
     */
    VisionCamera(0),
    /**
     *  Disables vision processing and used as a driver camera.
     */
    DriverCamera(1)
}