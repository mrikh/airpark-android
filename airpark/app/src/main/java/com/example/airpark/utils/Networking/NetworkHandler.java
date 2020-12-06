package com.example.airpark.utils.Networking;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkHandler {

    private static NetworkHandler shared = null;
    private static String httpUrl = "https://airpark-django.herokuapp.com/api/";

    public static NetworkHandler getInstance(){

        if (shared == null){
            shared = new NetworkHandler();
        }

        return shared;
    }

    public void paymentDone(JSONObject object, NetworkingClosure completion){

        performPostRequest(EndPoints.paymentDone, object, completion);
    }

    public void calculatePrice(JSONObject object, NetworkingClosure completion){
        performPostRequest(EndPoints.calcPrice, object, completion);
    }

    public void getAvailableCarParks( HashMap<String, String> params, NetworkingClosure completion){

        performGetRequest(EndPoints.availabilityList, params, completion);
    }

    public void airportsListing(NetworkingClosure completion){

        performGetRequest(EndPoints.airportsList, new HashMap<>(), completion);
    }

    public void paymentIntent(String customerId, JSONObject params, NetworkingClosure completion){

        try {
            params.put("customer_id", customerId);
            performPostRequest(EndPoints.paymentIntent, params, completion);
        }catch (Exception e){
            completion.completion(null, e.getMessage());
            return;
        }
    }

    public void signUp(String email, String password, String name, NetworkingClosure completion){

        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("email", email);
            dataJson.put("password", password);
            dataJson.put("name", name);
            performPostRequest(EndPoints.signUp, dataJson, completion);
        }catch (Exception e){
            completion.completion(null, e.getMessage());
            return;
        }
    }

    public void loginUser(String email, String password, NetworkingClosure completion){

        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("email", email);
            dataJson.put("password", password);
            performPostRequest(EndPoints.login, dataJson, completion);
        }catch (Exception e){
            completion.completion(null, e.getMessage());
            return;
        }
    }

    private void performPostRequest(String endpoint, JSONObject params, NetworkingClosure completion){

        AndroidNetworking.post(httpUrl + endpoint).addJSONObjectBody(params).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.get("code").toString();
                    if (code.equalsIgnoreCase("200")){
                        completion.completion(response.getJSONObject("data"), null);
                    }else{
                        completion.completion(null, response.get("message").toString());
                    }
                }catch (Exception e){
                    completion.completion(null, e.getMessage());
                }
            }

            @Override
            public void onError(ANError anError) {
                if (anError != null){
                    try {
                        completion.completion(null, anError.getErrorDetail());
                    }catch (Exception e){
                        completion.completion(null, null);
                    }
                }else{
                    completion.completion(null, null);
                }
            }
        });
    }

    private void performGetRequest(String endpoint, HashMap<String, String> params, NetworkingClosure completion){

        AndroidNetworking.get(httpUrl + endpoint).addQueryParameter(params).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code = response.get("code").toString();
                    if (code.equalsIgnoreCase("200")){
                        completion.completion(response.getJSONObject("data"), null);
                    }else{
                        completion.completion(null, response.get("message").toString());
                    }
                }catch (Exception e){
                    completion.completion(null, e.getMessage());
                }
            }

            @Override
            public void onError(ANError anError) {
                if (anError != null){
                    try {
                        completion.completion(null, anError.getErrorDetail());
                    }catch (Exception e){
                        completion.completion(null, null);
                    }
                }else{
                    completion.completion(null, null);
                }
            }
        });
    }
}
