package com.example.airpark.activities;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.utils.HelperInterfaces.Callback;

import java.text.DecimalFormat;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Final payment amount with extra costs or discounts as a pop-up window
 */
public class PopUpConfirmPayment extends AppCompatActivity {

    private String finalPayment;
    private String[] discounts;
    private Callback callback;
    private BookingTicket ticket;

    public PopUpConfirmPayment(BookingTicket ticket, float finalPayment, String[] discounts, Callback callBack){
        DecimalFormat df = new DecimalFormat("#.00");
        this.finalPayment = df.format(finalPayment);
        this.discounts = discounts;
        this.callback = callBack;
        this.ticket = ticket;
    }

    public void showPopUp(final View view){

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_confirm_booking, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Center pop-up window
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBackground(popupWindow);

        TextView title = popupView.findViewById(R.id.popUp_title);
        title.setText(R.string.popup_title);

        TextView extraChargesText = popupView.findViewById(R.id.popUp_extraCharges);
        String chargesString = "";
        for(int i = 0; i<discounts.length; i++){
            chargesString += discounts[i];
            if (i != discounts.length - 1){
                chargesString += ", ";
            }
        }
        if(discounts.length > 0) {
            extraChargesText.setText(view.getContext().getString(R.string.discounts_applied) + ":\n" + chargesString);
        }

        String totalPrice = this.finalPayment;

        TextView finalPayment = popupView.findViewById(R.id.popUp_finalAmount);
        finalPayment.setText(view.getContext().getString(R.string.total_price) + totalPrice);

        Button confirm = popupView.findViewById(R.id.popUp_paymentBtn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                callback.onComplete();
            }
        });

        Button cancel = popupView.findViewById(R.id.popUp_cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    /**
     * Dim background behind pop-up
     * @param popupWindow
     */
    private void dimBackground(PopupWindow popupWindow){
        View container = (View) popupWindow.getContentView().getParent();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }
}
