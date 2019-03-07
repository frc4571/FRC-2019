package org.usfirst.frc.team4571.robot

object Constants {
    const val timeoutMs = 30

    object Controllers {
        const val LEFT_STICK = 0
        const val RIGHT_STICK = 1
        const val GAMEPAD = 2
    }

    enum class Units {
        Ticks, Inches
    }

    object Elevator {
        const val BASE_MOTOR_MASTER = 1
        const val BASE_MOTOR_FOLLOWER = 3
        const val TOP_MOTOR = 2
        const val LIMIT_SWITCH = 0 // DIO port

        const val kPIDLoopIdx = 0
        const val kSlotIdx = 0
        const val cruiseVel = 0
        const val acceleration = 0
        const val TICKS_PER_INCH = 723.16129

        object Gains {
            const val kP = 0.0
            const val kI = 0.0
            const val kD = 0.0
            const val kF = 0.0
        }
    }

    object Intake {
        const val LEFT_INTAKE_MOTOR = 4
        const val RIGHT_INTAKE_MOTOR = 5
        const val PULLEY_MOTOR = 6
    }
}