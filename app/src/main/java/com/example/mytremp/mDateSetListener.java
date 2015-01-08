package com.example.mytremp;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;


class mDateSetListener implements DatePickerDialog.OnDateSetListener {
	
	private TextView dateTextView;

	public mDateSetListener(TextView dateTextView){
		this.dateTextView = dateTextView;
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
		// getCalender();
		int mYear = year;
		int mMonth = monthOfYear;
		int mDay = dayOfMonth;

		dateTextView.setText(new StringBuilder()
		// Month is 0 based so add 1
		.append(mMonth + 1).append("/").append(mDay).append("/")
		.append(mYear).append(" "));
		System.out.println(dateTextView.getText().toString());

	}

}
