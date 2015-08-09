/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

public class ShutDataNot extends Activity 
{
	final String PREFS = "MyPrefs";
	SharedPreferences mySettings;
	
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override protected void onStart() 
	{
		super.onStart();
	}

	@Override protected void onRestart() 
	{
		super.onRestart();
	}

	@Override protected void onResume() 
	{
		super.onResume();
		if (Build.VERSION.SDK_INT>=11) mySettings = getSharedPreferences(PREFS, MODE_MULTI_PROCESS);
		else mySettings = getSharedPreferences(PREFS,0);
		SharedPreferences.Editor editor = mySettings.edit();
		editor.putBoolean("showdata",false);
		editor.commit();
		finish();
	}
}