package com.example.mytremp;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class EditTrempActivity extends Activity {
	EditText name;
	EditText phone;
	EditText address;
	EditText numberToPick;
	EditText description;
	RadioButton male;
	RadioButton female;
	Bundle extras;
	Tremp tremp;
	private ImageButton btnPick;
	private Button dateButton;
	private TextView dateTextView;
	private Button timeButton;
	private TextView timeTextView;
	int _id;
	private Model model;
	
	public static final String TABLE_TREMPS = "TREMPS";
	private static final String TABLE_TREMPS_ARCHIVE = "TREMPS_ARCHIVE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("EB", "onCreate Edit Tremp");
		setContentView(R.layout.activity_edit_tremp);
		extras = getIntent().getExtras();
		_id = extras.getInt("_id");
		Log.d("EB", "_id = "+_id);
		model = Model.getInstance(this);
		tremp = model.getTrempById(_id);


		name = (EditText) findViewById(R.id.editText1);
		phone = (EditText) findViewById(R.id.editText2);
		address = (EditText) findViewById(R.id.editText3);
		numberToPick = (EditText) findViewById(R.id.editText5);
		description = (EditText) findViewById(R.id.editText4);
		male = (RadioButton) findViewById(R.id.radioButton1);
		female = (RadioButton) findViewById(R.id.radioButton2);
		btnPick = (ImageButton) findViewById(R.id.PickBtn);
		dateTextView = (TextView) findViewById(R.id.date_textView3);
		timeTextView = (TextView) findViewById(R.id.time_textView3);



		name.setText(tremp.getName());
		phone.setText((tremp.getPhone()));
		address.setText(tremp.getAddress());
		numberToPick.setText(tremp.getNumberToPick());
		description.setText(tremp.getDescription());
		male.setChecked((tremp.getGender()==1)); 
		female.setChecked((tremp.getGender()==0));
		dateTextView.setText(tremp.getDate());
		timeTextView.setText(tremp.getTime());
	

		Button b = (Button)findViewById(R.id.button21);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("EB",(male.isChecked()?"Male is checked":"Female is checked"));

				if(!validateData()){
					return;
				}

				tremp = new Tremp(_id,
						name.getText().toString(), address.getText().toString(),
						phone.getText().toString(), description.getText().toString(),
						(male.isChecked()?1:0), numberToPick.getText().toString(),
						dateTextView.getText().toString(), timeTextView.getText().toString());

				if(model.editTremp(tremp)){
					Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});

		
		ImageButton dialer_button = (ImageButton)findViewById(R.id.imageButton1);
		dialer_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent callintent = new Intent(Intent.ACTION_CALL);
				String tel = "tel:"+phone.getText().toString();
				callintent.setData(Uri.parse(tel));
				startActivity(callintent);

			}
		});
		
		
		ImageButton sms_button = (ImageButton)findViewById(R.id.imageButton2);
		sms_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String smsto = "smsto:"+phone.getText().toString();
				Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(smsto));         
				sendIntent.putExtra("sms_body", "אני פה"); 
				startActivity(sendIntent); 

			}
		});


		Button b1=(Button)findViewById(R.id.button22);
		//Deleting a Tremp
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				model.deleteTremp(_id);
				Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		
		 btnPick.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
	              String[] strFields = { android.provider.CallLog.Calls._ID,
	                      android.provider.CallLog.Calls.NUMBER,
	                      android.provider.CallLog.Calls.CACHED_NAME, };
	              String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
	              final Cursor cursorCall = getContentResolver().query(
	                      android.provider.CallLog.Calls.CONTENT_URI, strFields,
	                      null, null, strOrder);

	              AlertDialog.Builder builder = new AlertDialog.Builder(EditTrempActivity.this);
	              builder.setTitle("Select recent number");
	              android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialogInterface,
	                          int item) {
	                      cursorCall.moveToPosition(item);
	                      phone.setText(cursorCall.getString(cursorCall
                                 .getColumnIndex(android.provider.CallLog.Calls.NUMBER)));
	                      String contactName = cursorCall.getString(cursorCall
                                  .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
	                      if(contactName == null){
	                    	  Log.d("EB","No Name");
	                    	  contactName = "No Name";
	                      }
	                      Toast.makeText(
	                    		  EditTrempActivity.this, contactName, Toast.LENGTH_SHORT).show();
	                      cursorCall.close();
	                      return;
	                  }
	              };
	              builder.setCursor(cursorCall, listener,
	              android.provider.CallLog.Calls.NUMBER);
	              builder.create().show();
	          }
	      });
		 
		 
		
			dateButton = (Button) findViewById(R.id.dateButton);
			dateButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Calendar c = Calendar.getInstance();
					int mYear = c.get(Calendar.YEAR);
					int mMonth = c.get(Calendar.MONTH);
					int mDay = c.get(Calendar.DAY_OF_MONTH);
					System.out.println("the selected " + mDay);
					DatePickerDialog dialog = new DatePickerDialog(EditTrempActivity.this,
							new mDateSetListener(dateTextView), mYear, mMonth, mDay);
					dialog.show();
				

				}
			});

			
			timeButton = (Button) findViewById(R.id.timeButton);
			timeButton.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            // TODO Auto-generated method stub
		            Calendar mcurrentTime = Calendar.getInstance();
		            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		            int minute = mcurrentTime.get(Calendar.MINUTE);
		            TimePickerDialog mTimePicker;
		            mTimePicker = new TimePickerDialog(EditTrempActivity.this, new TimePickerDialog.OnTimeSetListener() {
		                @Override
		                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
		                	timeTextView.setText( "      "+selectedHour + ":" + selectedMinute);
		                }
		            }, hour, minute, true);//Yes 24 hour time
		            mTimePicker.setTitle("Select Time");
		            mTimePicker.show();

		        }
		    });
		 
	}
	
	
	
	
	
	
	
	

	private boolean validateData(){
		if(name.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Please insert name", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(phone.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Please insert phone number", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(numberToPick.getText().toString().length() == 0){
			numberToPick.setText("0");
			Toast.makeText(getApplicationContext(), "Number to Pick-Up defined to "+
			numberToPick.getText().toString(), Toast.LENGTH_SHORT).show();
		}
		if(address.getText().toString().length() > 50){
			Toast.makeText(getApplicationContext(), "Address is too long", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	


}
