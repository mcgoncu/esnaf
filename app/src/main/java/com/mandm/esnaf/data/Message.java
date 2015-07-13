package com.mandm.esnaf.data;

import java.lang.reflect.Type;

/**
 * Created by mcgoncu on 6/14/15.
 */
public class Message {

    public String url;
    public ServiceModel request;
    public Type responseType;
    public ServiceModel response;
    public String errorMessage;
    public String errorCode;

    public static Message createRequest(String url, Type responseType){
        return createRequest(url, responseType, null);
    }

    public static Message createRequest(String url, Type responseType, ServiceModel request) {
        Message req = new Message();
        req.url = url;
        req.responseType = responseType;
        req.request = request;
        return req;
    }

    public static Message createResponse(ServiceModel request, String errorCode, String errorMessage){
        Message res = new Message();
        res.request = request;
        res.errorCode = errorCode;
        res.errorMessage = errorMessage;
        return res;
    }
}
