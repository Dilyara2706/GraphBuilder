package com.dilyara.graphbuilder.view

class CoordinatesConverter(
        var xMin: Float,
        var xMax: Float,
        var yMin: Float,
        var yMax: Float,
        var width: Int,
        var height: Int
) {

    fun xToScreen(x: Float): Float = (width.toFloat() / (xMax - xMin) * (x - xMin))

    fun yToScreen(y: Float): Float = (height.toFloat() / (yMax - yMin) * (yMax - y))

    fun xToCartesian(x: Int): Float = x * (xMax - xMin) / width.toFloat() + xMin

    fun yToCartesian(y: Int): Float = yMax - y * (yMax - yMin) / height.toFloat()

}