package com.example.airpark.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.Discounts;
import com.example.airpark.models.Price;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;


public class EnterDetailsActivity extends AppCompatActivity {
    private LinearLayout loginContainer;
    private TextInputEditText email, name, carReg, phoneNumber;
    private Button loginBtn, confirmBtn;
    private CheckBox elderlyCheck, carWashCheck;
    private InputValidator validator;
    private BookingTicket ticket;
    private Price price;
    private Discounts discounts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindUiItems();

        if (UserModel.currentUser != null) {
            loginContainer.setVisibility(View.GONE);
            name.setText(UserModel.currentUser.getName());
            email.setText(UserModel.currentUser.getEmail());
        }

        /** Hardcoded  **/
        discounts = new Discounts(20);
        price = new Price(0);

        Intent myIntent = getIntent();
        ticket = (BookingTicket)myIntent.getSerializableExtra("ticket");
//        carpark = (CarPark)myIntent.getSerializableExtra("car park");

        validator = new InputValidator();

        name.setOnClickListener(v -> name.setError(null));
        email.setOnClickListener(v -> email.setError(null));
        phoneNumber.setOnClickListener(v -> phoneNumber.setError(null));
        carReg.setOnClickListener(v -> carReg.setError(null));

        confirmBtn.setOnClickListener(v -> {
            if(validateUserDetails()){
                DecimalFormat dFormat = new DecimalFormat("#.##");

                double finalPrice = ticket.getTicketPrice();
                String discountText = null, carWashText = null;

                if(elderlyCheck.isChecked() || carWashCheck.isChecked()){
                    if(elderlyCheck.isChecked()){
                        ticket.setIsElderly(true);

                        double discountPrice = price.discountPrice(ticket.getTicketPrice(), discounts.getElderlyDiscount());
                        discountText = "\nDiscount: " + dFormat.format(discounts.getElderlyDiscount()) + "%";
                        finalPrice = discountPrice;
                    }
                    if(carWashCheck.isChecked()){
                        ticket.sethasCarWash(true);
                        /** Hardcoded **/
                        double carWashPrice = 10;
                        carWashText = "\nCar Wash: €" + dFormat.format(carWashPrice);
                        finalPrice += carWashPrice;
                    }
                }
                String carparkPrice = "\nCar Park: €" + dFormat.format(ticket.getTicketPrice());

                PopUpConfirmPayment popUpWindow = new PopUpConfirmPayment(carparkPrice, discountText, carWashText, dFormat.format(finalPrice));
                popUpWindow.showPopUp(v);

//                openDialogue(carparkPrice, discount, carWash, dFormat.format(finalPrice));
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

//    private void openDialogue(String carparkPrice, String discount, String carWash, String finalPrice){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//        DecimalFormat dFormat = new DecimalFormat("#.00");
//        String finalPayment = carparkPrice + discount + carWash + "\n\nTotal: €" + finalPrice;
//
//        alertDialogBuilder.setMessage(getString(R.string.dialogueTitle)+ "\n" + finalPayment);
//                alertDialogBuilder.setPositiveButton("Make Payment",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                Toast.makeText(EnterDetailsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

}