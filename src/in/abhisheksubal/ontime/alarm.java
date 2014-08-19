package in.abhisheksubal.ontime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class alarm extends BroadcastReceiver {
// class to start our lookup service at regular intervals
	AlarmManager alarmMgr;
	PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent startIntent = new Intent(context, lookup.class);

		context.startService(startIntent);
		// TODO Auto-generated method stub

	}

	public void setAlarm(Context context) {
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, alarm.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		Toast.makeText(context, "Alarm is being set", Toast.LENGTH_SHORT)
				.show();
		// here we are setting up an alarm which will broad cast an intent to
		// start our
		// look up service
		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 100,
				30*60*1000, alarmIntent);
		//We have added a boot receptor class  
		// Enable {@code SampleBootReceiver} to automatically restart the alarm
		// when the
		// device is rebooted.

	}
}
