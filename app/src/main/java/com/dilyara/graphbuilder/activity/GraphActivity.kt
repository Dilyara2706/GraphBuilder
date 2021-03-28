package com.dilyara.graphbuilder.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dilyara.graphbuilder.data.Function
import com.dilyara.graphbuilder.databinding.ActivityGraphBinding

class GraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.painter.setBounds(-10.5f, 10.3f, -7.15f, 7.27f)
        binding.painter.setFunction(Function.FUNCTION_2)
    }
}