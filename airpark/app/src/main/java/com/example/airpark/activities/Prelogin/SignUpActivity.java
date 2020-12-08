package com.example.airpark.activities.Prelogin;

import android.content.Intent;
import android.os.Bundle;

import com.example.airpark.activities.LandingSearchActivity;
import com.example.airpark.models.UserModel;
import com.example.airpark.utils.HelperInterfaces.ErrorRemoveInterface;
import com.example.airpark.utils.HelperInterfaces.NetworkingClosure;
import com.example.airpark.utils.InputValidator;
import com.example.airpark.utils.Networking.NetworkHandler;
import com.example.airpark.utils.Utilities;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airpark.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Sign up screen
 */
public class SignUpActivity extends AppCompatActivity {

    private InputValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        validator = new InputValidator();

        TextInputEditText emailField = findViewById(R.id.editTextEmail);
        emailField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { emailField.setError(null); }
        });

        TextInputEditText passwordField = findViewById(R.id.editTextPassword);
        passwordField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { passwordField.setError(null); }
        });

        TextInputEditText nameField = findViewById(R.id.editTextName);
        nameField.addTextChangedListener(new ErrorRemoveInterface() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { nameField.setError(null); }
        });

        findViewById(R.id.signUpBackground).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });

        TextView note = findViewById(R.id.noteSignUpTextView);
        String noteString = getString(R.string.note);
        String textString = getString(R.string.agree_terms);
        String finalString = noteString + " " + textString;
        SpannableStringBuilder str = new SpannableStringBuilder(finalString);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, noteString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        note.setText(str);

        note.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);

        Button signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener(){
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

                String name = nameField.getText().toString().trim();
                if (!validator.isValidName(name)){
                    //error
                    nameField.setError(getString(R.string.name_error));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                NetworkHandler.getInstance().signUp(email, password, name, new NetworkingClosure() {
                    @Override
                    public void completion(JSONObject object, String message) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (object == null){
                            //this will only happen if api fails
                            Toast.makeText(SignUpActivity.this, (message == null) ? getText(R.string.something_wrong): message, Toast.LENGTH_LONG).show();
                        }else{
                            try {
                                UserModel current = new UserModel(object.getInt("id"), object.getString("name"), object.getString("email"));
                                UserModel.currentUser = current;
                                Utilities.getInstance().saveJsonObject(object, getApplicationContext());
                                //go to landing
                                Intent myIntent = new Intent(SignUpActivity.this, LandingSearchActivity.class);
                                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(myIntent);
                            }catch (Exception e){
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }

    public void onClick(View v){

    }
}