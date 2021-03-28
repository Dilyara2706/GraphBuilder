package com.dilyara.graphbuilder.view

import android.graphics.Canvas
import android.graphics.Paint
import com.dilyara.graphbuilder.data.Function

class GraphPainter(
    val paint: Paint,
    val function: Function,
    val converter: CoordinatesConverter
) {

    fun paint(canvas: Canvas) {
        for (x in 0 until converter.xToScreen(converter.xMax).toInt()) {
            for (i in 0 until function.returnCount){
                canvas.drawLine(
                    x.toFloat(),
                    converter.yToScreen(
                        function.execute(converter.xToCartesian(x).toDouble())[i].toFloat()
                    ),
                    (x + 1).toFloat(),
                    converter.yToScreen(
                        function.execute(converter.xToCartesian(x + 1).toDouble())[i]
                            .toFloat()
                    ),
                    paint
                )
            }
        }
    }
}