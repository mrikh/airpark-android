package com.example.airpark.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EnterDetailsActivity extends AppCompatActivity {
    private LinearLayout loginContainer;
    private TextInputEditText email, name, carReg, phoneNumber;
    private Button loginBtn, paymentBtn;
    private CheckBox elderlyCheck, carWashCheck;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindUiItems();


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
        paymentBtn = (Button)findViewById(R.id.payment_Btn);
    }

}
