package com.dilyara.graphbuilder.data

import org.json.JSONObject
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Function(
    val returnCount: Int,
    private val id: Int = 0,
    private val function: (x: Double) -> DoubleArray,
) {
    fun execute(x: Double): DoubleArray = function.invoke(x)

    fun defaultToJson(): JSONObject = JSONObject()
        .put(keyDefaultFunctionId, id)

    companion object Default {
        const val keyDefaultFunctionId = "func_id"
        const val idDefaultFunction1 = 1
        const val idDefaultFunction2 = 2
        const val idDefaultFunction3 = 3

        fun fromJson(jsonObject: JSONObject): Function =
            when (jsonObject.getInt(keyDefaultFunctionId)) {
                idDefaultFunction1 -> FUNCTION_1
                idDefaultFunction2 -> FUNCTION_2
                idDefaultFunction3 -> FUNCTION_3
                else -> throw Exception("Not valid default function id")
            }

        val FUNCTION_1: Function = Function(1, idDefaultFunction1) { x ->
            doubleArrayOf(x - 5 * sin(x).pow(2))
        }

        val FUNCTION_2: Function = Function(2, idDefaultFunction2) { x ->
            val a = 4 * sqrt(1 + x.pow(2) / 25)
            doubleArrayOf(a, -a)
        }

        // Уравнение окружности с центров в (0, 0) радиусом 1
        // Для тестирования
        val FUNCTION_3: Function = Function(2, idDefaultFunction3) { x ->
            val a = sqrt(1 - x.pow(2))
            doubleArrayOf(a, -a)
        }
    }
}