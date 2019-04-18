package com.rambots4571.rampage.joystick

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.buttons.JoystickButton

/**
 * Class for the Logitech Extreme 3D Pro joystick
 */
class DriveStick(port: Int) : Joystick(port) {
    val button1 = Button(this, 1)
    val button2 = Button(this, 2)
    val button3 = Button(this, 3)
    val button4 = Button(this, 4)
    val button5 = Button(this, 5)
    val button6 = Button(this, 6)
    val button7 = Button(this, 7)
    val button8 = Button(this, 8)
    val button9 = Button(this, 9)
    val button10 = Button(this, 10)
    val button11 = Button(this, 11)
    val button12 = Button(this, 12)

    val xAxis: Double
        get() = getRawAxis(0)

    val yAxis: Double
        // inverted because ot returns negative going forward
        get() = -getRawAxis(1)

    val zAxis: Double
        get() = getRawAxis(2)
}