package com.example.airpark.utils.HelperInterfaces;
import com.stripe.android.EphemeralKeyUpdateListener;

import org.json.JSONException;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 */
public interface StripeCompletionAction {
    void onComplete(String version, EphemeralKeyUpdateListener keyUpdateListener);
}
