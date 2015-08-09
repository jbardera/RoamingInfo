/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class OnBootReceiver extends BroadcastReceiver 
{
	final String PREFS = "MyPrefs";
	static SharedPreferences mySettings;
	
	@SuppressLint("InlinedApi")
	@Override public void onReceive(Context context, Intent intent) 
	{
		if (Build.VERSION.SDK_INT>=11) mySettings = context.getSharedPreferences(PREFS, Context.MODE_MULTI_PROCESS);
		else mySettings = context.getSharedPreferences(PREFS,0);
		Boolean boot=mySettings.getBoolean("boot",true);
		if (boot) 
		{ 
			SharedPreferences.Editor editor = mySettings.edit();
			editor.putString("non","null");
			editor.putString("nci","null");
			editor.putString("son","null");
			editor.putString("sci","null");
			editor.putString("non2","null");
			editor.putString("nci2","null");
			editor.putString("son2","null");
			editor.putString("sci2","null");
			editor.commit();
			context.startService(new Intent(context,RoamingInfoService.class));
		}
	}
}