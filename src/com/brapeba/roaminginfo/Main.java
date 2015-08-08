/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 * app icon from http://crazeric.deviantart.com/ - CC license
 * 
 */

package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity 
{
	final String PREFS = "MyPrefs";
	SharedPreferences mySettings;
	
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mySettings = getSharedPreferences(PREFS, MODE_MULTI_PROCESS);
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
		//broadcastreceiver defined statically at manifest but we need to re-enable at start because
		//otherwise when the package is renewed (App is updated) it won't trigger
		ComponentName receiver = new ComponentName(this, StaticConChgReceiver.class);
	    PackageManager pm = this.getPackageManager();
	    pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
	    startService(new Intent(this,RoamingInfoService.class));
		Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
		moveTaskToBack(true);
		//finish();
	}
	
	@Override protected void onRestart() 
	{
		super.onRestart();
		Intent iMS = new Intent(this, Settings.class);
		startActivity(iMS);
	}
}