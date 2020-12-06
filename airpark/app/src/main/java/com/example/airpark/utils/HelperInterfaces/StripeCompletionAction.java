package com.example.airpark.utils.HelperInterfaces;
import com.stripe.android.EphemeralKeyUpdateListener;

import org.json.JSONException;

public interface StripeCompletionAction {
    void onComplete(String version, EphemeralKeyUpdateListener keyUpdateListener);
}
