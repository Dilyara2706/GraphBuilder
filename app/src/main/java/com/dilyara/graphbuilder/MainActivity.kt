package com.dilyara.graphbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dilyara.graphbuilder.view.CartesianPainter

class MainActivity : AppCompatActivity() {

    private lateinit var painter: CartesianPainter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        painter = findViewById<CartesianPainter>(R.id.painter)
    }

    override fun onStart() {

        painter.setBounds(-2f, 3f, -1f, 1f)
        painter.drawByFunction { x ->
            x * x
        }

        super.onStart()
    }
}