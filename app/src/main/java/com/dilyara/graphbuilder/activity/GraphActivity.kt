package com.dilyara.graphbuilder.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dilyara.graphbuilder.R
import com.dilyara.graphbuilder.data.Function
import com.dilyara.graphbuilder.data.GraphSettings
import com.dilyara.graphbuilder.data.GraphViewModel
import com.dilyara.graphbuilder.data.SettingsPreferences
import com.dilyara.graphbuilder.databinding.ActivityGraphBinding

class GraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphBinding

    private lateinit var viewModel: GraphViewModel

    private lateinit var preferences: SettingsPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        preferences = SettingsPreferences(this)
        getSettings()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(GraphViewModel::class.java)

        viewModel.observe(this) { settings ->
            if (settings == null) {
                Toast.makeText(this, getString(R.string.no_graph_settings), Toast.LENGTH_SHORT)
                    .show()
            } else {
                buildGraph(settings)
            }
        }
    }

    private fun getSettings() {
        val settings = preferences.getSettings()
        viewModel.setSettingByString(settings)
    }

    private fun buildGraph(settings: GraphSettings){
        binding.painter.setBounds(settings.bounds)
        settings.axesColor?.let { binding.painter.setAxesColor(it) }
        settings.graphColor?.let { binding.painter.setGraphColor(it) }
        settings.graphThickness?.let { binding.painter.setGraphThickness(it) }
        binding.painter.setFunction(settings.function)
    }
}