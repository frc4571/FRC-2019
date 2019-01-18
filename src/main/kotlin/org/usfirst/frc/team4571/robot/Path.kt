package org.usfirst.frc.team4571.robot

import java.io.File
import java.io.FileNotFoundException
import java.util.*

data class Point(val position: Double, val velocity: Double, val delta: Double)

fun getPoints(fileName: String, dir: String = "home/lvuser/deploy/",
              skipFirstLine: Boolean = false): ArrayList<Point> {
    val file = File(dir + fileName)
    val list = ArrayList<Point>()
    try {
        val reader = Scanner(file)
        if (skipFirstLine) reader.nextLine()
        while (reader.hasNextLine()) {
            val values = reader.nextLine().split(", ")
            list.add(Point(values[0].toDouble(), values[1].toDouble(),
                           values[2].toDouble()))
        }
    } catch (e: FileNotFoundException) {
        println("can't find file")
        println(e.stackTrace)
    }
    return list
}