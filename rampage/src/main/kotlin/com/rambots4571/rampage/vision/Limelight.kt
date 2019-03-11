package com.rambots4571.rampage.vision

import edu.wpi.first.networktables.NetworkTableInstance

object Limelight {
    private val networkTable = NetworkTableInstance.getDefault()
    private val table = networkTable.getTable("limelight")

    /**
     * Whether the limelight has any valid targets.
     */
    @JvmStatic
    val hasAnyTarget
        @JvmName("hasAnyTarget")
        get() = table.getEntry("tv").getDouble(0.0).toInt() > 0

    const val TX_MIN = -27.0
    const val TX_MAX = 27.0

    /**
     * Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees).
     */
    @JvmStatic
    val xOffset: Double
        get() = table.getEntry("tx").getDouble(0.0)

    const val TY_MIN = -20.5
    const val TY_MAX = 20.5

    /**
     * Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees).
     */
    @JvmStatic
    val yOffset: Double
        get() = table.getEntry("ty").getDouble(0.0)

    /**
     * Target Area (0% of image to 100% of image)
     */
    @JvmStatic
    val targetArea: Double
        get() = table.getEntry("ta").getDouble(0.0)

    /**
     * Skew or rotation (-90 degrees to 0 degrees)
     */
    @JvmStatic
    val skew: Double
        get() = table.getEntry("ts").getDouble(0.0)

    /**
     * The pipeline’s latency contribution (ms) Add at least 11ms for
     * image capture latency.
     */
    @JvmStatic
    val latency: Double
        get() = table.getEntry("tl").getDouble(0.0)

    /**
     * Side length of shortest side of the fitted bounding box (pixels).
     */
    @JvmStatic
    val shortSideLength: Double
        get() = table.getEntry("tshort").getDouble(0.0)

    /**
     * Side length of longest side of the fitted bounding box (pixels).
     */
    @JvmStatic
    val longSideLength: Double
        get() = table.getEntry("tlong").getDouble(0.0)

    /**
     * Horizontal side length of the rough bounding box (0 - 320 pixels).
     */
    @JvmStatic
    val horizontalSideLength: Double
        get() = table.getEntry("thor").getDouble(0.0)

    /**
     * Vertical sidelength of the rough bounding box (0 - 320 pixels).
     */
    @JvmStatic
    val verticalSideLength: Double
        get() = table.getEntry("tvert").getDouble(0.0)

    /**
     * Limelight pipeline ranging from 0..9.
     */
    @JvmStatic
    var pipeline: Int
        get() = table.getEntry("getpipe").getDouble(0.0).toInt()
        set(value) {
            if (value in 0..9) table.getEntry("pipeline").setNumber(value)
        }

    /**
     * Sets the mode of the camera led.
     * @param mode the LED mode.
     */
    @JvmStatic
    fun setLedMode(mode: LedMode) {
        table.getEntry("ledMode").setNumber(mode.value)
    }

    /**
     * Sets the mode of the camera.
     */
    @JvmStatic
    fun setCameraMode(mode: CamMode) {
        table.getEntry("camMode").setNumber(mode.value)
    }
}