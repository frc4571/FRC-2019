package com.rambots4571.rampage.components.joystick

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton

/**
 * Class for the Logitech Extreme 3D Pro joystick
 */
class DriveStick(port: Int) : Joystick(port) {
    val button1 = JoystickButton(this, 1)
    val button2 = JoystickButton(this, 2)
    val button3 = JoystickButton(this, 3)
    val button4 = JoystickButton(this, 4)
    val button5 = JoystickButton(this, 5)
    val button6 = JoystickButton(this, 6)
    val button7 = JoystickButton(this, 7)
    val button8 = JoystickButton(this, 8)
    val button9 = JoystickButton(this, 9)
    val button10 = JoystickButton(this, 10)
    val button11 = JoystickButton(this, 11)
    val button12 = JoystickButton(this, 12)

    val xAxis: Double
        get() = getRawAxis(0)

    val yAxis: Double
        // inverted because ot returns negative going forward
        get() = -getRawAxis(1)

    val zAxis: Double
        get() = getRawAxis(2)
}