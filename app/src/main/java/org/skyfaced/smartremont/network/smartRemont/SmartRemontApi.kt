package org.skyfaced.smartremont.network.smartRemont

import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.body.ClientLoginBody
import org.skyfaced.smartremont.model.body.PasswordBody
import org.skyfaced.smartremont.model.body.RegisterBody
import org.skyfaced.smartremont.model.body.SMSCodeVerifyBody
import org.skyfaced.smartremont.model.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SmartRemontApi {
    @GET("get-code/id/{id}")
    suspend fun getCode(@Path("id") id: Int): BaseResponse<SMSCodeDto?>

    @POST("register")
    suspend fun register(@Body body: RegisterBody): BaseResponse<RegisterDto?>

    @POST("sms-code-verify")
    suspend fun smsCodeVerify(@Body body: SMSCodeVerifyBody): BaseResponse<TokenDto?>

    @POST("password")
    suspend fun password(@Body body: PasswordBody): BaseResponse<Nothing?>

    @POST("client-login")
    suspend fun clientLogin(@Body body: ClientLoginBody): BaseResponse<TokenDto?>

    @POST("client-logout")
    suspend fun clientLogOut(): BaseResponse<Nothing?>

    @GET("city-list")
    suspend fun cityList(): BaseResponse<List<CityDto>?>

    @GET("shop-list/city_id/{id}")
    suspend fun shopList(@Path("id") cityId: Int): BaseResponse<List<ShopDto>?>

    @GET("get-shop/shop_id/{shopId}/city_id/{cityId}")
    suspend fun shopInfo(
        @Path("shopId") shopId: Int,
        @Path("cityId") cityId: Int
    ): BaseResponse<ShopInfoDto?>
}