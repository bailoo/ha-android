package com.mycompany.myinventory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ApplicationStatus;
import com.mobilemerit.javafiles.ItemsDetails;
import com.mobilemerit.service.NotifyService;

public class HomeActivity extends ActionBarActivity{
	
	private ApplicationStatus applicationStatus;
	private DatabaseHandler databaseHandler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		applicationStatus = new ApplicationStatus(this);

		if(!applicationStatus.getCurrentStartStatus()){
			databaseHandler = new DatabaseHandler(this);

			databaseHandler.saveCategory(new ItemsDetails("Automotive & Powersports"));
			databaseHandler.saveCategory(new ItemsDetails("Accessories"));
			databaseHandler.saveCategory(new ItemsDetails("Baby"));
			databaseHandler.saveCategory(new ItemsDetails("Beauty"));
			databaseHandler.saveCategory(new ItemsDetails("Camera & Photo"));
			databaseHandler.saveCategory(new ItemsDetails("Cell Phones"));
			databaseHandler.saveCategory(new ItemsDetails("Clothing"));
			databaseHandler.saveCategory(new ItemsDetails("Computers"));
			databaseHandler.saveCategory(new ItemsDetails("Furniture"));
			databaseHandler.saveCategory(new ItemsDetails("Jewelry"));
			databaseHandler.saveCategory(new ItemsDetails("Musical Instruments"));
			databaseHandler.saveCategory(new ItemsDetails("Sports Collectibles"));
			databaseHandler.saveCategory(new ItemsDetails("Toys"));
			databaseHandler.saveCategory(new ItemsDetails("Watches"));
			applicationStatus.setCurrentStartStatus(true);

			//start background service for notification
			startService(new Intent(HomeActivity.this, NotifyService.class));

		}
		startActivity(new Intent(HomeActivity.this, LoginActivity.class));
		finish();
	}



}
