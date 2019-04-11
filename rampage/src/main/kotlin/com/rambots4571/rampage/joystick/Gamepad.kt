package com.rambots4571.rampage.joystick

import edu.wpi.first.wpilibj.Joystick

/**
 * This class is for the logitech gamepad F310
 */
class Gamepad(port: Int) : Joystick(port) {
    val a = Button(this, 1)
    val b = Button(this, 2)
    val x = Button(this, 3)
    val y = Button(this, 4)
    val leftBumper = Button(this, 5)
    val rightBumper = Button(this, 6)
    val backButton = Button(this, 7)
    val startButton = Button(this, 8)
    val leftStick = Button(this, 9)
    val rightStick = Button(this, 10)

    val leftXAxis: Double
        get() = getRawAxis(0)

    val leftYAxis: Double
        get() = -getRawAxis(1)

    val leftTrigger: Double
        get() = getRawAxis(2)

    val rightXAxis: Double
        get() = getRawAxis(4)

    val rightYAxis: Double
        get() = -getRawAxis(5)

    val rightTrigger: Double
        get() = getRawAxis(3)

    fun rumble() {
        setRumble(RumbleType.kLeftRumble, 0.5)
        setRumble(RumbleType.kRightRumble, 0.5)
    }
}