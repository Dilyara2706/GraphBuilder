package com.dilyara.graphbuilder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.dilyara.graphbuilder.R

class CartesianPainter : View {

    private var converter: CoordinatesConverter? = null

    private var axesCoordinates: FloatArray? = null

    private lateinit var graphPaint: Paint

    private lateinit var axesPaint: Paint

    private var function: ((x: Float) -> Float)? = null

    //region Constructors
    constructor(
            context: Context?
    ) : super(context) {
        onInit(null)
    }

    constructor(
            context: Context?,
            attrs: AttributeSet?
    ) : super(context, attrs) {
        onInit(attrs)
    }

    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        onInit(attrs)
    }

    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        onInit(attrs)
    }
    //endregion

    private fun onInit(set: AttributeSet?) {
        graphPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        graphPaint.style = Paint.Style.STROKE

        axesPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        axesPaint.style = Paint.Style.STROKE

        if (set == null) return

        val ta = context.obtainStyledAttributes(set, R.styleable.CartesianPainter)

        graphPaint.color = ta.getColor(R.styleable.CartesianPainter_graphColor, Color.BLACK)
        graphPaint.strokeWidth = ta.getDimensionPixelSize(R.styleable.CartesianPainter_graphThickness, 1).toFloat()

        axesPaint.color = ta.getColor(R.styleable.CartesianPainter_axesColor, Color.LTGRAY)
        axesPaint.strokeWidth = ta.getDimensionPixelSize(R.styleable.CartesianPainter_axesThickness, 1).toFloat()


        ta.recycle()
    }

    fun setBounds(xMin: Float, xMax: Float, yMin: Float, yMax: Float) {
        converter = CoordinatesConverter(xMin, xMax, yMin, yMax, width, height)
        axesCoordinates = floatArrayOf(
                0f,
                converter!!.yToScreen(0f),
                width.toFloat(),
                converter!!.yToScreen(0f),
                converter!!.xToScreen(0f),
                0f,
                converter!!.xToScreen(0f),
                height.toFloat()
        )

    }

    fun drawByFunction(function: (x: Float) -> Float) {
        this.function = function
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        converter?.let { converter ->
            converter.width = width
            converter.height = height

            axesCoordinates?.let {
                it[0] = 0f
                it[1] = converter.yToScreen(0f)
                it[2] = width.toFloat()
                it[3] = converter.yToScreen(0f)
                it[4] = converter.xToScreen(0f)
                it[5] = 0f
                it[6] = converter.xToScreen(0f)
                it[7] = height.toFloat()
            }
        }
        drawAxes(canvas)
        drawGraph(canvas)
    }

    private fun drawAxes(canvas: Canvas?) {
        axesCoordinates?.let {
            canvas?.drawLines(it, axesPaint)
        }
    }

    private fun drawGraph(canvas: Canvas?) {
        function?.let { function ->
            converter?.let {
                for (x in 0 until width) {
                    canvas?.drawLine(
                            x.toFloat(),
                            it.yToScreen(function(it.xToCartesian(x))),
                            (x + 1).toFloat(),
                            it.yToScreen(function(it.xToCartesian(x + 1))),
                            graphPaint
                    )
                }
            }
        }
    }
}