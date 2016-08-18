package com.magee.project1_tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener{

    private EditText fahrenheitTextEdit;
    private TextView celciusTextView;
    private String tempString = "";
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_thermometer);

        fahrenheitTextEdit = (EditText) findViewById(R.id.fahrenheitTextEdit);
        celciusTextView = (TextView) findViewById(R.id.celsiusTextView);

        fahrenheitTextEdit.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {

        tempString = fahrenheitTextEdit.getText().toString();
        float temp;

        if(tempString.equals("")){
            temp = 32;
        }else {
            temp = Float.parseFloat(tempString);
        }

        //calculate temperature
        //c = (f-32) * 5/9
        float celsius = (temp -32) * 5/9;

        String finalTemp = String.format("%.02f", celsius);

        celciusTextView.setText(finalTemp);
    }

    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitTemp", tempString);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        tempString = savedValues.getString("fahrenheitTemp", "");

        fahrenheitTextEdit.setText(tempString);

        calculateAndDisplay();
    }


}
