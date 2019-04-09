package com.rambots4571.rampage.function

import java.util.function.Supplier

/**
 * This class is used to execute an action once, if it is inside a continuous
 * loop, it needs to compare the states that you supply and it will execute
 * the action you provide when the states switch.
 */
class DoOnce<S>(
    private val stateSupplier: Supplier<S>, private val initialState: S) {
    private var prevState: S
    private var currentState: S

    init {
        currentState = initialState
        prevState = initialState
    }

    fun run(action: Runnable) {
        prevState = currentState
        currentState = stateSupplier.get()
        if (currentState != initialState && prevState == initialState)
            action.run()
    }
}