package com.calendar;
import java.util.List;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.calendar.Model.Event;

public class Event_Adapter extends ArrayAdapter<Event> {

	List<Event> lstEvent;
	Activity context;

	public Event_Adapter(Activity context, List<Event> lstEvent) {
		super(context, R.layout.item_list_event, lstEvent);
		this.context = context;
		this.lstEvent = lstEvent;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		view = context.getLayoutInflater().inflate(R.layout.item_list_event,
				null, true);
		TextView txtDateEvent = (TextView) view.findViewById(R.id.txtDateEvent);
		TextView txtTitleEvent = (TextView) view
				.findViewById(R.id.txtTitleEvent);

		txtDateEvent.setText(lstEvent.get(position).getDateEvent());
		txtTitleEvent.setText(lstEvent.get(position).getTitleEvent());
		return view;
	}

}
