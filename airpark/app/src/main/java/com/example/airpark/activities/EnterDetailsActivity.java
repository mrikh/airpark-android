package com.example.airpark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.activities.Payments.StripeActivity;
import com.example.airpark.activities.Prelogin.LoginActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.UserModel;
import com.example.airpark.models.Vehicle;
import com.example.airpark.designPatterns.factory.VehicleFactory;
import com.example.airpark.utils.HelperInterfaces.Callback;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.HelperInterfaces.StripeCompletionAction;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.textfield.TextInputEditText;
import com.stripe.android.CustomerSession;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Enter User Details screen -> details applied to booking
 */
public class EnterDetailsActivity extends AppCompatActivity {

    private LinearLayout loginContainer;
    private TextInputEditText email, name, carReg, phoneNumber;
    private Button loginBtn, confirmBtn;
    private CheckBox elderlyCheck, carWashCheck;
    private InputValidator validator;
    private BookingTicket ticket;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");

        bindUiItems();

        if (UserModel.currentUser != null) {
            loginContainer.setVisibility(View.GONE);
            name.setText(UserModel.currentUser.getName());
            email.setText(UserModel.currentUser.getEmail());
        }

        validator = new InputValidator();

        loginBtn.setOnClickListener(v -> navigateToLogin());
        name.setOnClickListener(v -> name.setError(null));
        email.setOnClickListener(v -> email.setError(null));
        phoneNumber.setOnClickListener(v -> phoneNumber.setError(null));
        carReg.setOnClickListener(v -> carReg.setError(null));

        progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        confirmBtn.setOnClickListener(v -> {

            if(validateUserDetails()){

                Vehicle vehicle = ticket.getSpaceRequired().getVehicle();
                vehicle.setVehicleReg(carReg.toString());

                ticket.setCustomerName(name.getText().toString());
                ticket.setCustomerEmail(email.getText().toString());
                ticket.setCustomerNumber(phoneNumber.getText().toString());
                ticket.setCarRegistration(carReg.getText().toString());
                ticket.setOld(elderlyCheck.isChecked());
                ticket.setHasCarWash(carWashCheck.isChecked());
                if(UserModel.currentUser != null) {
                    ticket.setLoggedIn(UserModel.currentUser.getEmail().equalsIgnoreCase(email.getText().toString()));
                }

                try {
                    JSONObject params = ticket.convertForBooking();
                    progressBar.setVisibility(View.VISIBLE);
                    CustomerSession.initCustomerSession(this, new EphKeyProvider(new StripeCompletionAction() {
                        @Override
                        public void onComplete(String version, EphemeralKeyUpdateListener keyUpdateListener){
                            progressBar.setVisibility(View.INVISIBLE);
                            try {
                                params.put("version", version);
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(confirmBtn.getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG);
                            }
                            progressBar.setVisibility(View.VISIBLE);
                            NetworkHandler.getInstance().calculatePrice(params, new NetworkingClosure() {
                                @Override
                                public void completion(JSONObject object, String message){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    try {
                                        String key = object.getJSONObject("key").toString();
                                        keyUpdateListener.onKeyUpdate(key);
                                        JSONArray jsonArray = object.getJSONArray("discounts");
                                        int totalLength = jsonArray.length();
                                        String[] discounts = new String[totalLength];
                                        for (int i = 0;i<totalLength;i++){
                                            discounts[i] = jsonArray.getString(i);
                                        }
                                        PopUpConfirmPayment popUpWindow = new PopUpConfirmPayment(object.getLong("total"), discounts, new Callback() {
                                            @Override
                                            public void onComplete() {
                                                Intent myIntent = new Intent(EnterDetailsActivity.this, StripeActivity.class);
                                                myIntent.putExtra("ticket", ticket);
                                                startActivity(myIntent);
                                            }
                                        });
                                        popUpWindow.showPopUp(v);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(confirmBtn.getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG);
                                    }
                                }
                            });
                        }
                    }));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(confirmBtn.getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG);
                }
            }
        });

        findViewById(R.id.enterDetail_background).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bind Ui with id
     */
    private void bindUiItems(){
        loginContainer = (LinearLayout) findViewById(R.id.enterDetails_login_container);
        loginBtn = (Button)findViewById(R.id.login_btn);
        name = (TextInputEditText)findViewById(R.id.enterDetails_name);
        email = (TextInputEditText)findViewById(R.id.enterDetails_email);
        phoneNumber = (TextInputEditText)findViewById(R.id.enterDetails_phoneNo);
        carReg = (TextInputEditText)findViewById(R.id.enterDetails_carReg);
        elderlyCheck = (CheckBox)findViewById(R.id.elderly_check);
        carWashCheck = (CheckBox)findViewById(R.id.carWash_check);
        confirmBtn = (Button)findViewById(R.id.confirm_Btn);
    }

    /**
     * Method to validate user details
     * @return
     */
    private boolean validateUserDetails(){
        if(!validator.isValidName(name.getText().toString())){
            name.setError(getString(R.string.name_error));
        }
        if(!validator.isValidEmail(email.getText().toString())){
            email.setError(getString(R.string.invalid_email));
        }
        if(!validator.isValidPhoneNumber(phoneNumber.getText().toString())){
            phoneNumber.setError(getString(R.string.invalid_phone));
        }
        if(!validator.isValidReg(carReg.getText().toString())){
            carReg.setError(getString(R.string.invalid_reg));
        }
        if(name.getError() == null && email.getError() == null && phoneNumber.getError() == null && carReg.getError() == null){
            return true;
        }
        return false;
    }

    private void navigateToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("ticket", ticket);
        startActivity(intent);
    }

    private class EphKeyProvider implements EphemeralKeyProvider {

        private StripeCompletionAction completion;

        public EphKeyProvider(StripeCompletionAction completion){
            this.completion = completion;
        }

        @Override
        public void createEphemeralKey(@NotNull String s, @NotNull EphemeralKeyUpdateListener ephemeralKeyUpdateListener) {
            completion.onComplete(s, ephemeralKeyUpdateListener);
        }
    }
}
