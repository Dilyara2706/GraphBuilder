package com.dilyara.graphbuilder.view

import android.graphics.Canvas
import android.graphics.Paint

class AxesPainter(
    private val paint: Paint,
    private val converter: CoordinatesConverter,
    private val dashLength: Int
) {
    private val lines: FloatArray = floatArrayOf(
        0f,
        converter.yToScreen(0f),
        converter.xToScreen(converter.xMax),
        converter.yToScreen(0f),
        converter.xToScreen(0f),
        0f,
        converter.xToScreen(0f),
        converter.yToScreen(converter.yMax)
    )

    fun paint(canvas: Canvas) {
        setLines()
        drawAxes(canvas)
        drawSegmentation(canvas)
    }

    private fun setLines() {
        lines[0] = 0f
        lines[1] = converter.yToScreen(0f)
        lines[2] = converter.xToScreen(converter.xMax)
        lines[3] = converter.yToScreen(0f)
        lines[4] = converter.xToScreen(0f)
        lines[5] = 0f
        lines[6] = converter.xToScreen(0f)
        lines[7] = converter.yToScreen(converter.yMin)
    }

    private fun drawAxes(canvas: Canvas) {
        canvas.drawLines(
            lines,
            paint
        )
    }

    private fun drawSegmentation(canvas: Canvas) {
        paint.textSize = 24f

        if ((converter.xToScreen(0.1f) - converter.xToScreen(0f)) > 10)
            drawShortSegmentationForX(canvas)
        if ((converter.yToScreen(0f) - converter.yToScreen(0.1f)) > 10)
            drawShortSegmentationForY(canvas)

        drawLongSegmentationForX(canvas)
        drawLongSegmentationForY(canvas)
    }

    private fun drawShortSegmentationForX(canvas: Canvas) {
        val from = (converter.xMin * 10).toInt()
        val to = (converter.xMax * 10).toInt()
        for (i in from..to) {
            canvas.drawLine(
                converter.xToScreen(i / 10f),
                converter.yToScreen(0f) - dashLength / 2,
                converter.xToScreen(i / 10f),
                converter.yToScreen(0f) + dashLength / 2,
                paint
            )
        }
    }

    private fun drawShortSegmentationForY(canvas: Canvas) {
        val from = (converter.yMin * 10).toInt()
        val to = (converter.yMax * 10).toInt()
        for (i in from..to) {
            canvas.drawLine(
                converter.xToScreen(0f) - dashLength / 2,
                converter.yToScreen(i / 10f),
                converter.xToScreen(0f) + dashLength / 2,
                converter.yToScreen(i / 10f),
                paint
            )
        }
    }

    private fun drawLongSegmentationForX(canvas: Canvas) {
        val from = converter.xMin.toInt()
        val to = converter.xMax.toInt()
        for (i in from..to) {
            canvas.drawLine(
                converter.xToScreen(i.toFloat()),
                converter.yToScreen(0f) - dashLength,
                converter.xToScreen(i.toFloat()),
                converter.yToScreen(0f) + dashLength,
                paint
            )
            if (i != 0)
                canvas.drawText(
                    (i).toString(),
                    converter.xToScreen(i.toFloat()) - 10f,
                    converter.yToScreen(0f) + 35,
                    paint
                )
        }
    }

    private fun drawLongSegmentationForY(canvas: Canvas) {
        val from = converter.yMin.toInt()
        val to = converter.yMax.toInt()
        for (i in from..to) {
            canvas.drawLine(
                converter.xToScreen(0f) - dashLength,
                converter.yToScreen(i.toFloat()),
                converter.xToScreen(0f) + dashLength,
                converter.yToScreen(i.toFloat()),
                paint
            )
            if (i != 0)
                canvas.drawText(
                    (i).toString(),
                    converter.xToScreen(0f) + 20,
                    converter.yToScreen(i.toFloat()),
                    paint
                )
        }
    }
}