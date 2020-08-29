/*Dev-Sahil Hemnani*/

package com.example.calculator;

/*All the imports required in the code*/
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Running Class which will be executed when the app will start
public class MainActivity extends AppCompatActivity {

    //Field Variables of the widgets are defined
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private TextView errorMsg;

    /*
    Variables to hold operands and type of calculations
    * operand1 will hold the value of result(which is a EditText widget where Focusable=false)
    * operand2 will hold the value of new_Number(Which is a EditText widget and where all the entries
    *       from the keyboard will be saved i.e. focusable is True)
    * pendingOperation will hold the value of operation which is currently going on refer to the text
    *       of the TextView operation in the layout\activity_main.xml
    */
    private Double operand1=null;
    private Double operand2=null;
    private String pendingOperation="=";

    /*
    Variables for saving and restoring the values(Variables and TextView) when the Activity will be
    *       destroyed and then Recreated this used onSaveInstanceState() and onRestoreInstanceState()
    *       methods and act as key to store the state and information of operand1 and operation TextView
    *       in the bundle
    */
    private String SPACE1="SPACE_ALLOCATION";
    private String SPACE2="SPACE_ALLOCATION_2";

    /*
    OnCreate() method creates the project and enters the project with null bundle if there is no
    *       savedInstanceState i.e. savedInstanceState=null otherwise it has the information of the other
    *       state which was saved and restored
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the first/main activity will be activity_main.xml
        setContentView(R.layout.activity_main);

        /*
       result is assigned to widget with id = result with the help of findViewById() method
       *newNumber is assigned to widget with id = newNumber with the help of findViewById() method
       *displayOperation is assigned to widget with id = operation with the help of findViewById() method
       *errorMsg is assigned to widget with id = errorInfo to show the text messages on the screen
       */
        result= findViewById(R.id.result);
        newNumber=findViewById(R.id.newNumber);
        displayOperation=findViewById(R.id.operations);
        errorMsg=findViewById(R.id.errorInfo);


        /*
        Buttons are assigned the button(i) variables with help of the findViewById() method
        *       these are general buttons which will help in the input of numbers and dot(.)
        */
        Button button0 = findViewById(R.id.button0);
            Button button1 = findViewById(R.id.button1);
            Button button2 = findViewById(R.id.button2);
            Button button3 = findViewById(R.id.button3);
            Button button4 = findViewById(R.id.button4);
            Button button5 = findViewById(R.id.button5);
            Button button6 = findViewById(R.id.button6);
            Button button7 = findViewById(R.id.button7);
            Button button8 = findViewById(R.id.button8);
            Button button9 = findViewById(R.id.button9);
            Button buttonDot = findViewById(R.id.buttonDot);
        /*
        Buttons are assigned to the variables with help of the findViewById() method
        *       these are operation buttons which will help in performing the operation
        */

