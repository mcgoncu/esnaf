package com.mandm.esnaf;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mandm.esnaf.data.Message;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mcgoncu on 6/13/15.
 */
public class NetworkHelper {

    private final static String BASE_URL = "http://dev.t-appz.com/tcommerce/Plugins/Misc.TappzServices/Remote/Service.svc/";
    private final static String APPLICATION_ID = "";

    private final static String ACCEPT = "application/json";
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36)";// "IOS-Tablet (IOS; 7.0.4; iPad; 711C3786-5A29-4391-9884-82BE11252756)"; //"MobileSite (Android; 4.4.2; web GT-I9300; mobil)"



    private static NetworkHelper instance;
    private RequestQueue mQueue;
    private final Gson gson;

    private NetworkHelper(Context context) {
        gson = new Gson();
        mQueue = Volley.newRequestQueue(context);
    }

    public static NetworkHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkHelper(context);
        }
        return instance;
    }

    public void get(final Message request, final ResponseListener listener) {
        performRequest(Request.Method.GET, request, listener);
    }

    public void post(final Message request, final ResponseListener listener) {
        performRequest(Request.Method.POST, request, listener);
    }

    private void performRequest(final int method, final Message request, final ResponseListener listener) {

        String data = request.request == null ? null : gson.toJson(request.request);

        JsonRequest<Message> req = new JsonRequest<Message>(method, BASE_URL + request.url, data,
                new Response.Listener<Message>() {
                    @Override
                    public void onResponse(Message response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                    //TODO : mcgoncu
                }) {

            @Override
            protected Response<Message> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    Log.i("response", json);
                    Message message = new Message();
                    message.response = gson.fromJson(json, request.responseType);
                    return Response.success(message, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", GetAuthorization());
                headers.put("Application", APPLICATION_ID);
                headers.put("Accept", ACCEPT);
                headers.put("User-Agent", USER_AGENT);
                headers.put("Accept-Language", "tr-TR");
                return headers;
            }
        };
        req.setTag(method + " " + request.url);
        mQueue.add(req);
    }

    private String GetAuthorization() {
        return "a a a a a"; // TODO: mcgoncu
    }

    public interface ResponseListener {
        void onResponse(Message response);
    }
}
