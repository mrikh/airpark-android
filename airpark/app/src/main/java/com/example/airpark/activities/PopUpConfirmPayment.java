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
import android.widget.Toast;

import com.example.airpark.R;

import java.text.DecimalFormat;

public class PopUpConfirmPayment {

    private String carparkPrice, discountAmount, carWashPrice, finalPrice, finalPayment;

    public PopUpConfirmPayment(String carparkPrice, String discountAmount, String carWashPrice, String finalPrice){
        this.carparkPrice = carparkPrice;
        this.discountAmount = discountAmount;
        this.carWashPrice = carWashPrice;
        this.finalPrice = finalPrice;
        finalPayment=null;
    }

    public void showPopUp(final View view){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_confirm_booking, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBackground(popupWindow);

        TextView title = popupView.findViewById(R.id.popUp_title);
        title.setText(R.string.popup_title);

        DecimalFormat dFormat = new DecimalFormat("#.00");

        TextView extraChargesText = popupView.findViewById(R.id.popUp_extraCharges);
        extraCharges();
        extraChargesText.setText(finalPayment);

        TextView finalPayment = popupView.findViewById(R.id.popUp_finalAmount);
        finalPayment.setText("Total: €" + finalPrice);

        Button buttonEdit = popupView.findViewById(R.id.popUp_paymentBtn);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(view.getContext(), "Open Card Paymment View", Toast.LENGTH_SHORT).show();

            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void dimBackground(PopupWindow popupWindow){
        View container = (View) popupWindow.getContentView().getParent();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    private void extraCharges(){
        if(discountAmount == null || carWashPrice == null){
            if(discountAmount == null && carWashPrice == null){
                finalPayment = null;
            }else if(discountAmount == null){
                finalPayment = carparkPrice + carWashPrice;
            }else{
                finalPayment = carparkPrice + discountAmount;
            }
        }else{
            finalPayment = carparkPrice + discountAmount + carWashPrice;
        }
    }
}
