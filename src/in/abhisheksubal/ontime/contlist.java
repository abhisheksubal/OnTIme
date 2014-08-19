package in.abhisheksubal.ontime;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class contlist extends ListActivity {
	public String[] arr ;
	public String[] name;
	public String[] start;
	public String[] end;
	public String[] code;
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Bundle b = new Bundle();
		b.putString("name", name[position]);
		b.putString("starts", start[position]);
		b.putString("ends", end[position]);
		Intent information = new Intent("android.intent.action.CONTESTINFO");
		information.putExtras(b);
		startActivity(information);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bund = new Bundle();
		bund=getIntent().getExtras();
		arr=bund.getStringArray("ourlist");
        name= new String[(arr.length)/4];
        start= new String[(arr.length)/4];
        end= new String[(arr.length)/4];
        code=new String[(arr.length)/4];
        int i =0,j=0;
        while(i<arr.length-1)
        {
         code[j]=arr[i];
         start[j]=arr[i+2];
         end[j]=arr[i+3];
         name[j]=arr[i+1];
         i=i+4;
         j=j+1;
        }
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String> (contlist.this,R.layout.row,code));
		
	}
 
}
