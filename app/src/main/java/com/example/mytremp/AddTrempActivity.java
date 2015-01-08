package com.example.mytremp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTrempActivity extends Activity {

	private EditText name;
	private EditText phone;
	private EditText address;
	private EditText numberToPick;
	private EditText description;
	private RadioButton male;
	private RadioButton female;
	private ImageButton addNumberButt;
	private ImageButton btnPick;
	private Button dateButton;
	private TextView dateTextView;
	private Button timeButton;
	private TextView timeTextView;
	
	
	public static final String TABLE_TREMPS = "TREMPS";
	private static final String TABLE_TREMPS_ARCHIVE = "TREMPS_ARCHIVE";
	
	Tremp tremp;
	private Model model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tremp);
		model = Model.getInstance(this);
		
		name = (EditText) findViewById(R.id.editText11);
		phone = (EditText) findViewById(R.id.editText12);
		address = (EditText) findViewById(R.id.editText13);
		numberToPick = (EditText) findViewById(R.id.editText15);
		description = (EditText) findViewById(R.id.editText14);
		male = (RadioButton) findViewById(R.id.radioButton1);
		female = (RadioButton) findViewById(R.id.radioButton2);
	//	addNumberButt = (ImageButton) findViewById(R.id.addNumberButton);
		btnPick = (ImageButton) findViewById(R.id.PickBtn);
		
		

		Log.d("EB","oncreate");
		Button butt_save=(Button)findViewById(R.id.button21);
		Button butt_dismiss=(Button)findViewById(R.id.button22);
		Log.d("EB","oncreate b");
		butt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), name.getText().toString(), Toast.LENGTH_LONG).show();
				if(name.getText().toString().length() == 0){
					Toast.makeText(getApplicationContext(), "Please insert name", Toast.LENGTH_SHORT).show();
					return;
				}
				if(phone.getText().toString().length() == 0){
					Toast.makeText(getApplicationContext(), "Please insert phone number", Toast.LENGTH_SHORT).show();
					return;
				}
				if(numberToPick.getText().toString().length() == 0){
					numberToPick.setText("0");
					Toast.makeText(getApplicationContext(), "Number to Pick-Up defined to "+
					numberToPick.getText().toString(), Toast.LENGTH_SHORT).show();
				}
				if(address.getText().toString().length() > 50){
					Toast.makeText(getApplicationContext(), "Address is too long", Toast.LENGTH_SHORT).show();
					return;
				}
			
				Log.d("EB","onclick");
				
				
				int lastId = model.getLastID();
				
				tremp = new Tremp(lastId + 1,
						name.getText().toString(),address.getText().toString(),
						phone.getText().toString(), description.getText().toString(),
						(male.isChecked()?1:0), numberToPick.getText().toString(), 
						dateTextView.getText().toString(), timeTextView.getText().toString());

				model.addTremp(tremp, TABLE_TREMPS);
				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		butt_dismiss.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Good Bye", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		dateTextView = (TextView) findViewById(R.id.date_textView3);
		dateButton = (Button) findViewById(R.id.dateButton);
		dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);
				System.out.println("the selected " + mDay);
				DatePickerDialog dialog = new DatePickerDialog(AddTrempActivity.this,
						new mDateSetListener(dateTextView), mYear, mMonth, mDay);
				dialog.show();
			

			}
		});

		timeTextView = (TextView) findViewById(R.id.time_textView3);
		timeButton = (Button) findViewById(R.id.timeButton);
		timeButton.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	            Calendar mcurrentTime = Calendar.getInstance();
	            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	            int minute = mcurrentTime.get(Calendar.MINUTE);
	            TimePickerDialog mTimePicker;
	            mTimePicker = new TimePickerDialog(AddTrempActivity.this, new TimePickerDialog.OnTimeSetListener() {
	                @Override
	                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
	                	timeTextView.setText( "      "+selectedHour + ":" + selectedMinute);
	                }
	            }, hour, minute, true);//Yes 24 hour time
	            mTimePicker.setTitle("Select Time");
	            mTimePicker.show();

	        }
	    });
	
		
	/*	
		addNumberButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pickContact();
				
			}
		});
		
		*/
		 
		  
	      btnPick.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
	              String[] strFields = { android.provider.CallLog.Calls._ID,
	                      android.provider.CallLog.Calls.NUMBER,
	                      android.provider.CallLog.Calls.CACHED_NAME, };
	              String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
	              final Cursor cursorCall = getContentResolver().query(
	                      android.provider.CallLog.Calls.CONTENT_URI, strFields,
	                      null, null, strOrder);

	              
	        
	              
	              AlertDialog.Builder builder = new AlertDialog.Builder(AddTrempActivity.this);
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
	                    		  AddTrempActivity.this, contactName, Toast.LENGTH_SHORT).show();
	                      cursorCall.close();
	                      return;
	                  }
	              };
	              builder.setCursor(cursorCall, listener,
	            	android.provider.CallLog.Calls.NUMBER);
	              builder.create().show();
	          }
	      });
		
	}
/*
 * 
 * The following code is to choose a number from the contact list
	 // Declare
	static final int PICK_CONTACT_REQUEST = 1;  // The request code

	  private void pickContact() {
		    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
		    pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
		    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
		}

	  //code 
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      // Check which request it is that we're responding to
	      if (requestCode == PICK_CONTACT_REQUEST) {
	          // Make sure the request was successful
	          if (resultCode == RESULT_OK) {
	              // Get the URI that points to the selected contact
	              Uri contactUri = data.getData();
	              // We only need the NUMBER column, because there will be only one row in the result
	              String[] projection = {Phone.NUMBER};

	              // Perform the query on the contact to get the NUMBER column
	              // We don't need a selection or sort order (there's only one result for the given URI)
	              // CAUTION: The query() method should be called from a separate thread to avoid blocking
	              // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
	              // Consider using CursorLoader to perform the query.
	              Cursor cursor = getContentResolver()
	                      .query(contactUri, projection, null, null, null);
	              cursor.moveToFirst();

	              // Retrieve the phone number from the NUMBER column
	              int column = cursor.getColumnIndex(Phone.NUMBER);
	              String number = cursor.getString(column);

	              phone.setText(number);
	          }
	      }
	  }
	*/
	 
	
}
