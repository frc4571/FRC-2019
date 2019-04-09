package com.rambots4571.rampage.command

import java.util.function.Supplier

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