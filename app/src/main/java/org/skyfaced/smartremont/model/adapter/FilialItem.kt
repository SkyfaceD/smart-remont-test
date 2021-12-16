package org.skyfaced.smartremont.model.adapter

data class FilialItem(
    val id: Int,
    val address: String,
    val siteUrl: String,
    val coordinates: Pair<Double, Double>,
    val grafik: List<GrafikItem>,
    val contacts: List<ContactItem>
) : Item