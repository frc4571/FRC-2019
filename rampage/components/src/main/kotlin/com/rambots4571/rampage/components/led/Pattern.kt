package com.rambots4571.rampage.components.led

enum class PatternTable(val pulseWidth: Int, val value: Double) {
//    RAINBOW_PALETTE(1005, -0.99),
//    RAINBOW_PARTY_PALETTE(1015, -0.97),
//    RAINBOW_OCEAN_PALETTE(1025, -0.95),
//    RAINBOW_LAVA_PALETTE(1035, -0.93),
//    RAINBOW_FOREST_PALETTE(1045, -0.91),
//    RAINBOW_GLITTER(1055, -0.89),
//    CONFETTI(1065, -0.87),
//    SHOT_RED(1075, -0.85),
//    SHOT_BLUE(1085, -0.83),
//    SHOT_WHITE(1095, -0.81),
//    SINELON_RAINBOW(1105, -0.79),
//    SINELON_PARTY(1115, -0.77),
//    SINELON_OCEAN(1125, -0.75),
//    SINELON_LAVA(1135, -0.73),
//    SINELON_FOREST(1145, -0.71),
//    BEATS_PER_MIN_RAINBOW(1155, -0.69),
//    BEATS_PER_MIN_PARTY(1165, -0.67),
//    BEATS_PER_MIN_OCEAN(1175, -0.65),
//    BEATS_PER_MIN_LAVA(1185, -0.63),
//    BEATS_PER_MIN_FOREST(1195, -0.61),
//    FIRE_MEDIUM(1205, -0.59),
//    FIRE_LARGE(1215, -0.57),
//    TWINKLES_RAINBOW(1225, -0.55),
//    TWINKLES_PARTY(1235, -0.53),
//    TWINKLES_OCEAN(1245, -0.51),
//    TWINKLES_LAVA(1255, -0.49),
//    TWINKLES_FOREST(1265, -0.47),
//    COLOR_WAVES_RAINBOW(1275, -0.45),
//    COLOR_WAVES_PARTY(1285, -0.43),
//    COLOR_WAVES_OCEAN(1295, -0.41),
//    COLOR_WAVES_LAVA(1305, -0.39),
//    COLOR_WAVES_FOREST(1315, -0.37),
//    LARSON_SCANNER_RED(1325, -0.35),
//    LARSON_SCANNER_GRAY(1335, -0.33),
//    LIGHT_CHASE_RED(1345, -0.31),
//    LIGHT_CHASE_BLUE(1355, -0.29),
//    LIGHT_CHASE_GRAY(1365, -0.27),
//    HEARTBEAT_RED(1375, -0.25),
//    HEARTBEAT_BLUE(1385, -0.23),
//    HEARTBEAT_WHITE(1395, -0.21),
//    HEARTBEAT_GRAY(1405, -0.19),
//    BREATH_RED(1415, -0.17),
//    BREATH_BLUE(1425, -0.15),
//    BREATH_GRAY(1435, -0.13),
//    STROBE_RED(1445, -0.11),
//    STROBE_BLUE(1455, -0.09),
//    STROBE_GOLD(1465, -0.07),
//    STROBE_WHITE(1475, -0.05),
    COLOR_1_END_TO_END_BLACK(1485, -0.03),
//    COLOR_1_LARSON_SCANNER(1495, -0.01),
//    COLOR_1_LIGHT_CHASE(1505, 0.01),
//    COLOR_1_HEARTBEAT_SLOW(1515, 0.03),
//    COLOR_1_HEARTBEAT_MEDIUM(1525, 0.05),
//    COLOR_1_HEARTBEAT_FAST(1535, 0.07),
//    COLOR_1_BREATH_SLOW(1545, 0.09),
//    COLOR_1_BREATH_FAST(1555, 0.11),
    COLOR_1_SHOT(1565, 0.13),
    COLOR_1_STROBE(1575, 0.15),
    COLOR_2_END_TO_END_BLACK(1585, 0.17),
//    COLOR_2_LARSON_SCANNER(1595, 0.19),
//    COLOR_2_LIGHT_CHASE(1605, 0.21),
//    COLOR_2_HEARTBEAT_SLOW(1615, 0.23),
//    COLOR_2_HEARTBEAT_MEDIUM(1625, 0.25),
//    COLOR_2_HEARTBEAT_FAST(1635, 0.27),
//    COLOR_2_BREATH_SLOW(1645, 0.29),
//    COLOR_2_BREATH_FAST(1655, 0.31),
    COLOR_2_SHOT(1665, 0.33),
    COLOR_2_STROBE(1675, 0.35),
    SPARKLE_COLOR_1_ON_COLOR_2(1685, 0.37),
    SPARKLE_COLOR_2_ON_COLOR_1(1695, 0.39),
    COLOR_GRADIENT_COLOR_1_AND_COLOR_2(1705, 0.41),
    BEATS_PER_MINUTE_COLOR_1_AND_COLOR_2(1715, 0.43)
}

