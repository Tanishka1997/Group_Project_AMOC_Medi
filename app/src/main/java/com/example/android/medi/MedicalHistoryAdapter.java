package com.example.android.medi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepanshu on 3/24/2016.
 */
public class MedicalHistoryAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public MedicalHistoryAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(MedicalHistory object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        MedicalHistoryHolder medicalHistoryHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_medical_history,parent,false);
            medicalHistoryHolder = new  MedicalHistoryHolder();
            medicalHistoryHolder.showSymptoms = (TextView) row.findViewById(R.id.symptoms_patient_home_screen);
            medicalHistoryHolder.showDateTimeShow = (TextView) row.findViewById(R.id.date_time_patient_home_screen);
            medicalHistoryHolder.showMedicalTest = (TextView) row.findViewById(R.id.medical_test_patient_home_screen);
            medicalHistoryHolder.showMedicinesPrescribed = (TextView) row.findViewById(R.id.medicines_prescribed_patient_home_screen);
            row.setTag(medicalHistoryHolder);


        }

        else
        {
            medicalHistoryHolder = (MedicalHistoryHolder) row.getTag();
        }

        MedicalHistory medicalHistory = (MedicalHistory) getItem(position);
        medicalHistoryHolder.showSymptoms.setText(medicalHistory.getSymptoms());
        medicalHistoryHolder.showDateTimeShow.setText(medicalHistory.getDateTimeShow());
        medicalHistoryHolder.showMedicalTest.setText(medicalHistory.getMedicalTest());
        medicalHistoryHolder.showMedicinesPrescribed.setText(medicalHistory.getMedicinesPrescribed());

        return row;
    }

    static class MedicalHistoryHolder
    {
        TextView showSymptoms, showDateTimeShow, showMedicalTest, showMedicinesPrescribed;
    }
}
