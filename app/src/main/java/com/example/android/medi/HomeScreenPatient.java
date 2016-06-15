package com.example.android.medi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HomeScreenPatient extends AppCompatActivity {

    Bundle detailsImported;
    String admNoImportedString,nameImportedString,JSON_STRING;
    TextView welcomePatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_patient);
        welcomePatient = (TextView) findViewById(R.id.welcome_Patient);
        detailsImported = getIntent().getExtras();
        admNoImportedString = detailsImported.getString("admissionNo");
        nameImportedString = detailsImported.getString("name");
        welcomePatient.setText(nameImportedString);
        get_JSON();
    }

    public void get_JSON(){
        new BackgroundTask(this).execute(admNoImportedString);
    }

    public void update_your_details(View view){
        Intent intent = new Intent(this,updatePatientDetails.class);
        intent.putExtra("admissionNo",admNoImportedString);
        startActivity(intent);
    }

    public void logout(View view){
        Intent intent = new Intent(this,loginAsPatient.class);
        startActivity(intent);
    }

    public void check_your_medical_history(View view){

        if(JSON_STRING == null)
        {
            Message.message(this,"No Medical History Available");
        }
        else
        {
            Intent intent = new Intent(this,medicalHistoryPatientHomeScreen.class);
            intent.putExtra("JSON_STRING",JSON_STRING);
            startActivity(intent);
        }

    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String jsonGetMedicalHistoryURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            jsonGetMedicalHistoryURL = "http://172.24.82.176/mediworld/jsonGetMedicalHistory.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String PATIENT_MEDICAL_HISTORY_ADM_NO=params[0];

            try {
                URL url = new URL(jsonGetMedicalHistoryURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("PATIENT_MEDICAL_HISTORY_ADM_NO", "UTF-8") +"="+URLEncoder.encode(PATIENT_MEDICAL_HISTORY_ADM_NO,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null){

                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result){
            JSON_STRING = result;

        }
    }

}
