package com.brapeba.roaminginfo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class ShutDown extends Activity
{
	
	NotificationManager nm;
	
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		nm= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(100); // to remove the notification for roaming info
		nm.cancel(101); // to remove the notification for roaming info sim2
		nm.cancel(110); // to remove the notification for data connection state
		ComponentName receiver = new ComponentName(this, StaticConChgReceiver.class);
	    PackageManager pm = this.getPackageManager();
	    pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
		finish();
	}
}
