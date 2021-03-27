package com.dilyara.graphbuilder.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dilyara.graphbuilder.R
import com.dilyara.graphbuilder.view.CartesianPainter

class GraphActivity : AppCompatActivity() {

    private lateinit var painter: CartesianPainter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        initViews()

        painter.setBounds(-2f, 3f, -1f, 1f)
        painter.drawByFunction { x ->
            x * x
        }

    }

    private fun initViews(){
        painter = findViewById(R.id.painter)
    }
}