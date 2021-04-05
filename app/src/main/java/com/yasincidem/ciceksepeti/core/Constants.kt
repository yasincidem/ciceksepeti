package com.yasincidem.ciceksepeti.core

object Constants {
    const val BASE_URL = "https://api.ciceksepeti.com/"
    const val PRODUCT_BASE_ENDPOINT = "/v2/product/ch/dynamicproductlist"
    const val TIME_OUT_SECONDS = 10
    const val SWIPE_UP_ANIM_ASSET_NAME = "swipe.json"
}


enum class FilterType(val value: Pair<Int, String>) {
    DETAIL(1 to "detailList"),
    CHECK(2 to "checkList"),
    PRICE(3 to "priceList");

    companion object {
        fun valueOf(value: Int) = values().find { it.value.first == value }
    }
}