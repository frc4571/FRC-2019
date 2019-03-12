package com.rambots4571.rampage.sensor.pid

import edu.wpi.first.wpilibj.PIDSource
import edu.wpi.first.wpilibj.PIDSourceType
import java.util.function.DoubleSupplier

class SourceSupplier(private val func: DoubleSupplier,
                     private var sourceType: PIDSourceType) : PIDSource {

    constructor(func: DoubleSupplier) : this(func, PIDSourceType.kDisplacement)

    override fun getPIDSourceType(): PIDSourceType = sourceType

    override fun setPIDSourceType(pidSource: PIDSourceType?) {
        sourceType = pidSource!!
    }

    override fun pidGet(): Double = func.asDouble
}