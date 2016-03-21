package com.bupt.uiwidgettest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
    private Button button;		
    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(this);
		editText = (EditText)findViewById(R.id.edit_text);
		imageView = (ImageView)findViewById(R.id.image_view);
		progressBar = (ProgressBar)findViewById(R.id.progress_bar);
		/*button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button:
			/*String inputText = editText.getText().toString();
			Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();*/
			//imageView.setImageResource(R.drawable.test);
			/*if(progressBar.getVisibility()==View.GONE)
				progressBar.setVisibility(View.VISIBLE);
			else
				progressBar.setVisibility(View.GONE);*/
		/*	int progress = progressBar.getProgress();
			progress = progress+10;
			progressBar.setProgress(progress);*/
			/*AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
			dialog.setTitle("This is Dialog");
			dialog.setMessage("Something important.");
			dialog.setCancelable(false);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.setNegativeButton("CanCel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			dialog.show();*/
			ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle("This is progressDialog");
			progressDialog.setMessage("Loading...");
			progressDialog.setCancelable(true);
			progressDialog.show();
			
			break;
			default:
				break;
		
		
		
		}
		
	}

}