object Pattern {
    object Rainbow {
        val default = Pair(1005, -0.99)
        val party = Pair(1015, -0.97)
        val ocean = Pair(1025, -0.95)
        val lava = Pair(1035, -0.93)
        val forest = Pair(1045, -0.91)
        val glitter = Pair(1055, -0.89)
    }

    val confetti = Pair(1065, -0.87)

    object Shot {
        val red = Pair(1075, -0.85)
        val blue = Pair(1085, -0.83)
        val white = Pair(1095, -0.81)
    }

    object Sinelon {
        val rainbow = Pair(1105, -0.79)
        val party = Pair(1115, -0.77)
        val ocean = Pair(1125, -0.75)
        val lava = Pair(1135, -0.73)
        val forest = Pair(1145, -0.71)
    }

    object BeatsPerMinute {
        val rainbow = Pair(1155, -0.69)
        val party = Pair(1165, -0.67)
        val ocean = Pair(1175, -0.65)
        val lava = Pair(1185, -0.63)
        val forest = Pair(1195, -0.61)
    }

    object Fire {
        val medium = Pair(1205, -0.59)
        val large = Pair(1215, -0.57)
    }

    object Twinkle {
        val rainbow = Pair(1225, -0.55)
        val party = Pair(1235, -0.53)
        val ocean = Pair(1245, -0.51)
        val lava = Pair(1255, -0.49)
        val forest = Pair(1265, -0.47)
    }

    object ColorWaves {
        val rainbow = Pair(1275, -0.45)
        val party = Pair(1285, -0.43)
        val ocean = Pair(1295, -0.41)
        val lava = Pair(1305, -0.39)
        val forest = Pair(1315, -0.37)
    }

    object LarsonScanner {
        val red = Pair(1325, -0.35)
        val gray = Pair(1335, -0.33)
        val color1 = Pair(1495, -0.01)
        val color2 = Pair(1595, 0.19)
    }

    object LightChase {
        val red = Pair(1345, -0.31)
        val blue = Pair(1355, -0.29)
        val gray = Pair(1365, -0.27)
        val color1 = Pair(1505, 0.01)
        val color2 = Pair(1605, 0.21)
    }

    object HeartBeat {
        val red = Pair(1375, -0.25)
        val blue = Pair(1385, -0.23)
        val white = Pair(1395, -0.21)
        val gray = Pair(1405, -0.19)
        object Slow {
            val color1 = Pair(1515, 0.03)
            val color2 = Pair(1615, 0.23)
        }
        object Medium {
            val color1 = Pair(1525, 0.05)
            val color2 = Pair(1625, 0.25)
        }
        object Fast {
            val color1 = Pair(1535, 0.07)
            val color2 = Pair(1635, 0.27)
        }
    }

    object Breathe {
        val red = Pair(1415, -0.17)
        val blue = Pair(1425, -0.15)
        val gray = Pair(1435, -0.13)
        object Slow {
            val color1 = Pair(1545, 0.09)
            val color2 = Pair(1645, 0.29)
        }
        object Fast {
            val color1 = Pair(1555, 0.11)
            val color2 = Pair(1655, 0.31)
        }
    }

    object Strobe {
        val red = Pair(1445, -0.11)
        val blue = Pair(1455, -0.09)
        val gold = Pair(1465, -0.07)
        val white = Pair(1475, -0.05)
    }


}