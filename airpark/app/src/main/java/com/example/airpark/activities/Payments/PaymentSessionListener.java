package com.example.airpark.activities.Payments;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.model.PaymentMethod;

import org.jetbrains.annotations.NotNull;

public class PaymentSessionListener
        implements PaymentSession.PaymentSessionListener {

    // Called whenever the PaymentSession's data changes,
    // e.g. when the user selects a new `PaymentMethod` or enters shipping info.
    @Override
    public void onPaymentSessionDataChanged(@NonNull PaymentSessionData data) {
        if (data.getUseGooglePay()) {
            // customer intends to pay with Google Pay
        } else {
            final PaymentMethod paymentMethod = data.getPaymentMethod();
            if (paymentMethod != null) {
                // Display information about the selected payment method
            }
        }

        // Update your UI here with other data
        if (data.isPaymentReadyToCharge()) {
            // Use the data to complete your charge - see below.
        }
    }

    @Override
    public void onCommunicatingStateChanged(boolean isCommunicating) {

        if (isCommunicating){

        }else{

        }
    }

    @Override
    public void onError(int errorCode, @NotNull String errorMessage) {
    }
}
