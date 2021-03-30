package com.dilyara.graphbuilder.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GraphViewModel : ViewModel() {

    private val settings: MutableLiveData<GraphSettings?> = MutableLiveData()

    fun setSettingByString(settings: String?) {
        this.settings.value = if (settings == null) {
            null
        } else {
            GraphSettings.fromJsonString(settings)
        }
    }

    fun observe(owner: LifecycleOwner, observer: (GraphSettings?) -> Unit) {
        settings.observe(owner, observer)
    }

}