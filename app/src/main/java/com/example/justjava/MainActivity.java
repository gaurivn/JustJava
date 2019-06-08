package com.example.justjava;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity=0;


    /** Increment method*/
    public void increment(View view){
        if(quantity==100) {
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    /**Decrement method*/
    public void decrement(View view){
        if(quantity==0){
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){

       EditText nameField = (EditText)findViewById(R.id.name_field);
       Editable nameEditable = nameField.getText();
       String name = nameEditable.toString();
        Log.v("MainActivity", "Name: " + name);

       /** If user wants whipped cream topping*/
       CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
       boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

       /** If user wants chocolate topping*/
       CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
       boolean hasChocolate = chocolateCheckbox.isChecked();

       /** Calculate price*/
       int price = calculatePrice(hasWhippedCream,hasChocolate);

        /** Display order summary on screen*/
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);


        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(message);
        }

       /**String priceMessage="Total: $" + price + "\nThank you!";
       displayMessage(priceMessage);


    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        /** Price of only 1 cup of coffee*/
        int basePrice = 5;

        /** Add $1 for whipped cream topping*/
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        /** Add $2 for chocolate topping*/
        if(addChocolate){
            basePrice = basePrice + 2;
        }

        /** Total price*/
        return quantity*basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number){
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

   // private void displayMessage(String message){
     //   TextView orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
    //    orderSummaryTextView.setText(message);
    //}

}
