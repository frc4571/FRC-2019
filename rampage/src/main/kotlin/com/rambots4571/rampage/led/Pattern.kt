package com.rambots4571.rampage.led

object Pattern {
    object Rainbow {
        @JvmField val base = Pair(1005, -0.99)
        @JvmField val party = Pair(1015, -0.97)
        @JvmField val ocean = Pair(1025, -0.95)
        @JvmField val lava = Pair(1035, -0.93)
        @JvmField val forest = Pair(1045, -0.91)
        @JvmField val glitter = Pair(1055, -0.89)
    }

    @JvmField val confetti = Pair(1065, -0.87)

    object Shot {
        @JvmField val red = Pair(1075, -0.85)
        @JvmField val blue = Pair(1085, -0.83)
        @JvmField val white = Pair(1095, -0.81)
        @JvmField val color1 = Pair(1565, 0.13)
        @JvmField val color2 = Pair(1665, 0.33)
    }

    object Sinelon {
        @JvmField val rainbow = Pair(1105, -0.79)
        @JvmField val party = Pair(1115, -0.77)
        @JvmField val ocean = Pair(1125, -0.75)
        @JvmField val lava = Pair(1135, -0.73)
        @JvmField val forest = Pair(1145, -0.71)
        @JvmField val color1AndColor2 = Pair(1775, 0.55)
    }

    object BeatsPerMinute {
        @JvmField val rainbow = Pair(1155, -0.69)
        @JvmField val party = Pair(1165, -0.67)
        @JvmField val ocean = Pair(1175, -0.65)
        @JvmField val lava = Pair(1185, -0.63)
        @JvmField val forest = Pair(1195, -0.61)
        @JvmField val customColors = Pair(1715, 0.43)
    }

    object Fire {
        @JvmField val medium = Pair(1205, -0.59)
        @JvmField val large = Pair(1215, -0.57)
    }

    object Twinkle {
        @JvmField val rainbow = Pair(1225, -0.55)
        @JvmField val party = Pair(1235, -0.53)
        @JvmField val ocean = Pair(1245, -0.51)
        @JvmField val lava = Pair(1255, -0.49)
        @JvmField val forest = Pair(1265, -0.47)
        @JvmField val color1AndColor2 = Pair(1755, 0.51)
    }

    object ColorWaves {
        @JvmField val rainbow = Pair(1275, -0.45)
        @JvmField val party = Pair(1285, -0.43)
        @JvmField val ocean = Pair(1295, -0.41)
        @JvmField val lava = Pair(1305, -0.39)
        @JvmField val forest = Pair(1315, -0.37)
        @JvmField val color1AndColor2 = Pair(1765, 0.53)
    }

    object LarsonScanner {
        @JvmField val red = Pair(1325, -0.35)
        @JvmField val gray = Pair(1335, -0.33)
        @JvmField val color1 = Pair(1495, -0.01)
        @JvmField val color2 = Pair(1595, 0.19)
    }

    object LightChase {
        @JvmField val red = Pair(1345, -0.31)
        @JvmField val blue = Pair(1355, -0.29)
        @JvmField val gray = Pair(1365, -0.27)
        @JvmField val color1 = Pair(1505, 0.01)
        @JvmField val color2 = Pair(1605, 0.21)
    }

    object HeartBeat {
        @JvmField val red = Pair(1375, -0.25)
        @JvmField val blue = Pair(1385, -0.23)
        @JvmField val white = Pair(1395, -0.21)
        @JvmField val gray = Pair(1405, -0.19)
        object Slow {
            @JvmField val color1 = Pair(1515, 0.03)
            @JvmField val color2 = Pair(1615, 0.23)
        }
        object Medium {
            @JvmField val color1 = Pair(1525, 0.05)
            @JvmField val color2 = Pair(1625, 0.25)
        }
        object Fast {
            @JvmField val color1 = Pair(1535, 0.07)
            @JvmField val color2 = Pair(1635, 0.27)
        }
    }

    object Breathe {
        @JvmField val red = Pair(1415, -0.17)
        @JvmField val blue = Pair(1425, -0.15)
        @JvmField val gray = Pair(1435, -0.13)
        object Slow {
            @JvmField val color1 = Pair(1545, 0.09)
            @JvmField val color2 = Pair(1645, 0.29)
        }
        object Fast {
            @JvmField val color1 = Pair(1555, 0.11)
            @JvmField val color2 = Pair(1655, 0.31)
        }
    }

    object Strobe {
        @JvmField val red = Pair(1445, -0.11)
        @JvmField val blue = Pair(1455, -0.09)
        @JvmField val gold = Pair(1465, -0.07)
        @JvmField val white = Pair(1475, -0.05)
        @JvmField val color1 = Pair(1575, 0.15)
        @JvmField val color2 = Pair(1675, 0.35)
    }

    object EndToEndBlack {
        @JvmField val color1 = Pair(1485, -0.03)
        @JvmField val color2 = Pair(1585, 0.17)
    }

    object DualColors {
        object Sparkle {
            @JvmField val color1OnColor2 = Pair(1685, 0.37)
            @JvmField val color2OnColor1 = Pair(1695, 0.39)
        }

        object EndToEndBlend {
            @JvmField val color1ToColor2 = Pair(1725, 0.45)
            @JvmField val color2ToColor1 = Pair(1735, 0.47)
        }

        @JvmField val gradient = Pair(1705, 0.41)
        @JvmField val color1AndColor2 = Pair(1745, 0.49)
    }

    object SolidColor {
        @JvmField val hotPink = Pair(1785, 0.57)
        @JvmField val darkRed = Pair(1795, 0.59)
        @JvmField val red = Pair(1805, 0.61)
        @JvmField val redOrange = Pair(1815, 0.63)
        @JvmField val orange = Pair(1825, 0.65)
        @JvmField val gold = Pair(1835, 0.67)
        @JvmField val yellow = Pair(1845, 0.69)
        @JvmField val lawnGreen = Pair(1855, 0.71)
        @JvmField val lime = Pair(1865, 0.73)
        @JvmField val darkGreen = Pair(1875, 0.75)
        @JvmField val green = Pair(1885, 0.77)
        @JvmField val blueGreen = Pair(1895, 0.79)
        @JvmField val aqua = Pair(1905, 0.81)
        @JvmField val skyBlue = Pair(1915, 0.83)
        @JvmField val darkBlue = Pair(1925, 0.85)
        @JvmField val blue = Pair(1935, 0.87)
        @JvmField val blueViolet = Pair(1945, 0.89)
        @JvmField val violet = Pair(1955, 0.91)
        @JvmField val white = Pair(1965, 0.93)
        @JvmField val gray = Pair(1975, 0.95)
        @JvmField val darkGray = Pair(1985, 0.97)
        @JvmField val black = Pair(1995, 0.99)
    }
}