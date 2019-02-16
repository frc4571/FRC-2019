package org.usfirst.frc.team4571.robot

object Constants {
    object Controllers {
        const val LEFT_STICK = 0
        const val RIGHT_STICK = 1
    }

    object Elevator {
        const val BASE_MOTOR = 1
        const val TOP_MOTOR = 2
        const val LIMIT_SWITCH = 0 // DIO port
        const val kPIDLoopIdx = 0
        const val timeoutMs = 30
        const val kSlotIdx = 0
        const val cruiseVel = 0
        const val acceleration = 0
        private const val TICK_PER_REV = 4096
        private const val SPROCKET_DIAMETER = 1.79 // inches
        const val TICKS_PER_INCH = TICK_PER_REV / SPROCKET_DIAMETER
        object Gains {
            const val kP = 0.0
            const val kI = 0.0
            const val kD = 0.0
            const val kF = 0.0
        }
    }
}