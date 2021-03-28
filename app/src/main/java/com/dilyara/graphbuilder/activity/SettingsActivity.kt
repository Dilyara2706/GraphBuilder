package com.dilyara.graphbuilder.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.dilyara.graphbuilder.data.Bounds
import com.dilyara.graphbuilder.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rbtnFunc1.text = Html.fromHtml("x-5sin<sup>2</sup>(x)", Html.FROM_HTML_MODE_COMPACT)
        binding.rbtnFunc2.text =
            Html.fromHtml("x<sup>2</sup>/25 - y<sup>2</sup>/16 - 1", Html.FROM_HTML_MODE_COMPACT)

        binding.btnBuild.setOnClickListener {
            prepareForGraphBuilding()
        }
    }

    private fun prepareForGraphBuilding() {
//        val bounds = Bounds(
//            binding.inpXMin.text.toString().toDouble(),
//            binding.inpXMax.text.toString().toDouble(),
//            binding.inpYMin.text.toString().toDouble(),
//            binding.inpYMax.text.toString().toDouble()
//        )

        val intent = Intent(this, GraphActivity::class.java)
        startActivity(intent)

    }
}