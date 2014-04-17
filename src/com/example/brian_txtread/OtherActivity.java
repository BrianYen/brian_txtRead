package com.example.brian_txtread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends ListActivity {
	/*
	 * add by v1.1 begin
	 */
	/*物件宣告*/
	private List<String> items = null; 	//items：存放顯示的名稱
	private List<String> paths = null; 	//paths：存放檔案路徑
	//private String rootPath = "/";
	private String rootPath = Environment.getExternalStorageDirectory().getPath(); 		//rootPath：起始目錄, 指向Sdcard
	private TextView mPath;				//mPath:TextView名稱

	/* 取得檔案架構的method */
	private void getFileDir(String filePath)
	{
		/* 設定目前所在路徑 */
		mPath.setText(filePath);
		
		/*建立元件*/
		items=new ArrayList<String>();
		paths=new ArrayList<String>();  
		File f=new File(filePath);  
		File[] files=f.listFiles();

			if(!filePath.equals(rootPath))
			{
				/* 第一筆設定為[回到根目錄] */
				items.add("Back to "+rootPath);
				paths.add(rootPath);
				/* 第二筆設定為[回上層] */
				items.add("Back to ../");
				paths.add(f.getParent());
			}
			/* 將所有檔案加入ArrayList中 */
			for(int i=0;i<files.length;i++)
			{
				File file=files[i];
				items.add(file.getName());
				paths.add(file.getPath());
			}
			
			setListAdapter(new MyAdapter(this,items,paths));
		/*mask by v1.2 begin*/
		/* 宣告一ArrayAdapter，使用file_row這個Layout，
        /         	並將Adapter設定給此ListActivity 
		ArrayAdapter<String> fileList = 
				new ArrayAdapter<String>(this,R.layout.file_row, items);
		setListAdapter(fileList);*/
		/*mask by v1.2 end*/
	}
	/* 設定ListItem被按下時要做的動作 */
	@Override
	protected void onListItemClick(ListView l,View v,int position,long id)
	{
		File file = new File(paths.get(position));
		//File fileName = new File(items.get(position));
		if(file.canRead())
		{
			if (file.isDirectory())
			{
				try
				{
					/* 如果是資料夾就再進去撈一次 */
					getFileDir(paths.get(position));
				}
				catch(Exception e)
				{
					Toast.makeText(
									OtherActivity.this,
									getResources().getString(R.string.err_folder),
									Toast.LENGTH_LONG
							      ).show();
				}
				
			}
			else
			{
				//openFile(file);//add by v1.2 ,mask by v1.3
				
				/*mask by v1.2 begin
				// 如果是檔案，則跳出AlertDialog 
				new AlertDialog.Builder(this)
					.setTitle("Message")
					.setMessage("["+file.getName()+"] is File!")
					.setPositiveButton("OK",
	            new DialogInterface.OnClickListener()
	            {
					public void onClick(DialogInterface dialog,int which)
					{
						//break
					}
	            }).show();   
	            mask by v1.2 end*/   				
				/*add by v1.3 begin*/
				try
				{
					fileHandle(file); 
				}
				catch(Exception e)
				{
					Toast.makeText(
									OtherActivity.this,
									getResources().getString(R.string.err_file),
									Toast.LENGTH_LONG
								  ).show();
				}
				/*add by v1.3 end*/
			}	
		}
		else
		{
			/* 跳出AlertDialog顯示權限不足 */
			new AlertDialog.Builder(this)
	        	.setTitle("Message")
	        	.setMessage("權限不足!")
	        	.setPositiveButton("OK",
	        new DialogInterface.OnClickListener()
	        {
	        	public void onClick(DialogInterface dialog,int which)
	            {
	        		//break
	            }
	        }).show();     
		}
	}
	/*
	 * add by v1.1 end
	 */
	/*
	 * add by v1.2 begin
	 */
	/* 在手機上開啟檔案的method */
	private void openFile(File f) 
	{
		Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setAction(android.content.Intent.ACTION_VIEW);
	    
	    /* 呼叫getMIMEType()來取得MimeType */
	    String type = getMIMEType(f);
	    /*mask by v1.4 begin
	    /* 設定intent的file與MimeType 
	    intent.setDataAndType(Uri.fromFile(f),type);
    	startActivity(intent); 
    	mask by v1.4 end*/
	    /*add by v1.4 begin*/
	    if (type == "txt")
	    {
	    	Bundle bundle = new Bundle();
	    	Intent txt = new Intent();
	    	//txt.setClass(OtherActivity.this, TextActivity.class);// mask by v1.5
	    	txt.setClass(OtherActivity.this, TextActivity.class);// add by v1.5
	    	bundle.putString("filepath",f.getPath()); //將路徑檔名放入
	    	//bundle.putString("filename",f.getName()); //將檔名放入 mask by v1.5
	    	Log.d("LOG","位置:"+f.getPath());
	    	//Log.d("LOG","檔名:"+f.getName()); mask by v1.5
	    	txt.putExtras(bundle); //將參數放入
			startActivity(txt);
			finish();
	    }
	    else
	    {
	    	intent.setDataAndType(Uri.fromFile(f),type);
	    	startActivity(intent); 
	    }
	    /*add by v1.4 end*/
	}

	/* 判斷檔案MimeType的method */
	private String getMIMEType(File f)
	{
	    String type="";
	    String fName=f.getName();
	    /* 取得副檔名 */
	    String end=fName.substring(fName.lastIndexOf(".")+1,
	                               fName.length()).toLowerCase(Locale.getDefault()); 
	    
	    /* 依附檔名的類型決定MimeType */
	    if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
	       end.equals("xmf")||end.equals("ogg")||end.equals("wav"))
	    {
	    	type = "audio"; 
	    }
	    else if(end.equals("3gp")||end.equals("mp4"))
	    {
	    	type = "video";
	    }
	    else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
	            end.equals("jpeg")||end.equals("bmp"))
	    {
	    	type = "image";
	    }
	    /*add by v1.4 begin*/
	    else if(end.equals("txt"))
	    {
	    	return "txt";
	    }
	    /*add by v1.4 end*/
	    else
	    {
	    	type="*";
	    }
	    /* 如果無法直接開啟，就跳出軟體清單給使用者選擇 */
	    type += "/*"; 
	    return type; 
	}
	/*
	 * add by v1.2 end
	 */
	/*
	 * add by v1.3 begin
	 */
	private void fileHandle(final File file)
	{
		//開啟檔案按鈕對話框
		AlertDialog.Builder openMessage = new AlertDialog.Builder(OtherActivity.this);
		openMessage.setTitle(R.string.Ale_title);
		//open
		openMessage.setPositiveButton(R.string.Ale_open,new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog,int which) 
			{
				openFile(file);
			}
		});
		//close
		openMessage.setNegativeButton(R.string.Ale_no,new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichButton)
			{
				// TODO Auto-generated method stub
				//break
			}
		});
		openMessage.show();
	}
	/*
	 * add by v1.3 end
	 */
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle icicle)
	{
		super.onCreate(icicle); //add by v1.1
	   
		/* 載入main.xml Layout */
		setContentView(R.layout.activity_other);
		mPath=(TextView)findViewById(R.id.mPath);
	   
		getFileDir(rootPath); //add by v1.1
	 }
	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other, menu);
		return true;
	}

}
