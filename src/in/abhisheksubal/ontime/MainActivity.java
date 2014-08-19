package in.abhisheksubal.ontime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	String name = "activtiy.txt";
	File output;
	Button updateB, button1;
	String url = "http://www.codechef.com/contests";
	ProgressDialog mProgressDialog;
	FileOutputStream fos;
	public Intent servi;
	AlarmManager alrm;
	PendingIntent intr;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		File output1 = new File(Environment.getExternalStorageDirectory(),
				"abhisheksubal");
		output1.mkdirs();

		File output = new File(output1, "activity.txt");

		IntentFilter iff = new IntentFilter();
		iff.addAction("in.abhisheksubal.ontime.SendBroadcast");
		updateB = (Button) findViewById(R.id.button2);

		try {
			fos = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (isOnline()) {
					new Title().execute();
				} else {
					Toast.makeText(getApplicationContext(),
							"your network connection is pissing you off :P",
							Toast.LENGTH_LONG).show();
				}
			}

		});

		updateB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alarm al = new alarm();
				al.setAlarm(getApplicationContext());
			}
		});
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	protected void onResume() {
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Title AsyncTask
	public class Title extends AsyncTask<Void, Void, Void> {
		public String title = "";
		// public String[] rows=new String[100];
		public int i = 0;
		public String[] tr;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setTitle("Code Chef");
			mProgressDialog.setCancelable(true);
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setIcon(R.drawable.ic_launcher);
			mProgressDialog.setMessage("Connecting...");
			mProgressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				Document document = Jsoup.connect(url).get();
				Elements texty = document
						.select(".table-questions:first-of-type tr td  ");
				OutputStreamWriter os = new OutputStreamWriter(fos);
				tr = new String[texty.size()];
				ListIterator<Element> it = texty.listIterator();

				while (it.hasNext()) {
					tr[i] = it.next().text();
					i++;

				}
				int y = 0;
				while (y < tr.length) {

					os.append(tr[y]);
					y = y + 4;

				}
				os.flush();
				os.close();

				Intent ll = new Intent(MainActivity.this, contlist.class);
				Bundle bund = new Bundle();
				bund.putStringArray("ourlist", tr);
				ll.putExtras(bund);
				startActivity(ll);

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			mProgressDialog.dismiss();

		}
	}

}
