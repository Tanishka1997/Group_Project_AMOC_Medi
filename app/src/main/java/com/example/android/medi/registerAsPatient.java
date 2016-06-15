package com.example.android.medi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerAsPatient extends AppCompatActivity {

    TextInputEditText admNo,name,password,contact,email,dob,height,weight,metabolicDisorders;
    RadioGroup genderRadioGroup;
    RadioButton gender;
    Spinner spinner;
    String[] bloodGroupList = {"A(+ve)", "A(-ve)", "B(+ve)", "B(-ve)", "AB(+ve)", "AB(-ve)", "O(+ve)", "O(-ve)"};
    boolean isDataValid = false;
    DateValidator dateValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_patient);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dateValidator = new DateValidator();
        admNo = (TextInputEditText) findViewById(R.id.adm_no_register);
        name = (TextInputEditText) findViewById(R.id.name_register);
        password = (TextInputEditText) findViewById(R.id.password_register);
        contact = (TextInputEditText) findViewById(R.id.contact_register);
        email = (TextInputEditText) findViewById(R.id.email_register);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        dob = (TextInputEditText) findViewById(R.id.dob_register);
        spinner = (Spinner) findViewById(R.id.bloodGroup_register);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Message.message(registerAsPatient.this,"Select Blood Group");
            }
        });
        height = (TextInputEditText) findViewById(R.id.height_register);
        weight = (TextInputEditText) findViewById(R.id.weight_register);
        metabolicDisorders = (TextInputEditText) findViewById(R.id.metabolicDisorders_register);
    }

    public void radioButtonRegister(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_register:
                if (checked)
                    gender = (RadioButton) findViewById(R.id.male_register);
                break;
            case R.id.female_register:
                if (checked)
                    gender = (RadioButton) findViewById(R.id.female_register);
                break;
        }
    }

    private boolean validateDetails() {

        boolean validateData = true;
        // Reset errors.
        admNo.setError(null);
        name.setError(null);
        password.setError(null);
        contact.setError(null);
        email.setError(null);
        dob.setError(null);
        height.setError(null);
        weight.setError(null);
        metabolicDisorders.setError(null);



        // Store values at the time of the login attempt.
        String admNoString = admNo.getText().toString();
        String nameString = name.getText().toString();
        String passwordString = password.getText().toString();
        String contactString = contact.getText().toString();
        String emailString = email.getText().toString();
        String dobString = dob.getText().toString();
        String heightString = height.getText().toString();
        String weightString = weight.getText().toString();
        String metabolicDisordersString = metabolicDisorders.getText().toString();



        View focusView = null;

        if (TextUtils.isEmpty(metabolicDisordersString)) {
            metabolicDisorders.setError("This field is required");
            validateData = false;
            focusView = metabolicDisorders;
        }

        if (TextUtils.isEmpty(weightString)) {
            weight.setError("This field is required");
            validateData = false;
            focusView = weight;
        }

        if (TextUtils.isEmpty(heightString)) {
            height.setError("This field is required");
            validateData = false;
            focusView = height;
        }

        if (TextUtils.isEmpty(dobString)) {
            dob.setError("This field is required");
            validateData = false;
            focusView = dob;
        }
        else if(!dateValidator.validate(dobString)){
            dob.setError("Enter a valid date");
            validateData = false;
            focusView = dob;
        }



        // Check for a valid email address.
        if (TextUtils.isEmpty(emailString)) {
            email.setError("This field is required");
            validateData = false;
            focusView = email;
        } else if (!isEmailValid(emailString)) {
            email.setError("This email address is invalid");
            validateData = false;
            focusView = email;
        }

        // Check for a valid Mobile no, if the user entered one.
        if (TextUtils.isEmpty(contactString)) {
            contact.setError("This field is required");
            validateData = false;
            focusView = contact;
        } else if (!isContactValid(contactString)) {
            contact.setError("This mobile no is invalid");
            validateData = false;
            focusView = contact;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordString)) {
            password.setError("This field is required");
            validateData = false;
            focusView = password;
        } else if (!isPasswordValid(passwordString)) {
            password.setError("This password  is too short");
            validateData = false;
            focusView = password;
        }

        if (TextUtils.isEmpty(nameString)) {
            name.setError("This field is required");
            validateData = false;
            focusView = name;
        }else if(!isNameValid(nameString)){
            name.setError("Make sure you have typed  Name correctly");
            validateData = false;
            focusView = name;
        }

        // Check for a valid Admission no, if the user entered one.
        if (TextUtils.isEmpty(admNoString)) {
            admNo.setError("This field is required");
            validateData = false;
            focusView = admNo;
        } else if (!isAdmissionNoValid(admNoString)) {
            admNo.setError("This admission no is invalid");
            validateData = false;
            focusView = admNo;
        }

        if(isRadioButtonChecked()==false){
            Toast.makeText(this, "Select Male or Female", Toast.LENGTH_SHORT).show();
            validateData = false;
        }

        if (validateData==false) {
            focusView.requestFocus();
        } else {
            Toast.makeText(this, "Details are valid", Toast.LENGTH_SHORT).show();
        }

        return validateData;
    }
    private boolean isRadioButtonChecked() {


        boolean gender_flag = false;
        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {

        } else {
            gender = (RadioButton) findViewById(genderRadioGroup.getCheckedRadioButtonId());
            gender_flag = true;
        }

        return gender_flag;
    }

    private boolean isAdmissionNoValid(String adm_no) {
        return (adm_no.length() == 8);
    }

    private boolean isNameValid(String adm_no) {

        boolean isValid = false;

        CharSequence string = adm_no;

        Pattern pattern = Pattern.compile("^[a-zA-z]+([ '-][a-zA-Z]+)*$");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private boolean isEmailValid(String email) {

        boolean isValid = false;

        CharSequence emailString = email;

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(emailString);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private boolean isContactValid(String mobile_no) {
        return (mobile_no.length() == 10);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    public void cancel(View view){
        finish();
    }

    public void reset(View view){
        admNo.setText("");
        name.setText("");
        password.setText("");
        contact.setText("");
        email.setText("");
        dob.setText("");
        height.setText("");
        weight.setText("");
        metabolicDisorders.setText("");

        admNo.setError(null);
        name.setError(null);
        password.setError(null);
        contact.setError(null);
        email.setError(null);
        dob.setError(null);
        height.setError(null);
        weight.setError(null);
        metabolicDisorders.setError(null);
    }

    public void submitAsPatient(View view){

        isDataValid = validateDetails();
        if (isDataValid == true) {
            String admNoString = admNo.getText().toString();
            String nameString = name.getText().toString();
            String passwordString = password.getText().toString();
            String contactString = contact.getText().toString();
            String emailString = email.getText().toString();
            String genderString = null;
            try {
                genderString = gender.getText().toString();
            } catch (Exception e) {
                Toast.makeText(this, "Select Male or Female", Toast.LENGTH_SHORT).show();
            }
            String dobString = dob.getText().toString();
            String bloodGroupString = (String) spinner.getSelectedItem();
            String heightString = height.getText().toString();
            String weightString = weight.getText().toString();
            String metabolicDisordersString = metabolicDisorders.getText().toString();

            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(admNoString, nameString, passwordString, contactString, emailString, genderString, dobString, bloodGroupString, heightString, weightString, metabolicDisordersString);
            finish();
        }
    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String registerAsPatientURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            registerAsPatientURL = "http://172.24.82.176/mediworld/registerAsPatient.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String PATIENT_ADM_NO=params[0];
            String PATIENT_NAME=params[1];
            String PATIENT_PASSWORD=params[2];
            String PATIENT_CONTACT_NO=params[3];
            String PATIENT_EMAIL=params[4];
            String PATIENT_GENDER=params[5];
            String PATIENT_DOB=params[6];
            String PATIENT_BLOOD_GROUP=params[7];
            String PATIENT_HEIGHT=params[8];
            String PATIENT_WEIGHT=params[9];
            String PATIENT_METABOLIC_DISORDERS=params[10];
                try {
                    URL url = new URL(registerAsPatientURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = null;
                    data = URLEncoder.encode("PATIENT_ADM_NO", "UTF-8") +"="+URLEncoder.encode(PATIENT_ADM_NO,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_NAME","UTF-8") +"="+URLEncoder.encode(PATIENT_NAME,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_PASSWORD", "UTF-8") +"="+URLEncoder.encode(PATIENT_PASSWORD,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_CONTACT_NO","UTF-8") +"="+URLEncoder.encode(PATIENT_CONTACT_NO,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_EMAIL", "UTF-8") +"="+URLEncoder.encode(PATIENT_EMAIL,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_GENDER","UTF-8") +"="+URLEncoder.encode(PATIENT_GENDER,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_DOB", "UTF-8") +"="+URLEncoder.encode(PATIENT_DOB,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_BLOOD_GROUP","UTF-8") +"="+URLEncoder.encode(PATIENT_BLOOD_GROUP,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_HEIGHT", "UTF-8") +"="+URLEncoder.encode(PATIENT_HEIGHT,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_WEIGHT","UTF-8") +"="+URLEncoder.encode(PATIENT_WEIGHT,"UTF-8")+"&"+
                                        URLEncoder.encode("PATIENT_METABOLIC_DISORDERS","UTF-8") +"="+URLEncoder.encode(PATIENT_METABOLIC_DISORDERS,"UTF-8");


                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String response = "";
                    response = bufferedReader.readLine();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return "Not Registered!!!";
        }

        @Override
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result){
            Message.message(context,result);
        }
    }
}
