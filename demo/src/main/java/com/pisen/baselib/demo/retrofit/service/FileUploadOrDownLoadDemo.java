package com.pisen.baselib.demo.retrofit.service;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yangbo on 2016/4/7.
 *
 * @version 1.0
 */
public class FileUploadOrDownLoadDemo {

    /**
     * 测试上传 map封装格式
     *
     * @return
     */
    public static Map<String, RequestBody> getTestUploadMap() {

        Map<String, RequestBody> paramsMap = new HashMap<>();

        //文件传输
        String filePath = Environment.getExternalStorageDirectory().getPath() + "xxxx.jpg";
        File file = new File(filePath);
        String filename = file.getName();
        RequestBody upLoadBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String fileParamsKey = "file";

        //????为什么要这么写 疑惑
        /*
        * 回答：
        * Okhttp 中的上传写法
        * File file = new File(Environment.getExternalStorageDirectory(), "balabala.mp4");
          RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
          RequestBody requestBody = new MultipartBuilder()
                      .type(MultipartBuilder.FORM)
                      .addPart(Headers.of("Content-Disposition", "form-data; name=\"username\""),RequestBody.create(null, "测试值"))
                      .addPart(Headers.of("Content-Disposition","form-data; name=\"mFile\";filename=\"wjd.mp4\""), fileBody)
                      .build();
        *
        *
        * java 模拟表单数据时是以块作为写入的， 文件块块格式如下：
        * StringBuilder sb = new StringBuilder();
        * sb.append("--");
        * sb.append(BOUNDARY);
        * sb.append("\r\n");
        * sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName()+ "\"\r\n");
        * sb.append("Content-Type:application/octet-stream\r\n\r\n");
        *
        * */
        String fileMapKey = fileParamsKey+"\"; filename=\"" + filename;
        paramsMap.put(fileMapKey, upLoadBody);

        //普通参数
        RequestBody paramBody1 = RequestBody.create(MediaType.parse("text/plain"), "param_value1");
        RequestBody paramBody2 = RequestBody.create(MediaType.parse("text/plain"), "param_value2");
        RequestBody paramBody3 = RequestBody.create(MediaType.parse("text/plain"), "param_value3");
        paramsMap.put("param_key1", paramBody1);
        paramsMap.put("param_key2", paramBody2);
        paramsMap.put("param_key3", paramBody3);
        return paramsMap;
    }

    //上传
/*    public static void main(String... args) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://repo1.maven.org").
                build();

        IfsDefService service = retrofit.create(IfsDefService.class);
        Call<String> call = service.uploadAvator(getTestUploadMap());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }*/

    //下载
    public static void main(String... args) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://repo1.maven.org").
                build();

        IfsDefService service = retrofit.create(IfsDefService.class);
        Call<ResponseBody> call = service.download("fbi.mp4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String filePath = Environment.getExternalStorageDirectory().getPath() + "xxx.mp4";
                File file = new File(filePath);
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(response.body().bytes());
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
