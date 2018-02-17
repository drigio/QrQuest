package com.example.drigio.qrcodescan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Debug;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button scanbtn;
    private Button prevHints;
    private TextView grpCode;
    private TextView textContents;
    private int groupCode = 0;
    private int currentHint = 0;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prevHints = (Button) findViewById(R.id.prevHints);
        prevHints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PreviousHints.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        checkGroupCode();
        initializeScanner();
        showLastKnownHint();

    }

    private void showLastKnownHint() {
        textContents = (TextView)findViewById(R.id.textContents);
        Cursor data = databaseHelper.getDataByRawQuery();
        if(data.getCount() <= 0) {
            textContents.setText("Scan To Show Current Hint");
        }
        else {
            data.moveToLast();
            textContents.setText(data.getString(1));
        }
    }

    void checkGroupCode() {

        //Check For existing Treasure Hunt Group Code
        SharedPreferences prefs = getSharedPreferences("myprefs",MODE_PRIVATE);
        boolean isFirstUsage = prefs.getBoolean("firstusage",true);

        //First Time App is launched
        if(isFirstUsage) {
            Random r = new Random();
            groupCode = r.nextInt(10); //Get a random Group Code
            Log.d("mylog","First Run Group Code is "+groupCode);
            currentHint = 0;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstusage",false);
            editor.putInt("groupcode",groupCode);
            editor.apply();
        }
        else {
            groupCode = prefs.getInt("groupcode",12); //Retrieve the previously set Group Code and if not present return with 12.
            currentHint = prefs.getInt("currenthint",0); //Retrieve the Current hint found by the player and if not present return 0
            Log.d("mylog","Saved Group Code is "+groupCode);
            Log.d("mylog","Current Hint is "+currentHint);
            grpCode = (TextView) findViewById(R.id.grpCode);
            grpCode.setText(String.valueOf(groupCode));

        }
    }


    void initializeScanner() {

        //Initialize The QR Code scanner
        scanbtn = (Button) findViewById(R.id.scanbtn);
        final Activity activity = this;
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    //Check The Output From the scan
    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

            if(result != null){
                //User Cancelled the search
                if(result.getContents() == null){
                    Toast.makeText(this,"You cancelled the scan",Toast.LENGTH_SHORT).show();

                }
                //Data Found
                else {
                    int extractedCode = Character.getNumericValue(result.getContents().charAt(0)); //Check if same Group code present
                    int extractedHint = Character.getNumericValue(result.getContents().charAt(1));
                    Log.d("mylog","The extracted Code is " + extractedCode);
                    Log.d("mylog","The extracted Hint number is " + extractedHint);
                    if(extractedCode == groupCode) {
                        if (currentHint == extractedHint - 1) {
                            textContents = (TextView) findViewById(R.id.textContents);
                            String content = result.getContents().substring(2).trim();
                            textContents.setText(content);
                            SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("currenthint", extractedHint);
                            editor.apply();
                            currentHint = extractedHint;

                            //Add the hint to database
                            AddData(extractedHint,content);

                        } else if (extractedHint > currentHint + 1) {
                            Toast.makeText(getApplicationContext(), "NO CHEATING", Toast.LENGTH_LONG).show();
                        } else if (extractedHint <= currentHint) {
                            Toast.makeText(getApplicationContext(), "Hint Already Scanned", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong Hint",Toast.LENGTH_LONG).show();
                    }
                }
            }
            //Loopback and continue Scanning
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        //Method to add to database
        private void AddData(int hintno, String hint) {
            boolean insertData = databaseHelper.addData(hintno,hint);
            if(insertData) {
                Toast.makeText(this,"Hint SuccessFully Added",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Hint Not Added",Toast.LENGTH_LONG).show();
            }
        }

}

