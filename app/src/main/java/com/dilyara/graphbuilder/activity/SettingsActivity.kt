package com.dilyara.graphbuilder.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dilyara.graphbuilder.R
import com.dilyara.graphbuilder.data.*
import com.dilyara.graphbuilder.data.Function
import com.dilyara.graphbuilder.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var viewModel: GraphViewModel

    private lateinit var preferences: SettingsPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        preferences = SettingsPreferences(this)

        getSettings()

        binding.rbtnFunc1.text = Html.fromHtml("x-5sin<sup>2</sup>(x)", Html.FROM_HTML_MODE_COMPACT)
        binding.rbtnFunc2.text =
            Html.fromHtml("x<sup>2</sup>/25 - y<sup>2</sup>/16 - 1", Html.FROM_HTML_MODE_COMPACT)

        binding.btnBuild.setOnClickListener {
            prepareForGraphBuilding()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(GraphViewModel::class.java)

        viewModel.observe(this){ settings ->
            if(settings != null){
                restoreSettings(settings)
            }
        }
    }

    private fun getSettings() {
        val settings = preferences.getSettings()
        viewModel.setSettingByString(settings)
    }

    private fun restoreSettings(settings: GraphSettings){
        binding.inpXMin.setText(settings.bounds.xMin.toString())
        binding.inpXMax.setText(settings.bounds.xMax.toString())
        binding.inpYMin.setText(settings.bounds.yMin.toString())
        binding.inpYMax.setText(settings.bounds.yMax.toString())

        when (settings.axesColor) {
            Color.BLACK -> binding.rgrpAxesColor.check(R.id.rbtn_axes_black)
            Color.BLUE -> binding.rgrpAxesColor.check(R.id.rbtn_axes_blue)
            Color.RED -> binding.rgrpAxesColor.check(R.id.rbtn_axes_red)
            else -> binding.rgrpAxesColor.clearCheck()
        }

        when (settings.graphColor) {
            Color.RED -> binding.rgrpGraphColor.check(R.id.rbtn_graph_red)
            Color.GREEN -> binding.rgrpGraphColor.check(R.id.rbtn_graph_green)
            Color.BLUE -> binding.rgrpGraphColor.check(R.id.rbtn_graph_blue)
            else -> binding.rgrpGraphColor.clearCheck()
        }

        when (settings.graphThickness) {
            resources.getDimensionPixelSize(R.dimen.graph_thickness_thin) -> {
                binding.rgrpGraphThikness.check(R.id.rbtn_graph_thin)
            }
            resources.getDimensionPixelSize(R.dimen.graph_thickness_medium) -> {
                binding.rgrpGraphThikness.check(R.id.rbtn_graph_medium)
            }
            resources.getDimensionPixelSize(R.dimen.graph_thickness_thick) -> {
                binding.rgrpGraphThikness.check(R.id.rbtn_graph_thick)
            }
            else -> binding.rgrpGraphThikness.clearCheck()
        }

        when (settings.function){
            Function.FUNCTION_1 -> binding.rgrpFunctions.check(R.id.rbtn_func_1)
            Function.FUNCTION_2 -> binding.rgrpFunctions.check(R.id.rbtn_func_2)
            else -> binding.rgrpFunctions.clearCheck()
        }
    }

    private fun prepareForGraphBuilding() {
        if (!validateBoundsInputValues()) return

        val bounds = Bounds(
            binding.inpXMin.text.toString().toDouble(),
            binding.inpXMax.text.toString().toDouble(),
            binding.inpYMin.text.toString().toDouble(),
            binding.inpYMax.text.toString().toDouble()
        )

        val axesColor = getSelectedAxesColor()
        val graphColor = getSelectedGraphColor()
        val graphThickness = getSelectedGraphThickness()
        val function = getSelectedFunction()
        if (function == null) {
            Toast.makeText(
                this,
                getString(R.string.no_function_selected),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val settings = GraphSettings(
            bounds,
            axesColor,
            graphColor,
            graphThickness,
            function
        )

        saveSettings(settings)

        val intent = Intent(this, GraphActivity::class.java)
        startActivity(intent)

    }

    private fun validateBoundsInputValues(): Boolean {
        if (binding.inpXMin.text.isNullOrBlank()) {
            binding.inpXMin.error = getString(R.string.empty_field_error)
            return false
        }
        if (binding.inpXMax.text.isNullOrBlank()) {
            binding.inpXMax.error = getString(R.string.empty_field_error)
            return false
        }
        if (binding.inpYMin.text.isNullOrBlank()) {
            binding.inpYMin.error = getString(R.string.empty_field_error)
            return false
        }
        if (binding.inpYMax.text.isNullOrBlank()) {
            binding.inpYMax.error = getString(R.string.empty_field_error)
            return false
        }
        return true
    }

    private fun getSelectedAxesColor(): Int? {
        return when (binding.rgrpAxesColor.checkedRadioButtonId) {
            R.id.rbtn_axes_black -> Color.BLACK
            R.id.rbtn_axes_blue -> Color.BLUE
            R.id.rbtn_axes_red -> Color.RED
            else -> null
        }
    }

    private fun getSelectedGraphColor(): Int? {
        return when (binding.rgrpGraphColor.checkedRadioButtonId) {
            R.id.rbtn_graph_green -> Color.GREEN
            R.id.rbtn_graph_blue -> Color.BLUE
            R.id.rbtn_graph_red -> Color.RED
            else -> null
        }
    }

    private fun getSelectedGraphThickness(): Int? {
        return when (binding.rgrpGraphThikness.checkedRadioButtonId) {
            R.id.rbtn_graph_thin -> resources.getDimensionPixelSize(R.dimen.graph_thickness_thin)
            R.id.rbtn_graph_medium -> resources.getDimensionPixelSize(R.dimen.graph_thickness_medium)
            R.id.rbtn_graph_thick -> resources.getDimensionPixelSize(R.dimen.graph_thickness_thick)
            else -> null
        }
    }

    private fun getSelectedFunction(): Function? {
        return when (binding.rgrpFunctions.checkedRadioButtonId) {
            R.id.rbtn_func_1 -> Function.FUNCTION_1
            R.id.rbtn_func_2 -> Function.FUNCTION_2
            else -> null
        }
    }

    private fun saveSettings(settings: GraphSettings){
        Log.d(javaClass.simpleName, settings.toString())
        preferences.saveSettings(settings)
    }
}