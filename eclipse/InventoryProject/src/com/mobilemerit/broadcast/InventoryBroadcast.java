package com.mobilemerit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mobilemerit.service.NotifyService;

public class InventoryBroadcast extends BroadcastReceiver {	
	
	@Override
	public void onReceive(Context context, Intent arg1) {			
				context.startService(new Intent(context,NotifyService.class));					
		
	}	
}
