package com.rambots4571.rampage.sensor.pid

import edu.wpi.first.wpilibj.SendableBase
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder

class Tuner(kP: Double, kI: Double, kD: Double, kF: Double = 0.0) :
        SendableBase() {
    var kP: Double
        private set
    var kI: Double
        private set
    var kD: Double
        private set
    var kF: Double
        private set

    constructor() : this(0.0, 0.0, 0.0)

    init {
        this.kP = kP
        this.kI = kI
        this.kD = kD
        this.kF = kF
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder?.addDoubleProperty("kP", { kP }, { kP = it })
        builder?.addDoubleProperty("kI", { kI }, { kI = it })
        builder?.addDoubleProperty("kD", { kD }, { kD = it })
        builder?.addDoubleProperty("kF", { kF }, { kF = it })
    }
}