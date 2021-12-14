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
    @GET("/get-code/id/{id}")
    fun getCode(@Path("id") id: Int): BaseResponse<SMSCodeDto?>

    @POST("/register")
    fun register(@Body body: RegisterBody): BaseResponse<RegisterDto?>

    @POST("/sms-code-verify")
    fun smsCodeVerify(@Body body: SMSCodeVerifyBody): BaseResponse<TokenDto?>

    @POST("/password")
    fun password(@Body body: PasswordBody): BaseResponse<Nothing?>

    @POST("/client-login")
    fun clientLogin(@Body body: ClientLoginBody): BaseResponse<TokenDto?>

    @POST("/client-logout")
    fun clientLogOut(): BaseResponse<Nothing?>

    @GET("/city-list")
    fun cityList(): BaseResponse<List<CityDto>?>

    @GET("/shop-list/city_id/{id}")
    fun shopList(@Path("id") cityId: Int): BaseResponse<List<ShopDto>?>

    @GET("/get-shop/shop_id/{shopId}/city_id/{cityId}")
    fun shopInfo(
        @Path("shopId") shopId: Int,
        @Path("cityId") cityId: Int
    ): BaseResponse<ShopInfoDto?>
}