package com.mobilemerit.javafiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationStatus {

	Context mContext;
	private static final String SP_START_SERVICE="start";
	private static final String KEY_START="start";
	private static boolean KEY_IS_FIRST_START = false;	
	//notification setting
		private static final String KEY_NOTIFICATION="notification";
		private static boolean KEY_IS_NOTIFICATION = true;
		//vibration setting
		private static final String KEY_VIBRATION="vibration";
		private static boolean KEY_IS_VIBRATE = true;
	public ApplicationStatus(Context ctx) {
		// TODO Auto-generated constructor stub
		this.mContext=ctx;
	}	
	//service controller 
	public boolean getCurrentStartStatus(){
		SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
		return sp.getBoolean(KEY_START, KEY_IS_FIRST_START);
	}	
	public void setCurrentStartStatus(boolean isStartAgain){
		this.KEY_IS_FIRST_START = isStartAgain;
		SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
		Editor edt=sp.edit();
		edt.putBoolean(KEY_START, KEY_IS_FIRST_START);
		edt.commit();
	}
	//Notification controller
		public boolean getNotificationStatus(){
			SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
			return sp.getBoolean(KEY_NOTIFICATION, KEY_IS_NOTIFICATION);
		}
		
		public void setNotificationStatus(boolean isNotify){
			this.KEY_IS_NOTIFICATION = isNotify;
			SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
			Editor edt=sp.edit();
			edt.putBoolean(KEY_NOTIFICATION, KEY_IS_NOTIFICATION);
			edt.commit();
		}
		
		//VIBRATION controller
			public boolean getVibrateStatus(){
				SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
				return sp.getBoolean(KEY_VIBRATION, KEY_IS_VIBRATE);
			}
			
			public void setVibrateStatus(boolean isVibrate){
				this.KEY_IS_VIBRATE = isVibrate;
				SharedPreferences sp=mContext.getSharedPreferences(SP_START_SERVICE,Context.MODE_PRIVATE);
				Editor edt=sp.edit();
				edt.putBoolean(KEY_VIBRATION, KEY_IS_VIBRATE);
				edt.commit();
			}
}
