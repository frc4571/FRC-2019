package com.rambots4571.rampage.sensor.pid

import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType
import java.util.function.DoubleSupplier

/**
 * Class used to send values as a source without needing to implement PID
 * source all the time, inspired by team 166.
 */
class SourceSupplier(
    private val sensorValue: DoubleSupplier,
    private var sourceType: PIDSourceType) : PIDSource {

    constructor(sensorValue: DoubleSupplier) : this(
            sensorValue, PIDSourceType.kDisplacement)

    override fun getPIDSourceType(): PIDSourceType = sourceType

    override fun setPIDSourceType(pidSource: PIDSourceType?) {
        sourceType = pidSource!!
    }

    override fun pidGet(): Double = sensorValue.asDouble
}