package com.rambots4571.rampage.function

import java.util.function.Supplier

/**
 * This class is used to execute an action once when the state you provide
 * switches, this is meant to be used inside a continuous loop.
 */
class DoOnce<S>(
    private val stateSupplier: Supplier<S>, private var initialState: S) {
    private var prevState: S
    private var currentState: S
    var doWhenBackToInitial = true

    init {
        currentState = initialState
        prevState = initialState
    }

    fun run(action: Runnable) {
        prevState = currentState
        currentState = stateSupplier.get()
        if (currentState != initialState && prevState == initialState)
            if (doWhenBackToInitial) initialState = currentState
            action.run()
    }
}