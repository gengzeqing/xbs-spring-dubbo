package com.xuebusi.xbs.utils.RequestUtils;
import java.io.Serializable;

public class Request<T> implements Serializable {

    private static final long serialVersionUID = -4255935577442133607L;

    private String sid; // 客户端请求ID
    private Long reqTime; // 请求时间
    private T data; // 请求参数体
    private String ip; // 请求服务的IP

    private Request(T data) {
        if(data == null) {
            throw new RpcException(RpcException.ERROR_MAP.get(RpcException.REQUEST_PARAM_SIZE_ERROR));
        }
        this.data = data;
        this.reqTime = System.currentTimeMillis(); // 服务端处理的标识
        this.ip = RpcNetworkUtil.localAddress();
        this.setSid(clientUID());
    }

    private Request(String sid) {
        this.sid = sid;
        this.reqTime = System.currentTimeMillis(); // 服务端处理的标识
        this.ip = RpcNetworkUtil.localAddress();
    }

    public void setSid(String sid) {
        if(this.sid ==null){
            this.sid = sid;
        }
    }

    public PageRequest.PageInfo getPageInfo() {
        if(PageRequest.class.isInstance(this)) {
            return PageRequest.class.cast(this).getPageInfo();
        }
        return null;
    }

    public Request() {}

    public static <T>Request <T> create(){
        return new Request<T>();
    }

    public static <T>Request <T> create(String sid){
        return new Request<T>(sid);
    }

    public static <T> Request<T> create(T data) {
        if(data == null) {
            throw new RpcException(RpcException.ERROR_MAP.get(RpcException.REQUEST_PARAM_SIZE_ERROR));
        }
        return new Request<T>(data);
    }

    public static String clientUID() {
        StringBuilder process = new StringBuilder();
        process.append(RpcUUIDGenerator.get32UUID()).append(System.currentTimeMillis());
        return process.toString();
    }

    public Long getReqTime() {
        return reqTime;
    }

    public void setReqTime(Long reqTime) {
        this.reqTime = reqTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if(data == null) {
            throw new RpcException(RpcException.ERROR_MAP.get(RpcException.REQUEST_PARAM_SIZE_ERROR));
        }
        this.data = data;
        this.setSid(clientUID());
        this.reqTime = System.currentTimeMillis(); // 服务端处理的标识
        this.ip = RpcNetworkUtil.localAddress();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSid() {
        return sid;
    }

    @Override
    public String toString() {
        return "Request [cid=" + sid + ", reqTime=" + reqTime + ", data=" + data + ", ip=" + ip + "]";
    }
}