package com.dilyara.graphbuilder.data

import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Function(
    val returnCount: Int,
    private val function: (x: Double) -> DoubleArray
) {
    fun execute(x: Double): DoubleArray = function.invoke(x)

    companion object Default {
        val FUNCTION_1: Function = Function(1) { x ->
            return@Function doubleArrayOf(x - 5 * sin(x).pow(2))
        }

        val FUNCTION_2: Function = Function(2) { x ->
            val a = 4 * sqrt(1 + x.pow(2) / 25)
            return@Function doubleArrayOf(a, -a)
        }

        // Уравнение окружности с центров в (0, 0) радиусом 1
        // Для тестирования
        val FUNCTION_3: Function = Function(2) { x ->
            val a = sqrt(1 - x.pow(2))
            return@Function doubleArrayOf(a, -a)
        }
    }
}