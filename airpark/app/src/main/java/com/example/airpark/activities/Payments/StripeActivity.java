package com.example.airpark.activities.Payments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.activities.Create.QRgeneratorActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.Stripe;
import com.stripe.android.StripeError;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.Customer;
import com.stripe.android.model.PaymentIntent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Enter card details for payment
 */
public class StripeActivity extends AppCompatActivity {

    private Stripe stripe;
    private PaymentSession paymentSession;
    private ProgressBar progressBar;
    private BookingTicket ticket;

    public DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        progressBar = findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);

        stripe = new Stripe(this, Objects.requireNonNull("pk_test_51HrvFLISFKjjBkEL0Vnxz62UUYtlQpDJcrUHmvSIvqed63wxTel3PfaZhdvhTT0uqKukhLVKfBpv4bkBPZItYJEB00SeuhsWMH"));

        paymentSession = new PaymentSession(this, new PaymentSessionConfig.Builder()
                .setShippingInfoRequired(false)
                .setShippingMethodsRequired(false)
                .build());
        setupPaymentSession();
        paymentSession.presentPaymentMethodSelection(null);
    }

    private void setupPaymentSession() {
        paymentSession.init(new PaymentSession.PaymentSessionListener() {
            @Override
            public void onPaymentSessionDataChanged(@NotNull PaymentSessionData paymentSessionData) {
                // use paymentMethod
                if (paymentSessionData.isPaymentReadyToCharge()){
                    progressBar.setVisibility(View.VISIBLE);

                    CustomerSession.getInstance().retrieveCurrentCustomer(new CustomerSession.CustomerRetrievalListener() {
                        @Override
                        public void onCustomerRetrieved(@NotNull Customer customer) {
                            try {
                                JSONObject params = ticket.convertForBooking();
                                NetworkHandler.getInstance().paymentIntent(customer.getId(), params, new NetworkingClosure() {
                                    @Override
                                    public void completion(JSONObject object, String message) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        if (object != null){
                                            try {
                                                String client_secret = object.getString("client_secret");
                                                paymentSucess(client_secret, paymentSessionData.getPaymentMethod().id);
                                                progressBar.setVisibility(View.VISIBLE);
                                            }catch(Exception e){

                                                displayAlert(getString(R.string.oops), e.getLocalizedMessage(), cancelListener);
                                            }
                                        }else{
                                            displayAlert(getString(R.string.oops), message, cancelListener);
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(progressBar.getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(int i, @NotNull String s, @Nullable StripeError stripeError) {
                            displayAlert(getString(R.string.oops), stripeError.getMessage(), cancelListener);
                        }
                    });
                }
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
                displayAlert(getString(R.string.oops), errorMessage, cancelListener);
            }
        });
    }

    private void paymentSucess(String clientSecret, String clientPaymentId){
        stripe.confirmPayment(this, ConfirmPaymentIntentParams.createWithPaymentMethodId(clientPaymentId, clientSecret));
    }

    public void completePaymentOnServer(){
        try {
            JSONObject info = ticket.convertForBooking();
            progressBar.setVisibility(View.VISIBLE);
            NetworkHandler.getInstance().paymentDone(info, new NetworkingClosure() {
                @Override
                public void completion(JSONObject object, String message) {
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        Intent myIntent = new Intent(StripeActivity.this, QRgeneratorActivity.class);
                        myIntent.putExtra("code", object.getString("alphanumeric_string"));
                        startActivity(myIntent);
                    }catch(Exception e){
                        e.printStackTrace();
                        displayAlert(getString(R.string.oops), e.getLocalizedMessage(), cancelListener);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            displayAlert(getString(R.string.oops), e.getLocalizedMessage(), cancelListener);
        }
    }

    public void displayAlert(String title, String body, DialogInterface.OnClickListener onClick){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(body);
        builder1.setTitle(title);

        builder1.setPositiveButton(R.string.okay, onClick);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            paymentSession.handlePaymentData(requestCode, resultCode, data);
        }
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private static final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripeActivity> activityRef;

        PaymentResultCallback(@NonNull StripeActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {

            final StripeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();

            activity.progressBar.setVisibility(View.INVISIBLE);
            if (status == PaymentIntent.Status.Succeeded) {
                activity.displayAlert(activity.getString(R.string.payment_completed), paymentIntent.getDescription(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.completePaymentOnServer();
                    }
                });
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                activity.displayAlert(activity.getString(R.string.payment_failed), Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(), activity.cancelListener);
            }
        }

        @Override
        public void onError(@NonNull Exception e) {

            activityRef.get().progressBar.setVisibility(View.INVISIBLE);
            final StripeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert(activity.getString(R.string.oops), e.toString(), activity.cancelListener);
        }
    }

}