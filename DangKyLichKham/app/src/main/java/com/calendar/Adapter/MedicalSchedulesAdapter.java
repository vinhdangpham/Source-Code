package com.calendar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calendar.Model.ExamSchedule;
import com.calendar.Model.Medical_Schedules;
import com.calendar.R;

import java.util.ArrayList;

/**
 * Created by nguyenhoang on 5/22/2015.
 */
public class MedicalSchedulesAdapter extends ArrayAdapter<Medical_Schedules> {
    LayoutInflater layoutInflater;
    int layoutResourceId;
    private Context context = null;
    private ArrayList<Medical_Schedules> listSchedules;
    public MedicalSchedulesAdapter(Context context, ArrayList<Medical_Schedules> listSchedules) {
        super(context, 0, listSchedules);
        this.context = context;
        this.listSchedules = listSchedules;
    }

    @Override
    public int getCount() {
        return listSchedules.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.item_list_event, null);
        Medical_Schedules medical_schedules = listSchedules.get(position);
        TextView txtDate=(TextView) convertView.findViewById(R.id.txtDateEvent);
        TextView txtTitle=(TextView) convertView.findViewById(R.id.txtTitleEvent);
        txtDate.setText("Ngày  : "+medical_schedules.getScheduleDate());
        txtTitle.setText(medical_schedules.getScheduleDetail());
        return convertView;
    }
    public void clear() {
        listSchedules.clear();

        notifyDataSetChanged();
    }
}
