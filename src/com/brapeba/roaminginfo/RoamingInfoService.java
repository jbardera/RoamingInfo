package com.brapeba.roaminginfo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.widget.Toast;

public class RoamingInfoService extends Service 
{
	static public ConnectivityChangeReceiver changeReceiver;
	static public boolean started=false;

	@Override public void onCreate() 
	{
		super.onCreate();
		PackageManager pm  = this.getPackageManager();
		ComponentName componentName = new ComponentName(this, ConnectivityChangeReceiver.class);
		pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
		changeReceiver=new ConnectivityChangeReceiver();
		registerReceiver(changeReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		started=true;
		//Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
	}
	
	@Override public int onStartCommand(Intent intenc, int flags, int idArranque) 
	{
		super.onStartCommand(intenc,flags,idArranque);
		started=true;
		Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
		return START_STICKY; 
	}

	@Override public void onDestroy() 
	{
			ConnectivityChangeReceiver.nm.cancel(ConnectivityChangeReceiver.ID_NOTIFICACION_CREAR); // to remove the notification
			if (changeReceiver!=null) unregisterReceiver(changeReceiver); // to unregister the broadcast listener
			PackageManager pm  = this.getPackageManager();
	        ComponentName componentName = new ComponentName(this, ConnectivityChangeReceiver.class);
	        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
	        started=false;
	        super.onDestroy();
	}

	@Override public IBinder onBind(Intent intencion) 
	{
		return null;
	}
}