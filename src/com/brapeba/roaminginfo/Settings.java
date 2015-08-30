/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity 
{
	ToggleButton tBsound,tBvibrate,tBshowdata;
	Button btn;
	SharedPreferences mySettings;
	final String PREFS = "MyPrefs";
	SharedPreferences.Editor editor;
	
    @Override public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
   		setContentView(R.layout.settings);
		tBsound=(ToggleButton)findViewById(R.id.esound);
		tBvibrate=(ToggleButton)findViewById(R.id.evibrate);
		tBshowdata=(ToggleButton)findViewById(R.id.edata);
		if (Build.VERSION.SDK_INT>=11) mySettings = getSharedPreferences(PREFS, MODE_MULTI_PROCESS);
		else mySettings = getSharedPreferences(PREFS,0);
		tBsound.setChecked(mySettings.getBoolean("sound",true));
		tBvibrate.setChecked(mySettings.getBoolean("vibrate",true));
		tBshowdata.setChecked(mySettings.getBoolean("showdata",true));
		editor=mySettings.edit();
		tBsound.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) 
		{
			//save settings
			if (tBsound.isChecked()) editor.putBoolean("sound",true); else editor.putBoolean("sound",false); 
			editor.commit();
		}
		});
		tBvibrate.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) 
		{
			//save settings
			if (tBvibrate.isChecked()) editor.putBoolean("vibrate",true); else editor.putBoolean("vibrate",false); 
			editor.commit();
		}
		});
		tBshowdata.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) 
		{
			//save settings
			if (tBshowdata.isChecked()) editor.putBoolean("showdata",true); else editor.putBoolean("showdata",false); 
			editor.commit();
		}
		});
		btn= (Button) findViewById(R.id.ssexit);
		btn.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) 
		{
			moveTaskToBack(true);
		}
	});
	}
    
    @Override public void onBackPressed()
    {
        moveTaskToBack(true);
    }
    
    @Override public void onPause() 
    {
    	startService(new Intent(this,RoamingInfoService.class));
    	Toast.makeText(this, getString(R.string.string4), Toast.LENGTH_SHORT).show();
    	super.onPause();
    }
}