            Button buttonPlus = findViewById(R.id.buttonPlus);
            Button buttonMinus = findViewById(R.id.buttonMinus);
            Button buttonMultiply = findViewById(R.id.buttonMultiply);
            Button buttonDivide = findViewById(R.id.buttonDivide);
            Button buttonEquals = findViewById(R.id.buttonEquals);
        /*
        Buttons are assigned the button variables with help of the findViewById() method
        *       these are special purpose buttons which will help in clearing the screen partially and completely
        *       buttonClearAll will the clear everything
        *       buttonClear will only one digit from the newButton
        */
            Button buttonClearAll = findViewById(R.id.clearAll);
            Button buttonClear = findViewById(R.id.buttonClear);
        /*
        * Creating a new View.OnClickListener for ClearAll Method to to perform te desired operation
        */
        View.OnClickListener clearListener =new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(result.getText().toString().equals("") && newNumber.getText().toString().equals("")){
                        errorMsg.setText("Already cleared!");
                    }else{
                        result.setText("");
                        newNumber.setText("");
                        displayOperation.setText("");
                        operand1=null;
                        operand2=null;
                        errorMsg.setText("Cleared");
                    }
                }
            };
        //setting the onClickListener for ClearAll widget
            buttonClearAll.setOnClickListener(clearListener);
        /*
         * Creating a new View.OnClickListener for buttonClear Method to to perform te desired operation
         */
            View.OnClickListener clear = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s=newNumber.getText().toString();
                    int length = s.length();
                    if(length!=0){
                        newNumber.setText(s.substring(0,length-1));
                    }else{
                        errorMsg.setText("Already cleared!");
                    }
                }
            };
        //setting the onClickListener for ClearAll widget
        buttonClear.setOnClickListener(clear);
        /*
         * Creating a new View.OnClickListener for general purpose buttons to perform the task of
         *      appending the text to newNumber method-------also it does the work of clearing Screen
         *      for the errorMsg TextView.
         */
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorMsg.setText("");
                    Button b = (Button) view;
                    newNumber.append(b.getText().toString());
                }
            };

            /*
            * Setting up the OnClickListeners for the General purpose buttons
             */
            button0.setOnClickListener(listener);
            button1.setOnClickListener(listener);
            button2.setOnClickListener(listener);
            button3.setOnClickListener(listener);
            button4.setOnClickListener(listener);
            button5.setOnClickListener(listener);
            button6.setOnClickListener(listener);
            button7.setOnClickListener(listener);
            button8.setOnClickListener(listener);
            button9.setOnClickListener(listener);
            buttonDot.setOnClickListener(listener);


        /*
         *Creating the OnClickListener for operations button--------lso it does the work of
         *     clearing Screen
         */
            View.OnClickListener opListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorMsg.setText("");
                    Button b = (Button) view;
                    String op = b.getText().toString();
                    String value = newNumber.getText().toString();
                    try{
                        Double newValue =Double.valueOf(value);
                        performOperation(newValue,op);
                    }catch(NumberFormatException e){
                        if(newNumber.getText().toString().equals(".")){
                            errorMsg.setText(". is not a number");
                            newNumber.setText("");
                        }
                        else{
                            errorMsg.setText("Please enter a number");
                        }
                    }
                    pendingOperation = op;
                    displayOperation.setText(pendingOperation);
                }
            };
            //Setting up the OnClickListener for the operation purpose buttons
            buttonEquals.setOnClickListener(opListener);
            buttonDivide.setOnClickListener(opListener);
            buttonMultiply.setOnClickListener(opListener);
            buttonPlus.setOnClickListener(opListener);
            buttonMinus.setOnClickListener(opListener);

        }
/*
* Takes the newValue and do the operation with the result stored variable with the help of provide
*       operation and then sets the text of result to the final result of the operation and clears the
*       newNumber widget
 */
        private void performOperation(Double newValue, String operation) {
            if (operand1 == null) {
                operand1=newValue;
            }else{
                operand2=newValue;
                if (pendingOperation.equals("=")){
                    pendingOperation=operation;
                }
                switch(pendingOperation){
                    case "+":
                        operand1+=operand2;
                        break;
                    case "-":
                        operand1-=operand2;
                        break;
                    case "*":
                        operand1*=operand2;
                        break;
                    case "/":
                        if (operand2!=0){
                            operand1/=operand2;
                        }else{
                            operand1=0.0;
                            errorMsg.setText("Division by 0  not possible");
                        }
                }
            }
            result.setText(operand1.toString());
            newNumber.setText("");
        }
/*
* onSaveInstanceState method which will helps in storing the variables and textView state in the bundle
*       with the help of key and so that we can restore it in the onRestoreInstanceState or OnCreate
*          method when the activity is destroyed ad recreated
 */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SPACE1, pendingOperation);
        if (operand1!=null){
            outState.putDouble(SPACE2, operand1);
        }
        super.onSaveInstanceState(outState);
    }
/*
* onRestoreInstanceState method will help in restoring the value form the bundle
 */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation=savedInstanceState.getString(SPACE1);
        operand1=savedInstanceState.getDouble(SPACE2);
        displayOperation.setText(pendingOperation);

    }
}



