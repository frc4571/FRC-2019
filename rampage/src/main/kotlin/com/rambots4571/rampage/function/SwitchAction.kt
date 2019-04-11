package com.rambots4571.rampage.function

import java.util.function.Supplier

/**
 * This class is used to execute an action when the state you provide
 * switches states, this is meant to be used inside a continuous loop.
 */
class SwitchAction<S>(
    private val stateSupplier: Supplier<S>, private var initialState: S) {
    private var prevState: S
    private var currentState: S

    /**
     * Set this to true if you want to run the action every time when the
     * state switches or false when you want to run when it is different from
     * the initial state provided, won't run when it returns to initial state,
     * used when you want do an action once when button is pressed without
     * having to execute more than once.
     */
    @JvmField
    var runWhenBackToInitial = true

    init {
        currentState = initialState
        prevState = initialState
    }

    /**
     * This action is run when there is a change in the state provided.
     * @param action Action to execute.
     */
    fun run(action: Runnable) {
        prevState = currentState
        currentState = stateSupplier.get()
        if (currentState != initialState && prevState == initialState) {
            if (runWhenBackToInitial) initialState = currentState
            action.run()
        }
    }
}