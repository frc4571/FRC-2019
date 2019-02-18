package com.rambots4571.rampage.joystick

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton

/**
 * This class is for the logitech gamepad F310
 */
class Gamepad(port: Int) : Joystick(port) {
    val a = JoystickButton(this, 1)
    val b = JoystickButton(this, 2)
    val x = JoystickButton(this, 3)
    val y = JoystickButton(this, 4)
    val leftBumper = JoystickButton(this, 5)
    val rightBumper = JoystickButton(this, 6)
    val backButton = JoystickButton(this, 7)
    val startButton = JoystickButton(this, 8)
    val leftStick = JoystickButton(this, 9)
    val rightStick = JoystickButton(this, 10)

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
}