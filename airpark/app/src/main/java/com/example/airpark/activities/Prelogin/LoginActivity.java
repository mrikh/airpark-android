package com.example.airpark.activities.Prelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.activities.EnterDetailsActivity;
import com.example.airpark.activities.LandingSearchActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.HelperInterfaces.ErrorRemoveInterface;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.example.airpark.utils.Utilities;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Login screen
 */
public class LoginActivity extends AppCompatActivity{

    private InputValidator validator;
    private ProgressBar progressBar;
    private BookingTicket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        validator = new InputValidator();

        TextInputEditText emailField = findViewById(R.id.editTextTextEmailAddress);
        emailField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { emailField.setError(null); }
        });

        TextInputEditText passwordField = findViewById(R.id.editTextTextPassword);
        passwordField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { passwordField.setError(null); }
        });

        TextView signUp = findViewById(R.id.textView5);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(myIntent);
            }
        });

        TextView exploreTextView = findViewById(R.id.exploreTextView);
        exploreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLanding();
            }
        });

        if(getIntent().getExtras() != null){
            ticket = (BookingTicket)getIntent().getSerializableExtra("ticket");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView orText = findViewById(R.id.textView4);
            orText.setVisibility(View.GONE);
            exploreTextView.setVisibility(View.GONE);
        }

        findViewById(R.id.loginBackground).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                if (!validator.isValidEmail(email)){
                    //error
                    emailField.setError(getString(R.string.invalid_email));
                    return;
                }

                String password = passwordField.getText().toString().trim();
                if (!validator.isValidPassword(password)){
                    //error
                    passwordField.setError(getString(R.string.password_short));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                NetworkHandler.getInstance().loginUser(email, password, new NetworkingClosure() {
                    @Override
                    public void completion(JSONObject object, String message) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (object == null){
                            //this will only happen if api fails
                            Toast.makeText(LoginActivity.this, (message == null) ? getText(R.string.something_wrong): message, Toast.LENGTH_LONG).show();
                        }else{
                            try {
                                UserModel current = new UserModel(object.getInt("id"), object.getString("name"), object.getString("email"));
                                UserModel.currentUser = current;
                                Utilities.getInstance().saveJsonObject(object, getApplicationContext());

                                if(ticket != null){
                                    navigateToEnterDetailsScreen();
                                }else{
                                    navigateToLanding();
                                }

                            }catch (Exception e){
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
            }
        });
    }

    public void onClick(View v){ }

    private void navigateToLanding(){
        Intent myIntent = new Intent(LoginActivity.this, LandingSearchActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
    }

    private void navigateToEnterDetailsScreen(){
        Intent myIntent = new Intent(LoginActivity.this, EnterDetailsActivity.class);
        myIntent.putExtra("ticket", ticket);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}