package com.brapeba.roaminginfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StaticConChgReceiver extends BroadcastReceiver 
{
	@Override public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED"))
		{
			//just in case to do anything...
		} 
		context.startService(new Intent(context,RoamingInfoService.class));
	}
}