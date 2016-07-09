package com.pisen.baselib.demo.retrofit.service;

import com.cn.design.pisen.retrofit_demo.dao.model.AccountOffset;
import com.cn.design.pisen.retrofit_demo.dao.req_resp.AccountOffsetReq;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by yangbo on 2016/4/6.
 * 请求接口定义类
 * @version 1.0
 */
public interface IfsDefService {

    /**
     * get 请求
     * @param username 替换接口地址{username}
     * @return 结果
     */
    @GET("InitializeLog/{username}")
    Call<String> getUser(@Path("username") String username);

    /**
     * get 请求
     * @param paramsMap 会将键值对添加到请求地址后
     * @return 结果
     */
    @GET("InitializeLog")
    Call<String> getUser(@QueryMap Map<String, String> paramsMap);

    /**
     * post表单————对象提交
     * @param xhDictReq req Pojo
     * @return 结果
     */
    @POST("InitializeLog")
    Call<AccountOffset> queryByPojo_POST(@Body AccountOffsetReq xhDictReq);
    /**
     * post表单————对象提交
     * @param xhDictReq req Pojo
     * @return string结果 GsonConverter并未直接提供基本类型的转换，需添加ScalarsConverterFactory扩展
     */
    @POST("InitializeLog")
    Call<String> queryByPojoRetunStr_POST(@Body AccountOffsetReq xhDictReq);

    /**
     * post表单
     * @param names 键值对
     * @return 结果
     */
    @FormUrlEncoded
    @POST("InitializeLog")
    Call<AccountOffset> queryByMap_POST(@FieldMap Map<String, Object> names);

    /**
     *  表单上传文件
     * @param params 键值对
     * @return 结果
     */
    @Multipart
    @POST("/uploadAvatar")
    Call<String> uploadAvator(@PartMap Map<String, RequestBody> params);

    /**
     * 下载文件
     * @param fileName 文件名
     * @return 结果
     */
    @GET("InitializeLog/download/{fileName}")
    Call<ResponseBody> download(@Path("fileName") String fileName);

}
