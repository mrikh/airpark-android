package com.example.airpark.activities.Prelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.activities.SearchActivity;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.HelperInterfaces.ErrorRemoveInterface;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{

    private InputValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        findViewById(R.id.loginBackground).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });

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

                NetworkHandler.getInstance().loginUser(email, password, new NetworkingClosure() {
                    @Override
                    public void completion(JSONObject object, String message) {
                        if (object == null){
                            //this will only happen if api fails
                            Toast.makeText(LoginActivity.this, (message == null) ? getText(R.string.something_wrong): message, Toast.LENGTH_LONG).show();
                        }else{
                            try {
                                UserModel current = new UserModel(object.getInt("id"), object.getString("name"), object.getString("email"));
                                UserModel.currentUser = current;
                                navigateToLanding();
                            }catch (Exception e){
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
            }
        });
    }

    public void onClick(View v){

    }

    private void navigateToLanding(){
        Intent myIntent = new Intent(LoginActivity.this, SearchActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
    }
}