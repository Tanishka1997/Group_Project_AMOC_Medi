package com.example.android.medi;

/**
 * Created by Deepanshu on 3/24/2016.
 */
public class MedicalHistory {

    private String symptoms,dateTimeSshow,medicalTest,MedicinesPrescribed;

    public MedicalHistory(String symp, String dateTime, String mediTest, String medicines){
        this.setSymptoms(symp);
        this.setDateTimeShow(dateTime);
        this.setMedicalTest(mediTest);
        this.setMedicinesPrescribed(medicines);
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDateTimeShow() {
        return dateTimeSshow;
    }

    public void setDateTimeShow(String dateTimeSshow) {
        this.dateTimeSshow = dateTimeSshow;
    }

    public String getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(String medicalTest) {
        this.medicalTest = medicalTest;
    }

    public String getMedicinesPrescribed() {
        return MedicinesPrescribed;
    }

    public void setMedicinesPrescribed(String medicinesPrescribed) {
        MedicinesPrescribed = medicinesPrescribed;
    }
}
