package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ShutDown extends Activity
{
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if (RoamingInfoService.started) 
		{
			stopService(new Intent(this,RoamingInfoService.class));
			Toast.makeText(this, getString(R.string.string5), Toast.LENGTH_SHORT).show();
			/*
			ConnectivityChangeReceiver.nm.cancel(ConnectivityChangeReceiver.ID_NOTIFICACION_CREAR); // to remove the notification
			RoamingInfoService.started=false;
			*/
		}
		finish();
	}
}
