package com.common.res.config

import android.content.Context
import android.text.TextUtils
import com.common.res.http.GlobalHttpHandler
import com.common.res.http.api.LoginService
import com.common.res.http.net.ReceiveObject
import com.common.res.utils.appLogoutToLogin
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters


//请求前和请求后的操作可以放在这
class GlobalHttpHandlerImpl(private val context: Context) : GlobalHttpHandler {

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      [okhttp3.Interceptor.Chain]
     * @param response   [Response]
     * @return [Response]
     */
    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken).build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        try {
            val moshi = Moshi.Builder()
                .add(NullSafeStandardJsonAdapters.FACTORY)
                .add(NullSafeKotlinJsonAdapterFactory())
                .addLast(KotlinJsonAdapterFactory()
            ).build()
            val adapter = moshi.adapter(ReceiveObject::class.java)
            httpResult?.let {
                val fromJson = adapter.fromJson(httpResult)
                if (fromJson?.code == 401) {
                    val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL) //域名
                        .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization()) //使用 Moshi
                        .build()
                    val login = retrofit.create(LoginService::class.java).login(hashMapOf<String, String>().apply {
                        put("username", AppConfig.username)
                        put("password", AppConfig.password)
                    })
                    val execute = login.execute()
                    if (execute.isSuccessful) {
                        if (TextUtils.isEmpty(execute.body()?.token.toString())) {
                            appLogoutToLogin()
                            return response
                        } else {
                            AppConfig.token = execute.body()?.token.toString()
                        }
                    } else {
                        return response
                    }
//                    val newResponse = OkHttpClient().newCall(chain.request().newBuilder().header("Authorization", AppConfig.token).build()).execute()
                    val newResponse = OkHttpClient().newCall(chain.request().newBuilder().build()).execute()
                    return newResponse.newBuilder().networkResponse(null).build()
                } else {
                    return response
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return response
        }
        return response
    }


    /**
     * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   [okhttp3.Interceptor.Chain]
     * @param request [Request]
     * @return [Request]
     */
    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId).build(); */
//            .setEncodedQueryParameter("", token)
//        return request.newBuilder().header("Authorization", AppConfig.token).build()
        return request.newBuilder().build()
    }

}