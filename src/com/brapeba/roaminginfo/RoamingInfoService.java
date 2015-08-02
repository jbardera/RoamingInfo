package com.brapeba.roaminginfo;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

public class RoamingInfoService extends Service 
{
	static public ConnectivityChangeReceiver changeReceiver;
	static public String tag="Roaming Info";
	static public NotificationManager nm;
	static final int ID_NOTI_ROAMING = 100;
	static final int ID_NOTI_ROAMING2 = 101; //2nd SIM Mediatek
	static final int ID_NOTI_DATA = 110;
	static public final String PREFS = "MyPrefs";
	static public SharedPreferences settings;
	
	@Override public void onCreate() 
	{
		super.onCreate();
				
		PackageManager pm  = this.getPackageManager();
		ComponentName componentName = new ComponentName(this, ConnectivityChangeReceiver.class);
		pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
		changeReceiver=new ConnectivityChangeReceiver();
		registerReceiver(changeReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		//Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
	}
	
	@Override public int onStartCommand(Intent intenc, int flags, int idArranque) 
	{
		super.onStartCommand(intenc,flags,idArranque);
		Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
		return START_STICKY; 
	}

	@Override public void onDestroy() 
	{
			nm.cancel(ID_NOTI_ROAMING); // to remove the notification for roaming info
			nm.cancel(ID_NOTI_ROAMING2); // to remove the notification for roaming info sim2
			nm.cancel(ID_NOTI_DATA); // to remove the notification for data connection state
			if (changeReceiver!=null) unregisterReceiver(changeReceiver); // to unregister the broadcast listener
			PackageManager pm  = this.getPackageManager();
	        ComponentName componentName = new ComponentName(this, ConnectivityChangeReceiver.class);
	        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
	        super.onDestroy();
	}

	@Override public IBinder onBind(Intent intencion) 
	{
		return null;
	}
}