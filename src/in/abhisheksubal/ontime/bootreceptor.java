package in.abhisheksubal.ontime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class bootreceptor extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			alarm al = new alarm();
				al.setAlarm(context);
            // Here we have started alarm if the device has been restarted
			// this was not working as last tested on 29-03-2014
			// a receiver was not registered in Manifest and it has been registered on 14-5-2014
			//not working on 14-05-2014
        }
		
	}

}
