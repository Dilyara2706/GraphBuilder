package com.dilyara.graphbuilder.data

import org.json.JSONObject

data class Bounds(
    val xMin: Double,
    val xMax: Double,
    val yMin: Double,
    val yMax: Double
) {
    fun toJson(): JSONObject = JSONObject()
        .put(keyXMin, xMin)
        .put(keyXMax, xMax)
        .put(keyYMin, yMin)
        .put(keyYMax, yMax)

    companion object {
        const val keyXMin = "x_min"
        const val keyXMax = "x_max"
        const val keyYMin = "y_min"
        const val keyYMax = "y_max"

        fun fromJsonString(json: String) : Bounds = fromJson(JSONObject(json))

        fun fromJson(json: JSONObject) : Bounds =
            Bounds(
                json.getDouble(keyXMin),
                json.getDouble(keyXMax),
                json.getDouble(keyYMin),
                json.getDouble(keyYMax)
            )
    }
}