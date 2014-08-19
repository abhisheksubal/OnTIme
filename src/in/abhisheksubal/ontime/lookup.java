package in.abhisheksubal.ontime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

public class lookup extends IntentService {
	public lookup() {
		super("lookup");
		// TODO Auto-generated constructor stub
	}

	String url = "http://www.codechef.com/contests";
	public static String read;
	StringBuilder sb;
	public int i = 0;
	static Elements texty;
	static File output1;

	FileOutputStream fos;
	Document document;
	OutputStreamWriter os;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		String[] temp = null;
		File output1 = new File(Environment.getExternalStorageDirectory(),
				"abhisheksubal");
		File output = new File(output1, "service.txt");

		// File output = new File(output1, "service.txt");
		if (!output.exists()) {
			try {
				output.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (isOnline()) {
			try {
				temp = getdata();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				if ((checkinfile(output, temp))) {
					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							this)
							.setSmallIcon(R.drawable.ic_launcher)
							.setContentTitle("New Contest")
							.setSound(
									RingtoneManager
											.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
							.setContentText(
									"Check for New Contest on Code Chef");

					Notification n = mBuilder.build();
					NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

					notificationManager.notify(0, n);
					writetofile(output, temp);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public String[] getdata() throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements texty = document
				.select(".table-questions:first-of-type tr td ");
		String[] tr = new String[texty.size()];
		ListIterator<Element> it = texty.listIterator();
		int i = 0;
		while (it.hasNext()) {
			tr[i] = it.next().text();
			i = i + 1;

		}
		return tr;
	}

	public void writetofile(File output, String[] tr)
			throws NullPointerException {
		int y = 0;
		try {
			fos = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		os = new OutputStreamWriter(fos);
		while (y < (tr.length - 1)) {

			try {
				os.append(tr[y]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			y = y + 4;

		}
		try {
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	boolean checkinfile(File output, String[] tr) throws IOException {
		BufferedReader inputReader2 = new BufferedReader(new InputStreamReader(
				new FileInputStream(output)));
		String inputString2;

		StringBuffer stringBuffer2 = new StringBuffer();
		while ((inputString2 = inputReader2.readLine()) != null) {
			stringBuffer2.append(inputString2);
		}
		String read = stringBuffer2.toString();
		if (read != null) {
			int x = 0;
			for (x = 0; x < tr.length; x += 4) {

				if (!(read.contains(tr[x]))) {
					return true;

				}
			}
			return false;
		} else
			return false;
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

}
