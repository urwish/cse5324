package com.notesmates;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.notesmates.library.DatabaseHandler;

public class MainProfile extends Activity {
	
	 protected static final Intent MainActivity = null;
	 	Button btncourse1;
	    Button btncourse2;
	    Button btncourse3;
	   

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainprofile);
		
		 btncourse1 = (Button) findViewById(R.id.course1);
	     btncourse2 = (Button) findViewById(R.id.course2);
	     btncourse3 = (Button)  findViewById(R.id.course3);
	     
	     DatabaseHandler db = new DatabaseHandler(getApplicationContext());

	        /**
	         * Hashmap to load data from the Sqlite database for courses
	         **/
	         HashMap<String,String> user = new HashMap<String, String>();
	         user = db.getUserDetails();
	         
	         btncourse1.setText("Coures1");
	         btncourse2.setText("Coures2");
	         btncourse3.setText("Coures3");
	         
	         /**
	          *Enter Course1 homepage
	          **/
	         btncourse1.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View view) {
	             Intent myIntent = new Intent(view.getContext(), MainActivity.class);
	             startActivityForResult(myIntent, 0);
	             finish();
	             }});

	         
	         btncourse2.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View view) {
	             Intent myIntent = new Intent(view.getContext(), MainActivity.class);
	             startActivityForResult(myIntent, 0);
	             finish();
	             }});

	         
	         btncourse3.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View view) {
	             Intent myIntent = new Intent(view.getContext(), MainActivity.class);
	             startActivityForResult(myIntent, 0);
	             finish();
	             }});
	         
	         




	}

}
