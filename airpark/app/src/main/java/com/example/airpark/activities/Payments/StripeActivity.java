package com.example.airpark.activities.Payments;

import androidx.activity.ComponentActivity;
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
import com.example.airpark.activities.PaymentConfirmedActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.Vehicle;
import com.example.airpark.utils.HelperInterfaces.StripeCompletionAction;
import com.example.airpark.models.UserModel;
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
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class StripeActivity extends AppCompatActivity {

    private Stripe stripe;
    private PaymentSession paymentSession;
    private ProgressBar progressBar;
    private String currentCustomerId;
    private BookingTicket ticket;
    private Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");
        vehicle = (Vehicle)myIntent.getSerializableExtra("vehicle");

        progressBar = findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);

        stripe = new Stripe(this, Objects.requireNonNull("pk_test_51HrvFLISFKjjBkEL0Vnxz62UUYtlQpDJcrUHmvSIvqed63wxTel3PfaZhdvhTT0uqKukhLVKfBpv4bkBPZItYJEB00SeuhsWMH"));

        CustomerSession.initCustomerSession(this, new EphKeyProvider(UserModel.currentUser.getEmail(), this, new StripeCompletionAction() {
            @Override
            public void onComplete(ComponentActivity c) {
                paymentSession = new PaymentSession(c, new PaymentSessionConfig.Builder()
                        .setShippingInfoRequired(false)
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
                // use paymentMethod
                if (paymentSessionData.isPaymentReadyToCharge()){
                    progressBar.setVisibility(View.VISIBLE);

                    CustomerSession.getInstance().retrieveCurrentCustomer(new CustomerSession.CustomerRetrievalListener() {
                        @Override
                        public void onCustomerRetrieved(@NotNull Customer customer) {
                            NetworkHandler.getInstance().paymentIntent(customer.getId(), new NetworkingClosure() {
                                @Override
                                public void completion(JSONObject object, String message) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (object != null){
                                        try {
                                            String client_secret = object.getString("client_secret");
                                            paymentSucess(client_secret, paymentSessionData.getPaymentMethod().id);

                                            Intent myIntent = new Intent(StripeActivity.this, PaymentConfirmedActivity.class);
                                            myIntent.putExtra("ticket", ticket);
                                            myIntent.putExtra("vehicle", (Serializable) vehicle);
                                            startActivity(myIntent);
                                        }catch(Exception e){
                                            displayAlert("Oops", e.getLocalizedMessage());
                                        }
                                    }else{
                                        displayAlert("Oops", message);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(int i, @NotNull String s, @Nullable StripeError stripeError) {
                            displayAlert("Oops", stripeError.getMessage());
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
                displayAlert("Oops", errorMessage);
            }
        });
    }

    private void paymentSucess(String clientSecret, String clientPaymentId){
        stripe.confirmPayment(this, ConfirmPaymentIntentParams.createWithPaymentMethodId(clientPaymentId, clientSecret));
    }

    public void displayAlert(String title, String body){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(body);
        builder1.setTitle(title);

        builder1.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

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

            if (status == PaymentIntent.Status.Succeeded) {
                activity.displayAlert("Payment completed", paymentIntent.getDescription());
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                activity.displayAlert("Payment failed", Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage());
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripeActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }
}