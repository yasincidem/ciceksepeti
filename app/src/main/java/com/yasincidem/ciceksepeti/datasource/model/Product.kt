package com.yasincidem.ciceksepeti.datasource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductData(
    @Json(name = "result") val result: Result?,
    @Json(name = "error") val error: Error?
)

@JsonClass(generateAdapter = true)
data class Result (
    @Json(name = "data") val data : Data?
)

@JsonClass(generateAdapter = true)
data class Error (
    @Json(name = "type") val type : Int?,
    @Json(name = "title") val title : String?,
    @Json(name = "message") val message : String?,
    @Json(name = "returnUrl") val returnUrl : String?
)

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "categoryName") val categoryName: String?,
    @Json(name = "products") val products: List<Product>?,
    @Json(name = "filter") val filter: String?,
    @Json(name = "banners") val banners: String?,
    @Json(name = "mainFilter") val mainFilter: MainFilter?,
    @Json(name = "branch") val branch: String?,
    @Json(name = "message") val message: String?,
    @Json(name = "productCount") val productCount: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "isAlternate") val isAlternate: Boolean?,
    @Json(name = "hasOldPrice") val hasOldPrice: Boolean?,
    @Json(name = "storeId") val storeId: String?,
    @Json(name = "backgroundColor") val backgroundColor: String?,
    @Json(name = "subCategoryModel") val subCategoryModel: SubCategoryModel?
)

@JsonClass(generateAdapter = true)
data class DynamicFilter (
    @Json(name = "detailId") val detailId : Int?,
    @Json(name = "name") val name : String?,
    @Json(name = "sequence") val sequence : Int?,
    @Json(name = "clearLink") val clearLink : String?,
    @Json(name = "filterType") val filterType : Int?,
    @Json(name = "urlTag") val urlTag : String?,
    @Json(name = "values") val values : List<Values>?,
    @Json(name = "filterBehaviour") val filterBehaviour : Int?,
    @Json(name = "isMainCategory") val isMainCategory : Boolean?,
    @Json(name = "isReload") val isReload : Boolean?,
    @Json(name = "isRemovableDetail") val isRemovableDetail : Boolean?,
    @Json(name = "dropdownTitle") val dropdownTitle : String?,
    @Json(name = "dropdownInfo") val dropdownInfo : String?,
    @Json(name = "id") val id : Int?
)

@JsonClass(generateAdapter = true)
data class MainFilter (
    @Json(name = "dynamicFilter") val dynamicFilter : MutableList<DynamicFilter>?,
    @Json(name = "sort") val sort : List<Sort>?
)

@JsonClass(generateAdapter = true)
data class Price (
    @Json(name = "current") val current : Double?,
    @Json(name = "old") val old : Double?,
    @Json(name = "tax") val tax : String?,
    @Json(name = "total") val total : Double?,
    @Json(name = "currency") val currency : String?,
    @Json(name = "currencyCode") val currencyCode : String?,
    @Json(name = "oldTotal") val oldTotal : Double?,
    @Json(name = "discountPercentage") val discountPercentage : String?,
    @Json(name = "showFirstPrice") val showFirstPrice : Boolean?,
    @Json(name = "subscriptionPrice") val subscriptionPrice : Double?,
    @Json(name = "subscriptionDiscountPercentage") val subscriptionDiscountPercentage : String?,
    @Json(name = "showCurrencyCode") val showCurrencyCode : Boolean?,
    @Json(name = "showDecimalPart") val showDecimalPart : Boolean?,
    @Json(name = "showDotDecimalPart") val showDotDecimalPart : Boolean?,
    @Json(name = "showRegisterCardButton") val showRegisterCardButton : Boolean?,
    @Json(name = "dateBasedDiscountAmount") val dateBasedDiscountAmount : Double?
)

@JsonClass(generateAdapter = true)
data class ProductDeliveryCity (
    @Json(name = "deliveryCityImage") val deliveryCityImage : String?,
    @Json(name = "isSendToSingleCity") val isSendToSingleCity : Boolean?
)

