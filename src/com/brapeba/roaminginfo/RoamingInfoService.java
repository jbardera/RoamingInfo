package com.brapeba.roaminginfo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.mediatek.telephony.TelephonyManagerEx;

public class RoamingInfoService extends Service 
{
	private String carrierName,carrierName2;
	private String operatorName,operatorName2;
	private String carrierCountry,carrierCountry2;
	private String operatorCountryISO,operatorCountryISO2;
	private String networkOperator,networkOperator2; //MCC+MNC of current operator, to display its icon
	private boolean roaming,roaming2;
	private String toShow,toShow2,toShowD;
	private final static int SIM1=0,SIM2=1;
	private boolean weMtk=true,chgTs=false,chgTs2=false;
	TelephonyManagerEx mgMtk;
	NotificationManager nm;
	StaticConChgReceiver changeReceiver;
	final String tag="Roaming Info";
	int ID_NOTI_ROAMING = 100;
	int ID_NOTI_ROAMING2 = 101; //2nd SIM Mediatek
	int ID_NOTI_DATA = 110;
	final String PREFS = "MyPrefs";
	SharedPreferences mySettings;

	@Override public void onCreate() 
	{
		super.onCreate();
	}

	@SuppressLint("InlinedApi")
	@SuppressWarnings("deprecation")
	@Override public int onStartCommand(Intent intenc, int flags, int idArranque) 
	{
		super.onStartCommand(intenc,flags,idArranque);

		TelephonyManager mg = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

		if (Build.VERSION.SDK_INT>=11) mySettings = getSharedPreferences("MyPrefs", Context.MODE_MULTI_PROCESS);
		else mySettings = getSharedPreferences("MyPrefs",0);

		String non = mySettings.getString("non","null");
		String nci = mySettings.getString("nci","null");
		String son = mySettings.getString("son","null");
		String sci = mySettings.getString("sci","null");
		
		nm= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		//code below for operator info (roaming)
		carrierName = mg.getNetworkOperatorName();
		operatorName = mg.getSimOperatorName();
		carrierCountry = mg.getNetworkCountryIso();
		networkOperator=mg.getNetworkOperator(); // defines the icon to show at notification
		operatorCountryISO=mg.getSimCountryIso();
		roaming = mg.isNetworkRoaming();
		
		toShow="[1] ";
		try { mgMtk= new TelephonyManagerEx(this); } catch (Exception e) { weMtk=false; toShow=""; } //!weMtk in case no Mediatek 

		chgTs=!(non.equals(carrierName))||!(nci.equals(carrierCountry))||!(son.equals(operatorName))||(!sci.equals(operatorCountryISO));
		SharedPreferences.Editor editor = mySettings.edit();
		PendingIntent intencionPendiente = PendingIntent.getActivity(this, 0, new Intent(this, ShutDown.class), Intent.FLAG_ACTIVITY_NO_HISTORY);
		
		//only modify the notification if there is a change on what to display!
		if (chgTs)
		{
			editor.putString("non",carrierName);
			editor.putString("nci",carrierCountry);
			editor.putString("son",operatorName);
			editor.putString("sci",operatorCountryISO);
			editor.commit();
			if (roaming) toShow=toShow+carrierName+":"+carrierCountry+" ("+operatorName+":"+operatorCountryISO+")";
			else
				if (networkOperator.length()>3) toShow=toShow+carrierName+" "+networkOperator.substring(0,3)+"-"+networkOperator.substring(3);
				else toShow=toShow+carrierName+" "+networkOperator;

			if (networkOperator.length()==0) //to show NO CARRIER
				toShow=toShow+getResources().getString(R.string.string1)+" ("+operatorName+":"+operatorCountryISO+")";

			//Notification noti = new Notification(R.drawable.stat_notify_rssi_in_range,toShow,System.currentTimeMillis());

			String uri = getResources().getString(R.string.icon_names_prefix)+networkOperator;
			int imageResource = getResources().getIdentifier(String.valueOf(uri),"drawable", getPackageName());

			if (imageResource==0) imageResource = getResources().getIdentifier("ic_stat_notify_rssi_in_range","drawable", getPackageName());

			//Notification noti = new Notification(R.drawable.ic_stat_notify_26203,toShow,System.currentTimeMillis());
			Notification noti = new Notification(imageResource,toShow,System.currentTimeMillis());
			noti.setLatestEventInfo(this, toShow,getResources().getString(R.string.string3), intencionPendiente);
			//noti.flags |= Notification.FLAG_ONGOING_EVENT; // to avoid dismiss it by swiping
			//noti.flags |= Notification.FLAG_NO_CLEAR; // to avoid dismiss it by clear all notifications
			nm.notify(ID_NOTI_ROAMING, noti); 
		}

		//notification for 2nd SIM:
		if (weMtk) 
		{  
			if (mgMtk.hasIccCard(SIM2)) //if SIM2 present
			{
				carrierName2 = mgMtk.getNetworkOperatorName(SIM2); 
				operatorName2 = mgMtk.getSimOperatorName(SIM2);
				carrierCountry2 = mgMtk.getNetworkCountryIso(SIM2);
				networkOperator2=mgMtk.getNetworkOperator(SIM2);
				operatorCountryISO2=mgMtk.getSimCountryIso(SIM2);
				roaming2 = mgMtk.isNetworkRoaming(SIM2);
				
				String non2 = mySettings.getString("non2","null");
				String nci2 = mySettings.getString("nci2","null");
				String son2 = mySettings.getString("son2","null");
				String sci2 = mySettings.getString("sci2","null");
				
				chgTs2=!(non2.equals(carrierName2))||!(nci2.equals(carrierCountry2))||!(son2.equals(operatorName2))||(!sci2.equals(operatorCountryISO2));

				//again, only modify the notification if there is a change on what to display!
				if (chgTs2)
				{
					editor.putString("non2",carrierName2);
					editor.putString("nci2",carrierCountry2);
					editor.putString("son2",operatorName2);
					editor.putString("sci2",operatorCountryISO2);
					editor.commit();
					if (roaming2) toShow2="[2] "+carrierName2+":"+carrierCountry2+" ("+operatorName2+":"+operatorCountryISO2+")";
					else 
						if (networkOperator2.length()>3) toShow2="[2] "+carrierName2+" "+networkOperator2.substring(0,3)+"-"+networkOperator2.substring(3);
						else toShow2="[2] "+carrierName2+" "+networkOperator2;
					if (networkOperator2.length()==0) //to show NO CARRIER
						toShow2="[2] "+getResources().getString(R.string.string1)+" ("+operatorName2+":"+operatorCountryISO2+")";

					String uri2 = getResources().getString(R.string.icon_names_prefix)+networkOperator2;
					int imageResource2 = getResources().getIdentifier(String.valueOf(uri2),"drawable", getPackageName());

					if (imageResource2==0) imageResource2 = getResources().getIdentifier("ic_stat_notify_rssi_in_range","drawable", getPackageName());

					//Notification noti3 = new Notification(R.drawable.ic_stat_notify_26203,toShow,System.currentTimeMillis());
					Notification noti3 = new Notification(imageResource2,toShow2,System.currentTimeMillis());
					noti3.setLatestEventInfo(this, toShow2,getResources().getString(R.string.string3), intencionPendiente);
					nm.notify(ID_NOTI_ROAMING2, noti3);
				}
			}  
		} 
		
		//code below for data connection activity
		if (mg.getDataState()!=0) //data connection ON
		{
			Boolean showme = mySettings.getBoolean("showdata",true); 
			if (showme)
			{
				if (weMtk)
				{
					if (mgMtk.getDataState(0)!=0) toShowD="[1] "+getResources().getString(R.string.string6); 
					else toShowD="[2] "+getResources().getString(R.string.string6);
				} else toShowD="";
				Notification noti2 = new Notification(R.drawable.dataon3,getResources().getString(R.string.string6),System.currentTimeMillis());
				PendingIntent iPendiente2 = PendingIntent.getActivity(this, 0, new Intent(this, ShutDataNot.class), Intent.FLAG_ACTIVITY_NO_HISTORY);
				noti2.setLatestEventInfo(this,toShowD,getResources().getString(R.string.string8), iPendiente2);
				//noti2.defaults |=Notification.DEFAULT_SOUND;
				noti2.flags|=Notification.FLAG_AUTO_CANCEL;
				//notification.defaults |= Notification.DEFAULT_VIBRATE;
				noti2.ledOnMS=1000; //light on in milliseconds
				noti2.ledOffMS=4000; //light off in milliseconds
				noti2.ledARGB=Color.RED; 
				//noti2.flags|=Notification.FLAG_SHOW_LIGHTS;
				nm.notify(ID_NOTI_DATA,noti2);
			}
		} else { nm.cancel(ID_NOTI_DATA); }
		
		stopSelf();
		return START_STICKY; 
	}

	@Override public void onDestroy() 
	{
		super.onDestroy();
	}

	@Override public IBinder onBind(Intent intencion) 
	{
		return null;
	}
}