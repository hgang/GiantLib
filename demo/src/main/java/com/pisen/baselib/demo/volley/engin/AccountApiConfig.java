package com.pisen.baselib.demo.volley.engin;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class AccountApiConfig {

    /**
     * pisen app config-AppKey
     */
    public static final String AppKey = "15121510";

    /**
     * pisen app config-AppSecret
     */
    public static final String AppSecret = "206c86ce9f304800b7cdc19c03a2cddd";

    /**
     * 获取公共请求键值序列
     */
    private static TreeMap<String, String> getCommonMap(String body, String method) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("AppKey", AppKey);
        map.put("Body", body);
        map.put("Format", "json");
        map.put("Method", method);
        map.put("SessionKey", "");
        map.put("Version", "");
        // 排序
        map = HmacEncryptUtils.sortMapByKey(map);
        String sign = HmacEncryptUtils.encryptByHmacSha1(map, AppSecret);
        map.put("Sign", sign);
        return map;
    }

    /**
     * 获取图片验证码请求键值
     */
    public static TreeMap<String, String> getIconCodeMap() {
        return getCommonMap("{}", "Pisen.Service.Share.SSO.Contract.ICustomerService.AppGetVerifyCode");
    }

    /**
     * 获取手机验证码请求键值
     *
     * @param phone    手机号
     * @param sendType 0注册,1找回密码
     * @return
     */
    public static TreeMap<String, String> getPhoneCodeMap(String phone, int sendType) {
        JSONObject body = new JSONObject();
        try {
            body.put("SendType", String.valueOf(sendType));
            body.put("PhoneNumber", phone);
            body.put("AppKey", AppKey);
            body.put("PlatformType", 2);//急啥为2
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppSendMsg");
    }

    /**
     * 获取注册时提交参数键值序列
     *
     * @return
     */
    public static TreeMap<String, String> getRegisterMap(String phone, String password, String phoneCode) {
        JSONObject body = new JSONObject();
        try {
            body.put("UserName", "");
            body.put("PhoneNumber", phone);
            body.put("PassWord", password);
            body.put("PhoneCode", phoneCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppRegister");
    }

//    /**
//     * 获取注册时提交参数键值序列
//     *
//     * @return
//     */
//    public static TreeMap<String, String> getRegisterMap2(String phone, String password, String phoneCode) {
//        JSONObject body = new JSONObject();
//        try {
//            body.put("UserName", "");
//            body.put("PhoneNumber", phone);
//            body.put("PassWord", encyrptPwd(password));
//            body.put("PhoneCode", phoneCode);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppRegisterV2");
//    }

    /**
     * 获取登录参数参数键值序列
     *
     * @return
     */
    public static TreeMap<String, String> getLoginMap(String phone, String password) {
        return getLoginMap(phone, password, "", "", false);
    }

    /**
     * 获取登录参数参数键值序列
     *
     * @return
     */
    public static TreeMap<String, String> getLoginMap(String phone, String password, String verifyKey, String verifyCode, boolean needVerify) {
        JSONObject body = new JSONObject();
        try {
            body.put("PhoneNumber", phone);
            body.put("PassWord", password);
            body.put("VerifyKey", verifyKey);
            body.put("VerifyCode", verifyCode);
            body.put("NeedVerify", String.valueOf(needVerify));
            body.put("Source", 32);//android
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "SOA.EC.Customer.Contract.ICustomerService.AppLogin");
    }


    /**
     * 获取登录参数参数键值序列
     *
     * @return
     */
//    public static TreeMap<String, String> getLoginMap2(String phone, String password) {
//        return getLoginMap2(phone, password, "", "", false);
//    }

//    /**
//     * 获取登录参数参数键值序列
//     *
//     * @return
//     */
//    public static TreeMap<String, String> getLoginMap2(String phone, String password, String verifyKey, String verifyCode, boolean needVerify) {
//        JSONObject body = new JSONObject();
//        try {
//            body.put("PhoneNumber", phone);
//            body.put("PassWord", encyrptPwd(password));
//            body.put("VerifyKey", verifyKey);
//            body.put("VerifyCode", verifyCode);
//            body.put("NeedVerify", String.valueOf(needVerify));
//            body.put("Source", 32);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return getCommonMap(body.toString(), "SOA.EC.Customer.Contract.ICustomerService.AppLoginV2");
//    }

    /**
     * 验证手机（找回密码的）验证码
     *
     * @return
     */
    public static TreeMap<String, String> getFindPwdVerifyMap(String phone, String code) {
        JSONObject body = new JSONObject();
        try {
            body.put("PhoneNumber", phone);
            body.put("PhoneCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppPhoneCodeVerify");
    }

    /**
     * 验证手机（找回密码的）验证码
     *
     * @return
     */
    public static TreeMap<String, String> getFindPwdVerifyMap2(String phone, String code) {
        JSONObject body = new JSONObject();
        try {
            body.put("PhoneNumber", phone);
            body.put("PhoneCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppPhoneCodeVerifyV2");
    }

    /**
     * 获取重置密码键值序列
     *
     * @param isUpdate true修改密码 false重置密码
     * @return
     */
    public static TreeMap<String, String> getResetPwdMap(String phone, String newPwd, String oldPwd, boolean isUpdate) {
        JSONObject body = new JSONObject();
        try {
            body.put("PhoneNumber", phone);
            body.put("PassWord", newPwd);
            body.put("OldPassword", oldPwd);
            body.put("IsUpdate", String.valueOf(isUpdate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppResetPassWord");
    }

//    /**
//     * 获取重置密码键值序列
//     *
//     * @param isUpdate true修改密码 false重置密码
//     * @return
//     */
//    public static TreeMap<String, String> getResetPwdMap2(String phone, String newPwd, String oldPwd, boolean isUpdate, String certificateID) {
//        JSONObject body = new JSONObject();
//        try {
//            body.put("PhoneNumber", phone);
//            body.put("PassWord", encyrptPwd(newPwd));
//            body.put("OldPassword", encyrptPwd(oldPwd));
//            body.put("IsUpdate", String.valueOf(isUpdate));
//            body.put("CertificateID", certificateID);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return getCommonMap(body.toString(), "Pisen.Service.Share.SSO.Contract.ICustomerService.AppResetPassWordV2");
//    }

    /**
     * 获取上传头像参数参数键值序列
     *
     * @param body
     * @return
     */
    public static TreeMap<String, String> getPostAvatarMap(String body) {
        return getCommonMap(body, "Pisen.Service.Share.SSO.Contract.ICustomerService.AppCustomerUploadImage");
    }


//    private static String encyrptPwd(String pwd) {
//        LogCat.d("before encrypt = " + pwd);
//        String ret = pwd;
//        try {
//            ret = DESEncryptor.encode(pwd, HttpKeys.DES_KEY);
//            LogCat.d("after encrypt = " + ret);
//            return ret;
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogCat.d("加密失败: " + e.getMessage());
//        }
//        return pwd;
//    }

}
