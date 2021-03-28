package com.dilyara.graphbuilder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.dilyara.graphbuilder.R
import com.dilyara.graphbuilder.data.Bounds
import com.dilyara.graphbuilder.data.Function

class CartesianPainter : View {

    private var converter: CoordinatesConverter? = null

    private var axesPainter: AxesPainter? = null

    private var graphPainter: GraphPainter? = null

    private lateinit var graphPaint: Paint

    private lateinit var axesPaint: Paint

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
        graphPaint.strokeWidth =
            ta.getDimensionPixelSize(R.styleable.CartesianPainter_graphThickness, 1).toFloat()

        axesPaint.color = ta.getColor(R.styleable.CartesianPainter_axesColor, Color.LTGRAY)
        axesPaint.strokeWidth =
            ta.getDimensionPixelSize(R.styleable.CartesianPainter_axesThickness, 1).toFloat()


        ta.recycle()
    }

    fun setBounds(xMin: Float, xMax: Float, yMin: Float, yMax: Float) {
        converter = CoordinatesConverter(xMin, xMax, yMin, yMax, width, height)
        axesPainter = AxesPainter(
            axesPaint,
            converter!!,
            resources.getDimensionPixelSize(R.dimen.dash_length)
        )
    }

    fun setBounds(bounds: Bounds) {
        setBounds(
            bounds.xMin.toFloat(),
            bounds.xMax.toFloat(),
            bounds.yMin.toFloat(),
            bounds.yMax.toFloat()
        )
    }

    fun setFunction(function: Function){
        graphPainter = GraphPainter(graphPaint, function, converter!!)
    }

    override fun onDraw(canvas: Canvas?) {
        converter?.let { converter ->
            converter.width = width
            converter.height = height
        }
        drawAxes(canvas)
        drawGraph(canvas)
    }

    private fun drawAxes(canvas: Canvas?) {
        canvas?.let { axesPainter?.paint(it) }
    }

    private fun drawGraph(canvas: Canvas?) {
        canvas?.let { graphPainter?.paint(it) }
    }
}