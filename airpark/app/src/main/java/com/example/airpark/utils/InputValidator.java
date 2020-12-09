package com.example.airpark.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Methods that validate user inputs
 */
public class InputValidator {

    public boolean isValidEmail(String s){

        if (s == null) {return false;}

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public boolean isValidPassword(String s){

        if (s == null) {return false;}

        String stripped = s.trim();
        if (stripped.length() < 8){
            return false;
        }

        return true;
    }

    public boolean isValidName(String s){

        String stripped = s.trim();

        if(stripped.isEmpty() || !stripped.contains(" ") || stripped.contains(".*\\d+.*")){
            return false;
        }
        String firstname = stripped.substring(0, stripped.indexOf(" "));
        String lastname = stripped.substring(stripped.lastIndexOf(" ")+1);

        if(firstname.length() <= 1 || lastname.length() <= 1 ){
            return false;
        }

        return true;
    }

    public boolean isValidTimeToDate(String entryDate, String exitDate, String entryTime, String exitTime) throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //Current Date & Time
        Date current = dateFormat.parse(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "/" + Calendar.getInstance().get(Calendar.YEAR) + " " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":00");

        //If any null params
        if(entryDate.equals("")) { entryDate = "00/00/0000"; }
        if(exitDate.equals("")) { exitDate = "00/00/0000"; }
        if(entryTime.equals("")) { entryTime = "00:00"; }
        if(exitTime.equals("")) { exitTime = "00:00"; }

        //Set entry & exit dates & times
        Date entry = dateFormat.parse(entryDate + " " + entryTime);
        Date exit = dateFormat.parse(exitDate + " " + exitTime);

        //if only one date to validate
        if(entryDate.equals("00/00/0000") || exitDate.equals("00/00/0000") && (entryTime.equals("00:00") && exitTime.equals("00:00"))){
            return true;
        }else if(entryTime.equals("00:00") && !exitDate.equals("00/00/0000") && !exitTime.equals("00:00")) {
            if(exit.compareTo(current) > 0){
                return true;
            }
        }else if(exitTime.equals("00:00") && !entryDate.equals("00/00/0000") && !entryTime.equals("00:00")) {
            if(entry.compareTo(current) > 0){
                return true;
            }
        }else{
            //If only dates to validate
            if(entryTime.equals("00:00") && exitTime.equals("00:00")){
                if(exit.compareTo(entry) >= 0){
                    return true;
                }
            }else if(exit.compareTo(entry) > 0){
                if(entry.compareTo(current) > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isValidPhoneNumber(String s) {
        String number = (s.replace(" ","")).trim();

        final String NUMBER_PATTERN = "\\+?\\d+";
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(number);

        if(number.length() > 15 || number.length() < 7){
            return false;
        }

        return matcher.matches();
    }

    public boolean isValidReg(String s){
        String reg = (s.replace(" ","")).trim();

        if(reg.length() > 10 || reg.length() < 2){
            return false;
        }
        return true;
    }
}

