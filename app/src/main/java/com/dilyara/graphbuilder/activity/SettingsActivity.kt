package com.dilyara.graphbuilder.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.RadioButton
import android.widget.TextView
import com.dilyara.graphbuilder.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var txtFunc1: RadioButton
    private lateinit var txtFunc2: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initViews()

        txtFunc1.text = Html.fromHtml("x-5sin<sup>2</sup>(x)", Html.FROM_HTML_MODE_COMPACT)
        txtFunc2.text = Html.fromHtml("x<sup>2</sup>/25 - y<sup>2</sup>/16 - 1", Html.FROM_HTML_MODE_COMPACT)
    }

    private fun initViews(){
        txtFunc1 = findViewById(R.id.rbtn_func_1)
        txtFunc2 = findViewById(R.id.rbtn_func_2)
    }
}