package com.example.airpark.activities.Prelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.airpark.R;
import com.example.airpark.utils.ErrorRemoveInterface;
import com.example.airpark.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;

//Instead of closure, if we need to, could change it to interface
public class LoginActivity extends AppCompatActivity{

    private TextInputEditText emailField;
    private TextInputEditText passwordField;
    private InputValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validator = new InputValidator();

        emailField = findViewById(R.id.editTextTextEmailAddress);
        emailField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { emailField.setError(null); }
        });

        passwordField = findViewById(R.id.editTextTextPassword);
        passwordField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { passwordField.setError(null); }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Editable email = emailField.getText();
                if (!validator.isValidEmail(email.toString())){
                    //error
                    emailField.setError(getString(R.string.invalid_email));
                }

                Editable password = passwordField.getText();
                if (!validator.isValidPassword(password.toString())){
                    //error
                    passwordField.setError(getString(R.string.password_short));
                }
            }
        });
    }
}