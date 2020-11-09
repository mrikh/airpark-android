package com.example.airpark.utils.HelperInterfaces;

import org.json.JSONObject;

public interface NetworkingClosure {

    void completion(JSONObject object, String message);
}
