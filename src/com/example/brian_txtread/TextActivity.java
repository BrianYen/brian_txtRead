/*add by v1.4*/
package com.example.brian_txtread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class TextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		
		//Find the view by its id
		TextView tv = (TextView)findViewById(R.id.test_read);
	
		Intent intent = this.getIntent();
		//取得Bundle
		Bundle bundleGet = intent.getExtras();
		// 取得 Bundle 中的資料
	    String path = bundleGet.getString( "filepath" );
	    //String name = bundleGet.getString( "filename" ); mask by v1.5
        /*mask by v1.4 begin*/
	    //Find the directory for the SD Card using the API
		//*Don't* hardcode "/sdcard"
		//File sdcard = Environment.getExternalStorageDirectory();
	    /*mask by v1.4 end*/
		//Get the text file
		//File file = new File(path,name); mask by v1.5
	    File file = new File(path);

		//Read text from file
		StringBuilder text = new StringBuilder();

		try {
		    @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
		}
		catch (IOException e) {
			Toast.makeText(
					TextActivity.this,
					getResources().getString(R.string.err_file),
					Toast.LENGTH_LONG
				  ).show();
		}
		//Set the text
		tv.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text, menu);
		return true;
	}

}
