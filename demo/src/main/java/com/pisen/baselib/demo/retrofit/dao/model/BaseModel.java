package com.pisen.baselib.demo.retrofit.dao.model;


import com.pisen.baselib.demo.retrofit.dao.BasePojo;

/**
 * Created by yangbo on 2016/4/6.
 *
 * @version 1.0
 */
public class BaseModel extends BasePojo {

    public boolean IsError;
    public boolean IsSuccess;
    public String ErrCode;
    public String ErrMsg;
    public String DetailError;
    public String Message;

    public boolean isError() {
        return IsError;
    }

    public void setIsError(boolean isError) {
        IsError = isError;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        IsSuccess = isSuccess;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public String getDetailError() {
        return DetailError;
    }

    public void setDetailError(String detailError) {
        DetailError = detailError;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
