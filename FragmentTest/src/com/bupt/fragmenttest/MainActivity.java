package com.bupt.fragmenttest;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(this);
	}

	public void onClick(View v){
		switch(v.getId()){
		case R.id.button:
			AnotherRightFragment fragment = new AnotherRightFragment();
		    FragmentManager fragmentManager = getFragmentManager();
		    FragmentTransaction transaction = fragmentManager.beginTransaction();
		    transaction.replace(R.id.right_layout,fragment);
		    transaction.addToBackStack(null);
		    transaction.commit();
		    break;
		    default:
		    	break;
		}
		
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
