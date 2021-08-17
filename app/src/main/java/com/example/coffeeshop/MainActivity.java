package com.example.coffeeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    public void composeEmail(String[] address, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void submitOrder(View view) {
        int price = calculatePrice();
        String[] address = {"java_coffee_shop_app_by_grushevskyi@gmail.com"};
        String subject = "New coffee order";
        String body = createOrderSummary(price,quantity,view);
        composeEmail(address, subject, body);
    }

    public void previewOrder(View view) {
        int price = calculatePrice();
        displayMessage(createOrderSummary(price,quantity,view));
    }

    public boolean onCheckBox1Clicked(View view){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        return whippedCreamCheckBox.isChecked();
    }

    public boolean onCheckBox2Clicked(View view){
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        return chocolateCheckBox.isChecked();
    }

    public void increase(View view) {

        if (quantity <= 20){
            quantity = ++quantity;
        }else {showTooManyCupsToast();}

        displayQuantity(quantity);
    }

    public void decrease(View view) {

        if (quantity <= 1){
            showTooLittleCupsToast();
        }else {quantity = --quantity;}

        displayQuantity(quantity);
    }


    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + numberOfCoffees);
    }



    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.orderSummaryTextView);
        orderSummaryTextView.setText(message);
    }


    private int calculatePrice() {
        int toppingPrice = 0;

        if (onCheckBox1Clicked(findViewById(R.id.whippedCreamCheckBox)) && !onCheckBox2Clicked(findViewById(R.id.chocolateCheckBox))) toppingPrice = 1;
        else if (!onCheckBox1Clicked(findViewById(R.id.whippedCreamCheckBox)) && onCheckBox2Clicked(findViewById(R.id.chocolateCheckBox))) toppingPrice = 2;
        else if (onCheckBox1Clicked(findViewById(R.id.whippedCreamCheckBox)) && onCheckBox2Clicked(findViewById(R.id.chocolateCheckBox))) toppingPrice = 3;

        return quantity * (5 + toppingPrice);
    }

    private String createOrderSummary(int price, int quantity, View view) {
        EditText nameEditText = findViewById(R.id.editTextPersonName);
        EditText addressEditText = findViewById(R.id.editTextPersonAddress);
        String creamStatus = "";
        String chocoStatus = "";

        if (onCheckBox1Clicked(view)) {
            creamStatus = "Yes!";
        } else if (!onCheckBox1Clicked(view)) {
            creamStatus = "Nope.";
        }
        if (onCheckBox2Clicked(view)) {
            chocoStatus = "Yes!";
        } else if (!onCheckBox2Clicked(view)) {
            chocoStatus = "Nope.";
        }

        String message = "Name: " + nameEditText.getText().toString();
        message += "\nAddress: " + addressEditText.getText().toString();
        message += "\nWhipped Cream? " + creamStatus;
        message += "\nChocolate? " + chocoStatus;
        message += "\nQuantity: " + quantity;
        message += "\nPrice: " + price + "€";

        return message;
    }

    public void showTooManyCupsToast() {
        Toast tooManyCupsToast = Toast.makeText(getApplicationContext(),
                "You cannot order so much coffee!", Toast.LENGTH_SHORT);
        tooManyCupsToast.show();
    }

    public void showTooLittleCupsToast() {
        Toast tooLittleCupsToast = Toast.makeText(getApplicationContext(),
                "Nope.", Toast.LENGTH_SHORT);
        tooLittleCupsToast.show();
    }
}