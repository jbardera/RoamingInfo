/* 
 * app icon from http://crazeric.deviantart.com/ - CC license
 * 
 */


package com.brapeba.roaminginfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity 
{
	
	@Override public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startService(new Intent(MainActivity.this,RoamingInfoService.class));
		finish();
	}
}