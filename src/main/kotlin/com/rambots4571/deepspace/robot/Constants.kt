package com.rambots4571.deepspace.robot

object Constants {
    const val period = 0.02
    const val periodMs = 20
    const val timeoutMs = 30
    const val longTimeoutMs = 1000

    enum class Units {
        Feet, Inches, Ticks
    }

    object Controllers {
        const val LEFT_STICK = 0
        const val RIGHT_STICK = 1
        const val GAMEPAD = 2
    }

    object Drive {
        const val LEFT_MASTER = 4
        const val LEFT_FOLLOWER1 = 5
        const val LEFT_FOLLOWER2 = 6
        const val RIGHT_MASTER = 1
        const val RIGHT_FOLLOWER1 = 2
        const val RIGHT_FOLLOWER2 = 3
        const val highGearPIDSlotIdx = 0

        const val deadband = 0.06

        object Turn {
            const val kP = 0.0
            const val kI = 0.0
            const val kD = 0.0
        }

        object MP {
            const val trajectoryPointPeriod = 10
            private const val maxVel = 32169 // u / 100 ms
            object Gains {
                const val kP = 0.0
                const val kI = 0.0
                const val kD = 0.0
                const val kF = (100.0 * 1023) / maxVel
            }
        }

        object Transmission {
            private const val encoderTeeth = 36
            private const val magnetTeeth = 12
            private const val compoundGearTeeth = 24
            private const val outputTeeth = 60
            private const val wheelDiameter = 8.0
            private const val ticksPerRotation = 4096
            private const val highToLowGearSpread = 2.16
            const val HIGH_GEAR_TICKS_PER_INCH = ((encoderTeeth / magnetTeeth) *
                    (outputTeeth / compoundGearTeeth) * ticksPerRotation) /
                    (wheelDiameter * Math.PI)
            const val HIGH_GEAR_TICKS_PER_FEET = HIGH_GEAR_TICKS_PER_INCH * 12
            const val LOW_GEAR_TICKS_PER_INCH = HIGH_GEAR_TICKS_PER_INCH *
                    highToLowGearSpread
            const val LOW_GEAR_TICKS_PER_FEET = LOW_GEAR_TICKS_PER_INCH * 12
        }
    }

    object Paths {
        const val dir = "/home/lvuser/deploy/paths/"
        const val leftSuffix = "_left.csv"
        const val rightSuffix = "_right.csv"
    }

    object Elevator {
        const val BASE_MOTOR_MASTER = 8
        const val BASE_MOTOR_FOLLOWER = 7
        const val TOP_MOTOR = 12
        const val LIMIT_SWITCH = 0 // DIO port

        const val kPIDLoopIdx = 0
        const val kSlotIdx = 0
        const val cruiseVel = 1177 // u/100ms
        const val acceleration = 9500 // u/100ms^2
        const val TICKS_PER_INCH = 741.96875

        object Gains {
            const val kP = 0.3
            const val kI = 0.0
            const val kD = 0.1
            const val kF = 0.0
        }

        object Height {
            const val hatchBottom = 3.5
            const val hatchMiddle = 16.875
            const val hatchTop = 31.75
            const val cargoBottom = hatchBottom + 9.5
            const val cargoMiddle = hatchMiddle + 9.5
            const val cargoTop = hatchTop
        }
    }

    object Intake {
        const val LEFT_INTAKE_MOTOR = 9
        const val RIGHT_INTAKE_MOTOR = 10
        const val PULLEY_MOTOR = 11
        const val HATCH_MOTOR = 13
    }

    object Pipelines {
        const val CARGO = 0
    }
}