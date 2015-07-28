package com.brapeba.roaminginfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ConnectivityChangeReceiver extends BroadcastReceiver 
{
	String tag="Roaming Info";
	static final int ID_NOTI_ROAMING = 1;
	static final int ID_NOTI_DATA = 2;
	String carrierName;
	String operatorName;
	String carrierCountry;
	String operatorCountryISO;
	String networkOperator; //MCC+MNC of current operator, to display its icon
	boolean roaming;
	String toShow;
	static public NotificationManager nm, nm2;
	
	@SuppressWarnings("deprecation")
	@Override public void onReceive(Context context, Intent intent) 
	{
		TelephonyManager mg = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);  
		nm2 = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	
		
		// code below for operator info (roaming)
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
		
		PendingIntent intencionPendiente = PendingIntent.getActivity(context, 0, new Intent(context, ShutDown.class), 0);
		
		//Notification noti = new Notification(R.drawable.stat_notify_rssi_in_range,toShow,System.currentTimeMillis());
		
		String uri = context.getResources().getString(R.string.icon_names_prefix)+networkOperator;
		int imageResource = context.getResources().getIdentifier(String.valueOf(uri),"drawable", context.getPackageName());
		
		if (imageResource==0) imageResource = context.getResources().getIdentifier("ic_stat_notify_rssi_in_range","drawable", context.getPackageName());
		
		//Notification noti = new Notification(R.drawable.ic_stat_notify_26821,toShow,System.currentTimeMillis());
		Notification noti = new Notification(imageResource,toShow,System.currentTimeMillis());
		noti.setLatestEventInfo(context, toShow,context.getResources().getString(R.string.string3), intencionPendiente);
		
		//noti.flags |= Notification.FLAG_ONGOING_EVENT; // to avoid dismiss it by swiping
		//noti.flags |= Notification.FLAG_NO_CLEAR; // to avoid dismiss it by clear all notifications
		
		nm.notify(ID_NOTI_ROAMING, noti); 
		
		// code below for data connection activity
		if (mg.getDataState()!=0) // data connection ON
		{
			Notification noti2 = new Notification(R.drawable.dataon3,toShow,System.currentTimeMillis());
			intencionPendiente = PendingIntent.getActivity(context, 1, new Intent(context, ShutDown.class), 0);
			noti2.setLatestEventInfo(context,context.getResources().getString(R.string.string6),context.getResources().getString(R.string.string3), intencionPendiente);
			nm2.notify(ID_NOTI_DATA,noti2);
			Log.d(tag, "Data connection ON!");
		} else { nm2.cancel(ConnectivityChangeReceiver.ID_NOTI_DATA); } 
		/*
		Log.e(tag, "action: " + intent.getAction());
		Log.e(tag, "component: " + intent.getComponent());
		Bundle extras = intent.getExtras();
		if (extras != null) 
		{
			for (String key: extras.keySet()) 
			{
				Log.e(tag, "key [" + key + "]: " +extras.get(key));
			}
		}
		else 
		{
			Log.e(tag, "no extras");
		}
		*/
	}
	
}