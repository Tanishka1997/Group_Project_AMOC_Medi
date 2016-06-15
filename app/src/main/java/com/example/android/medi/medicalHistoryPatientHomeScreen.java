package com.example.android.medi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class medicalHistoryPatientHomeScreen extends AppCompatActivity {

    String JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    MedicalHistoryAdapter medicalHistoryAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history_patient_home_screen);

        medicalHistoryAdapter = new MedicalHistoryAdapter(this,R.layout.layout_medical_history);
        listView = (ListView) findViewById(R.id.listViewMedicalHistoryPatientHomeScreen);
        listView.setAdapter(medicalHistoryAdapter);

        JSON_STRING = getIntent().getExtras().getString("JSON_STRING");
        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonArray = jsonObject.getJSONArray("Medical_History_From_Server");
            int count = 0;
            String symp, dateTime, mediTest, medicines;

            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                symp = JO.getString("Symptoms");
                dateTime = JO.getString("Date_Time_Show");
                mediTest = JO.getString("Medical_Test");
                medicines = JO.getString("Medicines_Prescribed");

                MedicalHistory medicalHistory = new MedicalHistory(symp,dateTime,mediTest,medicines);
                medicalHistoryAdapter.add(medicalHistory);
                count++;

            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
