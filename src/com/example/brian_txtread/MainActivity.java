package com.example.brian_txtread;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		/*add by v1.0 begin*/
		menu.add(0,0,0,"open file");
		menu.add(0,1,1,"exit");
		/*add by v1.0 end*/
		return super.onCreateOptionsMenu(menu);
		
	}
	/*add by v1.0 begin*/
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
		case 0:	
			Intent it = new Intent();
			it.setClass(MainActivity.this, OtherActivity.class);
			startActivity(it);
			finish();
		case 1:
			//finish();
			break;
		}
		return true;
	}
	/*add by v1.0 end*/

}
