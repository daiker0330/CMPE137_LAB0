package com.example.yunfan.cmpe137_lab0;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    int wrongCount = 0;
    int minuteCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox show = (CheckBox)findViewById(R.id.checkBox);
        final EditText user = (EditText)findViewById(R.id.editText);
        final EditText psw = (EditText)findViewById(R.id.editText2);
        Button login = (Button)findViewById(R.id.button);
        final TextView warning= (TextView)findViewById(R.id.textView5);

        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(minuteCount==0){
                    user.setEnabled(true);
                    psw.setEnabled(true);
                    minuteCount--;
                    warning.setText("You can enter right now");
                }
                else if(minuteCount>0) {
                    warning.setText("Too many attempts! Please try again after " + minuteCount + " Seconds");
                    minuteCount--;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    psw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        login.setOnClickListener(new CompoundButton.OnClickListener(){
            @Override
            public void onClick(View v){
                if(user.getText().toString().equals("010814856")){
                    if(psw.getText().toString().equals("CMPE#137")){
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, HelloActivity.class);
                        startActivity(intent);
                    }
                    else if(psw.getText().toString().toUpperCase().equals("CMPE#137")){
                        warning.setText("Try using upper-case!");
                        wrongCount++;
                    }
                    else{
                        warning.setText("Invalid Password");
                        wrongCount++;
                    }
                }
                else{
                    warning.setText("Invalid Username");
                    wrongCount++;
                }
                if(wrongCount==3){
                    user.setEnabled(false);
                    psw.setEnabled(false);
                    wrongCount = 0;
                    minuteCount = 30;
                }
            }
        });
    }
}
