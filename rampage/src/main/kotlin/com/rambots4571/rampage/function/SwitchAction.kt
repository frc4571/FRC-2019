package com.rambots4571.rampage.function

import edu.wpi.first.wpilibj.buttons.Trigger
import edu.wpi.first.wpilibj.command.InstantCommand
import java.util.function.Supplier

/**
 * This class is used to execute an action when the state you provide
 * switches states.
 */
class SwitchAction<S>(private val stateSupplier: Supplier<S>) : Trigger() {
    private var currentState = stateSupplier.get()
    private var prevState: S? = null

    override fun get(): Boolean {
        prevState = currentState
        currentState = stateSupplier.get()
        return currentState != prevState
    }

    fun whenDiff(action: Runnable) {
        whenActive(InstantCommand(action))
    }
}