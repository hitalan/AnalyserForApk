package com.bupt.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
//	Log.d("FirstActivity", this.toString());
	Log.d("FirstActivity", "Task id is "+getTaskId());
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.first_layout);
	Button button1 = (Button)findViewById(R.id.button_1);
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Toast.makeText(FirstActivity.this, "You click Button 1", Toast.LENGTH_SHORT).show();
		}
	});*/
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
		
		
		
	});*/
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
			Intent intent = new Intent("com.bupt.activitytest.ACTION_START");
			intent.addCategory("com.bupt.activitytest.MY_CATEGORY");
			startActivity(intent);
		}
		
		
	});*/
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		/*	Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http://www.baidu.com"));*/
		/*	Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:10086"));
			startActivity(intent);
		}
		
		
		
	});*/
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String data = "Hello SecondActivity";
			Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
			intent.putExtra("extra_data", data);
			startActivity(intent);
		}
	});*/
	/*button1.setOnClickListener(new OnClickListener(){
		
		public void onClick (View v){
		  Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
		  startActivityForResult(intent,1);
		}
		
		
		
		
	});*/
	/*button1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(FirstActivity.this,FirstActivity.class);
			startActivity(intent);
		}
		
		
	});*/
	/*button1.setOnClickListener(new OnClickListener(){
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
		startActivity(intent);
	}
	
	
});*/
	button1.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			SecondActivity.actionStart(FirstActivity.this, "data1", "data2");
		}
	});
}
	
   protected void onActivityResult(int requestCode, int resultCode ,Intent data){
	   switch(requestCode){
	   case 1:
		   if(resultCode==RESULT_OK){
			   
			   String returnedData = data.getStringExtra("data_return");
			   Log.d("FirstActivity",returnedData);
		   }
		   break;
		   default:
	   }
   }


	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		
		case R.id.add_item:
			Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
		    break;
		 default:
		}
		return true;
	}
	
	
	protected void onRestart(){
		super.onRestart();
		Log.d("FirstActivity","onRestart");
	}
}
