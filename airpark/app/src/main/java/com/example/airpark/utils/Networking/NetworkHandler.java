package com.example.airpark.utils.Networking;

import android.content.Context;

import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class NetworkHandler {

    private static NetworkHandler shared = null;
    private static String httpUrl = "https://airpark-django.herokuapp.com/api/";
    private AsyncHttpClient client;

    public static NetworkHandler getInstance(){

        if (shared == null){
            shared = new NetworkHandler();
        }

        return shared;
    }

    private NetworkHandler(){
        client = new AsyncHttpClient();
        client.addHeader("Content-type", "application/json;charset=utf-8");
    }

    public void loginUser(String email, String password, Context context, NetworkingClosure completion){

        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("email", email);
            dataJson.put("password", password);
            performPostRequest(EndPoints.login, dataJson, context, completion);
        }catch (Exception e){
            completion.completion(null, e.getMessage());
            return;
        }
    }

    private void performPostRequest(String endpoint, JSONObject params, Context context, NetworkingClosure completion){

        StringEntity se = new StringEntity(params.toString(), "UTF-8");

        client.post(context, httpUrl + endpoint, se, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                completion.completion(response, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                if (errorResponse != null){
                    try {
                        completion.completion(null, errorResponse.get("detail").toString());
                    }catch (Exception e){
                        completion.completion(null, null);
                    }
                }else if (throwable != null){
                    completion.completion(null, throwable.getMessage());
                }else{
                    completion.completion(null, null);
                }
            }
        });
    }
}
