package com.example.mytremp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.mytremp.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button b;
	private Button historyButton;
	private ListView lv;
	public static ArrayList<Tremp> trempsList = new ArrayList<Tremp>();
	private static boolean firstTime=true;
	private CustomAdapter adapter;
	private Model model;
	
	public static final String TABLE_TREMPS = "TREMPS";
	public static final String TABLE_TREMPS_ARCHIVE = "TREMPS_ARCHIVE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		model = Model.getInstance(this);
		String[] args = {"0"};
		trempsList = model.getAllTremps(TABLE_TREMPS, args);
		//Toast.makeText(getApplicationContext(), "ResList = "+restaurantsList.size(),Toast.LENGTH_LONG).show();
		if (trempsList.size() < 1 ){
			Toast.makeText(getApplicationContext(), "No Tremps", Toast.LENGTH_LONG).show();
			Log.d("EB","trempsList.size() < 1");
			createInitialTremps();
		}

		lv=(ListView)findViewById(R.id.listView1);
		adapter = new CustomAdapter();
		lv.setAdapter(adapter); 


		final Intent editTremp = new Intent(this, EditTrempActivity.class);

		lv.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int i, long l) {
				int _id = trempsList.get(i).get_id();
				editTremp.putExtra("_id",_id); 
				startActivity(editTremp);	

			}
		});

		b=(Button)findViewById(R.id.button1);
		final Intent i = new Intent(this,AddTrempActivity.class);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(i);		
			}
		});
		
		
		historyButton = (Button) findViewById(R.id.Button01);
		final Intent ii = new Intent(this,HistoryActivity.class);
		historyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(ii);	
				
			}
		});

	}
	private void createInitialTremps() {
		//Creating restaurants for the list
		Tremp tremp1 = new Tremp(1, "No Tremps", 
				"", "", "", 999, "1", null, null);

		//list initialization
		trempsList.add(tremp1);
		//model.addTremp(tremp1, TABLE_TREMPS);
		//String[] args = {"0"};
		//trempsList = model.getAllTremps(TABLE_TREMPS, args);
		//lv.setAdapter(adapter);
	}


	@Override
	protected void onResume() {
		super.onResume();
		String[] args = {"0"};
		trempsList = model.getAllTremps(TABLE_TREMPS, args);
		Collections.sort(trempsList);
		lv.setAdapter(adapter);

	}


	class CustomAdapter extends BaseAdapter{

		private LayoutInflater inflater;

		public CustomAdapter(){
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public int getCount() {
			return trempsList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return trempsList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int location, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = inflater.inflate(R.layout.row_layout, parent,false);
			}

			Tremp tremp = trempsList.get(location);

			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView address = (TextView) convertView.findViewById(R.id.address);
			TextView phone = (TextView) convertView.findViewById(R.id.phone);
			//TextView description = (TextView) convertView.findViewById(R.id.description);
			ImageView image = (ImageView) convertView.findViewById(R.id.imageView1);
			

			name.setText("Name: "+tremp.getName());
			address.setText("Address: "+tremp.getAddress());
			phone.setText("Phone number: "+tremp.getPhone());
			if (tremp.getGender()==1){
				image.setImageResource(R.drawable.boy_1);
			}else{
				image.setImageResource(R.drawable.girl_4);
			}

			return convertView;		
		}

	}
}
