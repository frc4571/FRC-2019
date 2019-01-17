package org.usfirst.frc.team4571.robot

object Constants {
    object Controllers {
        const val LEFT_STICK = 0
        const val RIGHT_STICK = 1
    }

    object DRIVE {
        const val LEFT_FOLLOWER = 2
        const val LEFT_MASTER = 1
        const val RIGHT_MASTER = 3
        const val RIGHT_FOLLOWER = 4
    }

    object Transmission {
        private const val encoderTeeth = 36
        private const val magnetTeeth = 12
        private const val compoundGearTeeth = 24
        private const val outputTeeth = 60
        private const val wheelDiameter = 6.0
        private const val ticksPerRotation = 4096
        const val TICKS_PER_INCH = ((encoderTeeth / magnetTeeth) *
                (outputTeeth / compoundGearTeeth) * ticksPerRotation) /
                (wheelDiameter * Math.PI)
    }
}