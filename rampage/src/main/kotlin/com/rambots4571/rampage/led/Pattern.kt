package com.rambots4571.rampage.led

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
        val color1 = Pair(1565, 0.13)
        val color2 = Pair(1665, 0.33)
    }

    object Sinelon {
        val rainbow = Pair(1105, -0.79)
        val party = Pair(1115, -0.77)
        val ocean = Pair(1125, -0.75)
        val lava = Pair(1135, -0.73)
        val forest = Pair(1145, -0.71)
        val color1AndColor2 = Pair(1775, 0.55)
    }

    object BeatsPerMinute {
        val rainbow = Pair(1155, -0.69)
        val party = Pair(1165, -0.67)
        val ocean = Pair(1175, -0.65)
        val lava = Pair(1185, -0.63)
        val forest = Pair(1195, -0.61)
        val customColors = Pair(1715, 0.43)
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
        val color1AndColor2 = Pair(1755, 0.51)
    }

    object ColorWaves {
        val rainbow = Pair(1275, -0.45)
        val party = Pair(1285, -0.43)
        val ocean = Pair(1295, -0.41)
        val lava = Pair(1305, -0.39)
        val forest = Pair(1315, -0.37)
        val color1AndColor2 = Pair(1765, 0.53)
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
        val color1 = Pair(1575, 0.15)
        val color2 = Pair(1675, 0.35)
    }

    object EndToEndBlack {
        val color1 = Pair(1485, -0.03)
        val color2 = Pair(1585, 0.17)
    }

    object DualColors {
        object Sparkle {
            val color1OnColor2 = Pair(1685, 0.37)
            val color2OnColor1 = Pair(1695, 0.39)
        }

        object EndToEndBlend {
            val color1ToColor2 = Pair(1725, 0.45)
            val color2ToColor1 = Pair(1735, 0.47)
        }

        val gradient = Pair(1705, 0.41)
        val color1AndColor2 = Pair(1745, 0.49)
    }

    object SolidColor {
        val hotPink = Pair(1785, 0.57)
        val darkRed = Pair(1795, 0.59)
        val red = Pair(1805, 0.61)
        val redOrange = Pair(1815, 0.63)
        val orange = Pair(1825, 0.65)
        val gold = Pair(1835, 0.67)
        val yellow = Pair(1845, 0.69)
        val lawnGreen = Pair(1855, 0.71)
        val lime = Pair(1865, 0.73)
        val darkGreen = Pair(1875, 0.75)
        val green = Pair(1885, 0.77)
        val blueGreen = Pair(1895, 0.79)
        val aqua = Pair(1905, 0.81)
        val skyBlue = Pair(1915, 0.83)
        val darkBlue = Pair(1925, 0.85)
        val blue = Pair(1935, 0.87)
        val blueViolet = Pair(1945, 0.89)
        val violet = Pair(1955, 0.91)
        val white = Pair(1965, 0.93)
        val gray = Pair(1975, 0.95)
        val darkGray = Pair(1985, 0.97)
        val black = Pair(1995, 0.99)
    }
}