package com.mycompany.myinventory;



import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mobilemerit.javafiles.ApplicationStatus;

public class NotificationActivity extends Activity {

	private ApplicationStatus status;
	private CheckBox notifyBox, vibrateBox;
//	private RelativeLayout ringtoneLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_activity);
		setTitle("Notification Setting");
		setView();
	}

	private void setView() {
		status = new ApplicationStatus(this);
		notifyBox = (CheckBox) findViewById(R.id.check_notify);
		vibrateBox = (CheckBox) findViewById(R.id.check_vibrate);
	//	ringtoneLayout = (RelativeLayout) findViewById(R.id.ringtoneLayout);
		setStatus();
		notifyBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				status.setNotificationStatus(isChecked);
			}
		});
		vibrateBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				status.setVibrateStatus(isChecked);

			}
		});		
	}

	private void setStatus() {
		notifyBox.setChecked(status.getNotificationStatus());
		vibrateBox.setChecked(status.getVibrateStatus());
	}	
}