@JsonClass(generateAdapter = true)
data class Product (
    @Json(name = "adsModel") val adsModel : String?,
    @Json(name = "id") val id : Int?,
    @Json(name = "code") val code : String?,
    @Json(name = "name") val name : String?,
    @Json(name = "image") val image : String?,
    @Json(name = "link") val link : String?,
    @Json(name = "webLink") val webLink : String?,
    @Json(name = "deliveryBadgeType") val deliveryBadgeType : Int?,
    @Json(name = "deliveryBadgeText") val deliveryBadgeText : String?,
    @Json(name = "deliveryChargeText") val deliveryChargeText : String?,
    @Json(name = "price") val price : Price?,
    @Json(name = "dateBasedDiscount") val dateBasedDiscount : String?,
    @Json(name = "marketplaceBranch") val marketplaceBranch : String?,
    @Json(name = "isMarketplace") val isMarketplace : Boolean?,
    @Json(name = "largeImage") val largeImage : String?,
    @Json(name = "isDiscountAvailable") val isDiscountAvailable : Boolean?,
    @Json(name = "isOriginal") val isOriginal : Boolean?,
    @Json(name = "installment") val installment : Boolean?,
    @Json(name = "installmentText") val installmentText : String?,
    @Json(name = "smallImage") val smallImage : String?,
    @Json(name = "xlargeImage") val xlargeImage : String?,
    @Json(name = "mediumImage") val mediumImage : String?,
    @Json(name = "mostSellestProduct") val mostSellestProduct : Boolean?,
    @Json(name = "isHaveVase") val isHaveVase : Boolean?,
    @Json(name = "isHaveVaseText") val isHaveVaseText : String?,
    @Json(name = "productDeliveryCity") val productDeliveryCity : ProductDeliveryCity?,
    @Json(name = "productGroupId") val productGroupId : Int?,
    @Json(name = "variantCode") val variantCode : String?,
    @Json(name = "entryId") val entryId : Int?,
    @Json(name = "reviewRating") val reviewRating : Double?,
    @Json(name = "deliveryChargeMessage") val deliveryChargeMessage : String?,
    @Json(name = "reviewCount") val reviewCount : Int?,
    @Json(name = "information") val information : String?,
    @Json(name = "isSubscription") val isSubscription : Boolean?,
    @Json(name = "productType") val productType : Int?,
    @Json(name = "isDigital") val isDigital : Boolean?,
    @Json(name = "role") val role : String?,
    @Json(name = "responseTimeText") val responseTimeText : String?,
    @Json(name = "showPriceDroppedTagAtFavoritePage") val showPriceDroppedTagAtFavoritePage : Boolean?,
    @Json(name = "isRequiredLegalPermission") val isRequiredLegalPermission : Boolean?
)

@JsonClass(generateAdapter = true)
data class Sort (
    @Json(name = "count") val count : Int?,
    @Json(name = "id") val id : Int?,
    @Json(name = "selected") val selected : Boolean?,
    @Json(name = "link") val link : String?,
    @Json(name = "filterQueryString") val filterQueryString : String?,
    @Json(name = "name") val name : String?,
    @Json(name = "icon") val icon : String?,
    @Json(name = "group") val group : Int?,
    @Json(name = "detailValueId") val detailValueId : Int?,
    @Json(name = "regionRequired") val regionRequired : Boolean?,
    @Json(name = "filterQueryStringWithProductGroup") val filterQueryStringWithProductGroup : String?
)

@JsonClass(generateAdapter = true)
data class SubCategoryModel (
    @Json(name = "shapeType") val shapeType : Int?,
    @Json(name = "subCategoryList") val subCategoryList : List<String>?
)

@JsonClass(generateAdapter = true)
data class Values (
    @Json(name = "count") val count : Int?,
    @Json(name = "id") val id : Int?,
    @Json(name = "selected") val selected : Boolean?,
    @Json(name = "link") val link : String?,
    @Json(name = "filterQueryString") val filterQueryString : String?,
    @Json(name = "name") val name : String?,
    @Json(name = "icon") val icon : String?,
    @Json(name = "group") val group : Int?,
    @Json(name = "detailValueId") val detailValueId : Int?,
    @Json(name = "regionRequired") val regionRequired : Boolean?,
    @Json(name = "filterQueryStringWithProductGroup") val filterQueryStringWithProductGroup : String?
)