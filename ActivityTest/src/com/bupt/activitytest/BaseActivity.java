package com.bupt.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getClass().getSimpleName());
		ActivityCollector.addActivity(this);
	}
	protected void onDestroy(){
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
