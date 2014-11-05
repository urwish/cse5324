package com.notesmates;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.notesmates.library.Category;
import com.notesmates.library.ServiceHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Select_courses extends Activity implements OnItemSelectedListener {

	private Button btnAddNewCategory;
	private Button btnSelectClass;
	private Button btnEnterHome;
	private TextView txtCategory;
	private Spinner spinnercourse1;
	private Spinner spinnercourse2;
	private Spinner spinnercourse3;
	// array list for spinner adapter
	private ArrayList<Category> categoriesList;
	ProgressDialog pDialog;

	// API urlss
	// Url to create new category
	private String URL_NEW_CATEGORY = "http://129.107.150.160/courses/new_category.php";
	// Url to get all categories
	private String URL_CATEGORIES = "http://129.107.150.160/courses/get_categories.php";
	// Url to Update categories
	private String URL_Add_Classes = "http://129.107.150.160/courses/Add_Classes.php";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectcourses);

		btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);
		btnSelectClass = (Button) findViewById(R.id.btnSelectClass);
		spinnercourse1 = (Spinner) findViewById(R.id.spincourse1);
		spinnercourse2 = (Spinner) findViewById(R.id.spincourse2);
		spinnercourse3 = (Spinner) findViewById(R.id.spincourse3);
		txtCategory = (TextView) findViewById(R.id.txtCategory);
		btnEnterHome = (Button) findViewById(R.id.btnEnterHomePage);
		
		categoriesList = new ArrayList<Category>();

		// spinner item select listener
		spinnercourse1.setOnItemSelectedListener(this);
		
		// spinner item select listener
		spinnercourse2.setOnItemSelectedListener(this);
				
		// spinner item select listener
		spinnercourse3.setOnItemSelectedListener(this);		

		// Add new category click event
		btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (txtCategory.getText().toString().trim().length() > 0) {
					
					// new category name
					String newCategory = txtCategory.getText().toString();

					// Call Async task to create new category
					new AddNewCategory().execute(newCategory);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please enter category name", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		
		  btnEnterHome.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View arg0) {

	                Intent mainactivity = new Intent(getApplicationContext(), MainActivity.class);
	                mainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(mainactivity);
	                finish();
	            }
	        });

		
		// On click insert update values
		btnSelectClass.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (spinnercourse1.getSelectedItemPosition()> 0 && spinnercourse2.getSelectedItemPosition()> 0 && spinnercourse3.getSelectedItemPosition()> 0) {
					
					// Spinner values category name
					int lspinner1Value = spinnercourse1.getSelectedItemPosition();
					int lspinner2Value = spinnercourse2.getSelectedItemPosition();
					int lspinner3Value = spinnercourse3.getSelectedItemPosition();
					
					String aStringspinner1Value = Integer.toString(lspinner1Value);
					String aStringspinner2Value = Integer.toString(lspinner2Value);
					String aStringspinner3Value = Integer.toString(lspinner3Value);

					// Call Async task to create new category
					new AddClasses().execute(aStringspinner1Value);
					new AddClasses().execute(aStringspinner2Value);
					new AddClasses().execute(aStringspinner3Value);
					
					 
				} else {
					Toast.makeText(getApplicationContext(),
							"Please enter category name", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		new GetCategories().execute();
	}
	
	
	
	/**
	 * Adding spinner data
	 * */
	private void populateSpinner() {
		List<String> lables = new ArrayList<String>();
		txtCategory.setText("");
		for (int i = 0; i < categoriesList.size(); i++) {
			lables.add(categoriesList.get(i).getName());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, lables);

		// Drop down layout style - list view 
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnercourse1.setAdapter(spinnerAdapter);
		spinnercourse2.setAdapter(spinnerAdapter);
		spinnercourse3.setAdapter(spinnerAdapter);
	}

	/**
	 * Async task to get all course categories
	 * */
	private class GetCategories extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Select_courses.this);
			pDialog.setMessage("Fetching Classes..");
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("categories");						

						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
							Category cat = new Category(catObj.getInt("id"),
							catObj.getString("name"));
							categoriesList.add(cat);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
				populateSpinner();
		}

	}

	/**
	 * Async task to create a new food category
	 * */
	private class AddNewCategory extends AsyncTask<String, Void, Void> {
		boolean isNewCategoryCreated = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Select_courses.this);
			pDialog.setMessage("Creating new category..");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected Void doInBackground(String... arg) {

			String newCategory = arg[0];

			// Preparing post params
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", newCategory));

			ServiceHandler serviceClient = new ServiceHandler();

			String json = serviceClient.makeServiceCall(URL_NEW_CATEGORY,
					ServiceHandler.POST, params);

			Log.d("Create Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					boolean error = jsonObj.getBoolean("error");
					// checking for error node in json
					if (!error) {	
						// new category created successfully
						isNewCategoryCreated = true;
					} else {
						Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (isNewCategoryCreated) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// fetching all categories
						new GetCategories().execute();
					}
				});
			}
		}
	}

	/**
	 * Async task to update and insert class info
	 * */
	private class AddClasses extends AsyncTask<String, Void, Void> {
		boolean isNewCategoryCreated = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Select_courses.this);
			pDialog.setMessage("Updating Class..");
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected Void doInBackground(String... arg) {

			String newCategory = arg[0];

			// Preparing post params
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("classes", newCategory));

			ServiceHandler serviceClient = new ServiceHandler();

			String json = serviceClient.makeServiceCall(URL_Add_Classes,ServiceHandler.POST, params);

			Log.d("Create Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					boolean error = jsonObj.getBoolean("error");
					// checking for error node in json
					if (!error) {	
						// new category created successfully
						isNewCategoryCreated = true;
					} else {
						Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (isNewCategoryCreated) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Intent redirhomepage = new Intent(getApplicationContext(), MainProfile.class);
		                redirhomepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(redirhomepage);
		                finish();
					}
				});
			}
		}
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString() + " Selected" ,Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {		
	}
}
