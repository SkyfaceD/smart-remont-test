package org.skyfaced.smartremont.model.adapter

data class FilialItem(
    val id: Int,
    val address: String,
    val siteUrl: String
) : Item