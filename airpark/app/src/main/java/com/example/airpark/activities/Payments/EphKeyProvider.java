package com.example.airpark.activities.Payments;

import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.example.airpark.utils.HelperInterfaces.StripeCompletionAction;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class EphKeyProvider implements EphemeralKeyProvider {

    private String email;
    private ComponentActivity c;
    private StripeCompletionAction completion;

    public EphKeyProvider(String email, ComponentActivity c, StripeCompletionAction completion){
        this.email = email;
        this.c = c;
        this.completion = completion;
    }

    @Override
    public void createEphemeralKey(@NotNull String s, @NotNull EphemeralKeyUpdateListener ephemeralKeyUpdateListener) {

        NetworkHandler.getInstance().ephemeralKey(email, s, new NetworkingClosure() {
            @Override
            public void completion(JSONObject object, String message) {
                if (object != null){
                    try {
                        String key = object.toString();
                        ephemeralKeyUpdateListener.onKeyUpdate(key);
                        completion.onComplete(c);
                    }catch(Exception e){
                        //do nothing
                        Toast.makeText(c, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }
}
