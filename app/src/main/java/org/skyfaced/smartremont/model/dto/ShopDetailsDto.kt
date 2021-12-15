package org.skyfaced.smartremont.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.skyfaced.smartremont.model.adapter.FilialItem
import org.skyfaced.smartremont.model.adapter.TagItem

@Serializable
data class ShopDetailsDto(
    @SerialName("shop_id")
    val shopId: Int,
    @SerialName("shop_name")
    val shopName: String,
    @SerialName("shop_description")
    val shopDescription: String,
    @SerialName("icon_text")
    val iconText: String,
    @SerialName("icon_url")
    val iconUrl: String,
    @SerialName("shop_cnt")
    val shopCnt: Int,
    @SerialName("cashback_percent_min")
    val cashbackPercentMin: Int,
    @SerialName("cashback_percent_max")
    val cashbackPercentMax: Int,
    @SerialName("shop_filials")
    val shopFilials: List<ShopFilial>,
    @SerialName("shop_tags")
    val shopTags: List<ShopTags>
) {
    @Serializable
    data class ShopFilial(
        @SerialName("shop_city_id")
        val shopCityId: Int,
        @SerialName("shop_address")
        val shopAddress: String,
        @SerialName("shop_grafik")
        val shopGrafik: List<ShopGrafik>,
        @SerialName("phone_numbers")
        val phoneNumbers: List<String>,
        @SerialName("gps_coord")
        val gpsCoord: List<Double>,
        @SerialName("icon_url")
        val iconUrl: String? = null,
        @SerialName("cashback_percent")
        val cashbackPercent: Int,
        @SerialName("icon_text")
        val iconText: String,
        @SerialName("site_url")
        val siteUrl: String
    ) {
        @Serializable
        data class ShopGrafik(
            val day: String,
            val time: String
        )

        fun toFilialItem() = FilialItem(
            id = shopCityId,
            address = shopAddress,
            siteUrl = siteUrl
        )
    }

    @Serializable
    data class ShopTags(
        @SerialName("tag_id")
        val tagId: Int,
        @SerialName("tag_name")
        val tagName: String
    ) {
        fun toTagItem() = TagItem(
            id = tagId,
            name = tagName
        )
    }
}