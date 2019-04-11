package com.rambots4571.rampage.joystick

import com.rambots4571.rampage.function.SwitchAction
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import java.util.function.Supplier

class Button(joystick: GenericHID, buttonNumber: Int) :
        JoystickButton(joystick, buttonNumber) {
    private val buttonListener = SwitchAction(Supplier(this::get), false)

    init {
        buttonListener.runWhenBackToInitial = false
    }

    /**
     * Use this method when you want to do an action once inside a continuous
     * loop, such as the execute() method inside the Command class.
     */
    fun whenPressedDoOnce(action: Runnable) {
        buttonListener.run(action)
    }
}