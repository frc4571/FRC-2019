package com.rambots4571.rampage.joystick

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton

class Button(joystick: GenericHID, buttonNumber: Int) :
        JoystickButton(joystick, buttonNumber) {
    private var prevState: Boolean = false
    private var currentState: Boolean = false

    /**
     * Use this method when you want to do an action once inside a continuous
     * loop, such as the execute() method inside the Command class.
     */
    fun doOnce(action: Runnable) {
        prevState = currentState
        currentState = get()
        if (currentState && !prevState) action.run()
    }
}