package com.brapeba.roaminginfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.telephony.TelephonyManager;

public class ConnectivityChangeReceiver extends BroadcastReceiver 
{
	private String carrierName;
	private String operatorName;
	private String carrierCountry;
	private String operatorCountryISO;
	private String networkOperator; //MCC+MNC of current operator, to display its icon
	private boolean roaming;
	private String toShow;
	
	
	@SuppressWarnings("deprecation")
	@Override public void onReceive(Context context, Intent intent) 
	{
		TelephonyManager mg = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		RoamingInfoService.nm= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		SharedPreferences mySettings;
		
		//code below for operator info (roaming)
		carrierName = mg.getNetworkOperatorName();
		operatorName = mg.getSimOperatorName();
		carrierCountry = mg.getNetworkCountryIso();
		networkOperator=mg.getNetworkOperator(); // defines the icon to show at notification
		operatorCountryISO=mg.getSimCountryIso();

		roaming = mg.isNetworkRoaming();

		if (roaming) 
		{
			toShow=carrierName+":"+carrierCountry+" ("+operatorName+":"+operatorCountryISO+")";
		} else
		{
			toShow=carrierName+" "+networkOperator;
		}
		
		if (networkOperator.length()==0) //to show NO CARRIER
		{
			toShow=context.getResources().getString(R.string.string1)+" ("+operatorName+":"+operatorCountryISO+")";
		}
		
		PendingIntent intencionPendiente = PendingIntent.getActivity(context, 0, new Intent(context, ShutDown.class), Intent.FLAG_ACTIVITY_NO_HISTORY);
		
		//Notification noti = new Notification(R.drawable.stat_notify_rssi_in_range,toShow,System.currentTimeMillis());
		
		String uri = context.getResources().getString(R.string.icon_names_prefix)+networkOperator;
		int imageResource = context.getResources().getIdentifier(String.valueOf(uri),"drawable", context.getPackageName());
		
		if (imageResource==0) imageResource = context.getResources().getIdentifier("ic_stat_notify_rssi_in_range","drawable", context.getPackageName());
		
		//Notification noti = new Notification(R.drawable.ic_stat_notify_22201,toShow,System.currentTimeMillis());
		Notification noti = new Notification(imageResource,toShow,System.currentTimeMillis());
		noti.setLatestEventInfo(context, toShow,context.getResources().getString(R.string.string3), intencionPendiente);
		
		//noti.flags |= Notification.FLAG_ONGOING_EVENT; // to avoid dismiss it by swiping
		//noti.flags |= Notification.FLAG_NO_CLEAR; // to avoid dismiss it by clear all notifications
		
		RoamingInfoService.nm.notify(RoamingInfoService.ID_NOTI_ROAMING, noti); 
		
		//code below for data connection activity
		if (mg.getDataState()!=0) //data connection ON
		{
			if (Build.VERSION.SDK_INT>=11) mySettings = context.getSharedPreferences("MyPrefs", Context.MODE_MULTI_PROCESS);
				else mySettings = context.getSharedPreferences("MyPrefs",0);
			Boolean showme = mySettings.getBoolean("showdata",true);
			if (showme)
			{
				Notification noti2 = new Notification(R.drawable.dataon3,context.getResources().getString(R.string.string6),System.currentTimeMillis());
				PendingIntent iPendiente2 = PendingIntent.getActivity(context, 0, new Intent(context, ShutDataNot.class), Intent.FLAG_ACTIVITY_NO_HISTORY);
				noti2.setLatestEventInfo(context,context.getResources().getString(R.string.string6),context.getResources().getString(R.string.string8), iPendiente2);
				noti2.defaults |=Notification.DEFAULT_SOUND;
				noti2.flags|=Notification.FLAG_AUTO_CANCEL;
				//notification.defaults |= Notification.DEFAULT_VIBRATE;
				noti2.ledOnMS=1000; //light on in milliseconds
				noti2.ledOffMS=4000; //light off in milliseconds
				noti2.ledARGB=Color.RED; 
				noti2.flags|=Notification.FLAG_SHOW_LIGHTS;
				RoamingInfoService.nm.notify(RoamingInfoService.ID_NOTI_DATA,noti2);
			}
		} else { RoamingInfoService.nm.cancel(RoamingInfoService.ID_NOTI_DATA); } 
	}
}