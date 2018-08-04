package com.xuebusi.xbs.common.res;
import com.xuebusi.xbs.common.rpc.RpcUUIDGenerator;

import java.io.Serializable;
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -4255935577442133607L;

    private int code = 200; // 默认200状态
    private String sid; // 服务端请求ID
    private T data; // 服务端返回数据集
    private boolean success;
    private String performance; // 表示请求耗时,默认单位:ms
    private String errorCode;
    private String errorMsg;
    private Throwable exception;
    private long requestTime; // 请求时间(初始化)

    public Response() {
    }

    private Response(String sid,long requestTime) {
        this.sid = sid;
        this.requestTime = requestTime;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public static <T> Response<T> create() {
        String sid = RpcUUIDGenerator.get32UUID(); // 默认UUID
        long requestTime = System.currentTimeMillis(); // 初始化对象请求[时间]
        return new Response<>(sid,requestTime);
    }

    public static <T> Response<T> create(String sid) {
        long requestTime = System.currentTimeMillis(); //
        return new Response<>(sid,requestTime);
    }

    public void setData(T data) {
        this.data = data;
        this.setSuccess(true);
        this.setPerformance(this.executionTime());
    }

    /**
     * 接口调用成功
     */
    public void success(T data) {
        this.data = data;
        this.setSuccess(true);
        this.setPerformance(this.executionTime());
    }

    public void failure(String errorCode, String errorMsg) {
        this.setErrorCode(errorCode);
        this.setErrorMsg(errorMsg);
        this.setSuccess(false);
        this.setPerformance(this.executionTime());
    }

    public void failure(String errorMsg) {
        this.failure(null, errorMsg);
        this.setSuccess(false);
        this.setPerformance(this.executionTime());
    }

    public void failure(Throwable exception) {
        this.exception = exception;
        this.setSuccess(false);
        this.setPerformance(this.executionTime());
    }

    public long executionTime() { // 相应执行计算时间 @TODO -> 此种方法计算的并不准,多少当做服务相应的一个参考
        long performance = (System.currentTimeMillis() - requestTime)/1000;
        return performance;
    }


    @Override
    public String toString() {
        return "Response [code=" + code + ", sid=" + sid + ", data=" + data + ", success=" + success + ", performance="
                + performance + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", exception=" + exception
                + "]";
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(long performance) {
        //	this.performance = performance+"/ms";
    }

    public static void main(String[] args) throws InterruptedException {
        Response<String> response = Response.create();
        //	response.setErrorMsg("time out");
        Thread.sleep(1000);
        response.setData("Hello");
        //	response.setErrorCode("500");
        //	response.failure(new Exception("error"));
        System.out.println(response);
    }
}
