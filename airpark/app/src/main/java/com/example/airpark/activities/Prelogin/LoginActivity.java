package com.example.airpark.activities.Prelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.airpark.R;
import com.example.airpark.utils.HelperInterfaces.ErrorRemoveInterface;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.net.URLEncoder;

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

                NetworkHandler.getInstance().loginUser(email, password, getApplicationContext(), new NetworkingClosure() {
                    @Override
                    public void completion(JSONObject object, String message) {
                        if (object == null){
                            //this will only happen if api fails
                            Toast.makeText(LoginActivity.this, (message == null) ? getText(R.string.something_wrong):message, Toast.LENGTH_LONG).show();
                        }else{
                            System.out.println(object);
                        }
                    }
                });
            }
        });
    }
}