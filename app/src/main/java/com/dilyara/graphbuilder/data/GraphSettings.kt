package com.dilyara.graphbuilder.data

import org.json.JSONException
import org.json.JSONObject

data class GraphSettings(
    val bounds: Bounds,
    val axesColor: Int?,
    val graphColor: Int?,
    val graphThickness: Int?,
    val function: Function
) {
    fun toJson(): JSONObject = JSONObject()
        .put(keyBounds, bounds.toJson())
        .putOpt(keyAxesColor, axesColor)
        .putOpt(keyGraphColor, graphColor)
        .putOpt(keyGraphThickness, graphThickness)
        .put(keyFunction, function.defaultToJson())

    override fun toString(): String {
        return toJson().toString()
    }

    companion object {
        const val keyBounds = "bounds"
        const val keyAxesColor = "axes_color"
        const val keyGraphColor = "graph_color"
        const val keyGraphThickness = "graph_thickness"
        const val keyFunction = "function"

        fun fromJsonString(json: String): GraphSettings {
            val json = JSONObject(json)
            val axesColor = try {
                json.getInt(keyAxesColor)
            } catch (ex: JSONException) {
                null
            }
            val graphColor = try {
                json.getInt(keyGraphColor)
            } catch (ex: JSONException) {
                null
            }
            val graphThickness = try {
                json.getInt(keyGraphThickness)
            } catch (ex: JSONException) {
                null
            }
            return GraphSettings(
                Bounds.fromJson(json.getJSONObject(keyBounds)),
                axesColor,
                graphColor,
                graphThickness,
                Function.fromJson(json.getJSONObject(keyFunction))
            )
        }
    }
}