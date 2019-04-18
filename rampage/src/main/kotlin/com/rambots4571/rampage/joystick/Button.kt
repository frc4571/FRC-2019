package com.rambots4571.rampage.joystick

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import edu.wpi.first.wpilibj.command.InstantCommand

class Button(joystick: GenericHID, buttonNumber: Int) :
        JoystickButton(joystick, buttonNumber) {

    fun whenPressed(action: Runnable) {
        whenPressed(InstantCommand(action))
    }
}