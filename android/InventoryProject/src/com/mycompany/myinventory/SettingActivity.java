package com.mycompany.myinventory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mobilemerit.adapter.SettingAdapter;
import com.mobilemerit.javafiles.Devices;
import com.mobilemerit.javafiles.ExportItemToExcel;
import com.mobilemerit.javafiles.SettingPojo;

public class SettingActivity extends Activity implements OnItemClickListener {
	private ListView listview;
	private SettingAdapter adapter;
	private List<SettingPojo> listItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		setTitle("Settings");
		populateList();
		listview = (ListView)findViewById(R.id.listView);		
		adapter = new SettingAdapter(this, listItems);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}
		
	private void populateList(){
		listItems = new ArrayList<SettingPojo>();
		listItems.add(new SettingPojo("Notification", R.drawable.notification));
		listItems.add(new SettingPojo("Export Excel", R.drawable.ic_excel));
		listItems.add(new SettingPojo("Feedback", R.drawable.feedback));
		listItems.add(new SettingPojo("About", R.drawable.about));
		
	}
	// directory location of Excel file in sd card
		private String fileLocation() {
			File sdCard = Environment.getExternalStorageDirectory();
			String path = (sdCard.getAbsolutePath() + "/inventory");
			return path;
		}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		 if(position == 0){
			Intent intent = new Intent(SettingActivity.this, NotificationActivity.class);
			startActivity(intent);
		}else if(position == 1){
			ExportItemToExcel export = new ExportItemToExcel(SettingActivity.this,
					 fileLocation());
			export.exportExcel();		
		}else if(position == 2){
			sendFeedback();			
		}else if(position == 3){
		showAboutDialog();	
		}		
	}
	private void sendFeedback() {	    	
   	 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   	 Calendar cal = Calendar.getInstance(); 	 
   	 String time = dateFormat.format(cal.getTime());
   	
   	String model = android.os.Build.MODEL;
   	String device = Devices.getDeviceName();
   	String product = android.os.Build.PRODUCT;
   	String make = android.os.Build.MANUFACTURER;
   	
   	PackageInfo pInfo = null;
   	  try {
   	   pInfo = SettingActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
   	  } catch (NameNotFoundException e) {
   	   e.printStackTrace();
   	  }
   	  String app = pInfo.packageName;
   	  String version = pInfo.versionName;    	 
   	  
   	  StringBuffer sb = new StringBuffer("---Environment---");
         sb.append('\n' +"Time = "+ time);
         sb.append('\n' + "Device = " + device);
         sb.append('\n' + "Make = " + make);
         sb.append('\n' + "Model = " + model);
         sb.append('\n' + "Product = " + product);
         sb.append('\n' + "App = " + app);
         sb.append('\n' + "Version " + version);
   	
	    Intent email = new Intent(Intent.ACTION_SEND);
	    email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.contact_mail)});          
	    email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
	    email.putExtra(Intent.EXTRA_TEXT, sb.toString());
	    email.setType("message/rfc822");
	    startActivity(Intent.createChooser(email, "Feedback"));	   
	}
	
	private void showAboutDialog(){
		PackageInfo pInfo = null;
	   	  try {
	   	   pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
	   	  } catch (NameNotFoundException e) {
	   	   e.printStackTrace();
	   	  }	   	  
	   	  String version = pInfo.versionName;
		AlertDialog.Builder builder1 = new AlertDialog.Builder(SettingActivity.this);
		builder1.setTitle(getResources().getString(R.string.app_name));
        builder1.setMessage(version);        
        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {		
		if(requestCode == 1001){
			recreate();			
		}
	}
}
