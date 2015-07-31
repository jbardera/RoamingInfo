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
		if (RoamingInfoService.changeReceiver!=null) unregisterReceiver(RoamingInfoService.changeReceiver);
		{
			stopService(new Intent(this,RoamingInfoService.class));
			Toast.makeText(this, getString(R.string.string5), Toast.LENGTH_SHORT).show();
		}
		finish();
	}
}
