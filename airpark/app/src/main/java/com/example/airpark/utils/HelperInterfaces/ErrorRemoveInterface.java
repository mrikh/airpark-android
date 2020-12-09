package com.example.airpark.utils.HelperInterfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 */
public abstract class ErrorRemoveInterface implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        //do nothing
    };

    @Override
    public void afterTextChanged(Editable s){
        //do nothing
    };
}
