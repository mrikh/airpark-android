package com.example.airpark.activities.Payments;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.utils.HelperInterfaces.Completion;
import com.example.airpark.models.UserModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.ShippingInformation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StripeActivity extends AppCompatActivity {

    private PaymentSession paymentSession;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);

        progressBar = findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);

        CustomerSession.initCustomerSession(this, new EphKeyProvider(UserModel.currentUser.getEmail(), this, new Completion() {
            @Override
            public void onComplete(Context c) {
                paymentSession = new PaymentSession((ComponentActivity) c, new PaymentSessionConfig.Builder()
                        .setPrepopulatedShippingInfo(getDefaultShippingInfo())
                        .setShippingMethodsRequired(false)
                        .build());
                setupPaymentSession();
                paymentSession.presentPaymentMethodSelection(null);
            }
        }));
    }

    private void setupPaymentSession() {
        paymentSession.init(new PaymentSession.PaymentSessionListener() {
            @Override
            public void onPaymentSessionDataChanged(@NotNull PaymentSessionData paymentSessionData) {
                final PaymentMethod paymentMethod = paymentSessionData.getPaymentMethod();
                // use paymentMethod
            }

            @Override public void onCommunicatingStateChanged(boolean isCommunicating) {
                // update UI, such as hiding or showing a progress bar
                if (isCommunicating){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override public void onError(int errorCode, @Nullable String errorMessage) {
                // handle error
                Toast.makeText(StripeActivity.this, errorMessage, Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            paymentSession.handlePaymentData(requestCode, resultCode, data);
        }
    }

    private ShippingInformation getDefaultShippingInfo() {
        return new ShippingInformation();
    }
}