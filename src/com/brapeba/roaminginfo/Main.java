/* 
 * app icon from http://crazeric.deviantart.com/ - CC license
 * 
 */


package com.brapeba.roaminginfo;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
		startService(new Intent(this,RoamingInfoService.class));
		Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
		finish();
	}
}