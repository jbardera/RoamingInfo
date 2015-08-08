/**
 * @author      Joanmi Bardera <joanmibb@gmail.com>
 *          
 */

package com.brapeba.roaminginfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StaticConChgReceiver extends BroadcastReceiver 
{
	@Override public void onReceive(Context context, Intent intent) 
	{
		context.startService(new Intent(context,RoamingInfoService.class));
	}
}