package org.skyfaced.smartremont.util

import android.content.Context
import android.widget.ArrayAdapter

class SaveListArrayAdapter<T>(
    context: Context,
    resource: Int,
    items: MutableList<T> = mutableListOf()
) : ArrayAdapter<T>(context, resource, items) {
    val items: List<T>

    init {
        this.items = items
    }
}