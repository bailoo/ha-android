package com.mobilemerit.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Parser;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.mobilemerit.broadcast.InventoryBroadcast;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.ApplicationStatus;
import com.mobilemerit.javafiles.ItemsDetails;
import com.mycompany.myinventory.MainActivity;
import com.mycompany.myinventory.R;

public class NotifyService extends Service {
	
	public static int IDs=21356;
	private ApplicationStatus status;
	public static final String DB_INTENT = "com.mobilemerit.inventory.Notify";
	private List<ItemsDetails> itemList;
	private DatabaseHandler databaseHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();			
		schedualService();
		status = new ApplicationStatus(this);	
		itemList = new ArrayList<ItemsDetails>();
		databaseHandler = new DatabaseHandler(this);
		itemList = databaseHandler.getItemsDetails();
		if(status.getNotificationStatus()){
		for(ItemsDetails item : itemList){
			if(item.getQuantity()< item.getNotifyLimit()){
				notifyUser(item);
			}
		}
		}
		stopSelf();
	}	
	 
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	
	public void notifyUser(ItemsDetails item){
		//vibration on
				NotificationCompat.Builder mBuilder = null;		
					if(status.getVibrateStatus()){
						 mBuilder =
						        new NotificationCompat.Builder(this)
								.setSmallIcon(R.drawable.ic_launcher)
						        .setContentTitle(item.getName())
						        .setContentText("Item quantity is low!")
						        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)		        
						        .setAutoCancel(true);
					}else{
						 mBuilder =
						        new NotificationCompat.Builder(this)
								.setSmallIcon(R.drawable.ic_launcher)
						        .setContentTitle(item.getName())
						        .setContentText("Item quantity is low!")
						        .setDefaults(Notification.DEFAULT_SOUND)		        
						        .setAutoCancel(true);
					}
				
		Intent resultIntent = new Intent(this, MainActivity.class);
		//resultIntent.putExtra("position",position);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(new Random().nextInt(IDs),
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(new Random().nextInt(IDs), mBuilder.build());
		//Toast.makeText(getBaseContext(), "Notify End",Toast.LENGTH_SHORT).show();
	}
	
	public void sendBroadcastMsg() {
		Intent i = new Intent();
		i.setAction(DB_INTENT);
		sendBroadcast(i);
	}
	
	public void schedualService(){
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		Intent intent = new Intent(this,InventoryBroadcast.class);		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(),
				new Random().nextInt(1000), intent, 0);
		
		Date date = new Date();
		Calendar virtual = Calendar.getInstance();
		virtual.set(Calendar.DATE, date.getDate()+1);
		virtual.set(Calendar.MONTH, date.getMonth());
		virtual.set(Calendar.YEAR, date.getYear() + 1900);
		virtual.set(Calendar.HOUR_OF_DAY, 12);
		virtual.set(Calendar.MINUTE, 0);		
		virtual.set(Calendar.SECOND, 0);
		virtual.set(Calendar.MILLISECOND, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, virtual.getTimeInMillis(),
				pendingIntent);
	}
}