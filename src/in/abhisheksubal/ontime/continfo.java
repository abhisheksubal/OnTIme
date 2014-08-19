package in.abhisheksubal.ontime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class continfo extends Activity {
    public String name,start,end;
    TextView names,starts,ends;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contesti);
		names= (TextView)findViewById(R.id.tv2);
		starts=(TextView) findViewById(R.id.tv4);
		ends=(TextView) findViewById(R.id.tv6);
		
		Bundle bu = new Bundle();
		bu=getIntent().getExtras();
		name=bu.getString("name");
		start=bu.getString("starts");
		
		end=bu.getString("ends");
		names.setText(name);
		
		if(Build.VERSION.SDK_INT >=14)
		names.setTextColor(getResources().getColor(android.R.color.white));
		ends.setText(end);
		starts.setText(start);
		
	}

}
