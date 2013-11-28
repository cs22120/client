package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ConfrimUploadActivity extends Activity {

	private Context context;
	private ImageButton helpButton;
	private Button confirmButton, cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confrim_upload);
		context=this;
		addOnClickListeners();
	}

	public void addOnClickListeners(){


		helpButton = (ImageButton) findViewById(R.id.confirmUploadIHelpButton);

		helpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, HelpScreen.class);
				startActivity(intent);
			}
		});


	}
	
	public void EvaluateUserAction() {
		
		
	}
	
	public void uploadToServer() {
		
		
	}
	
	public void showUploadMessageToUser() {
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confrim_upload, menu);
		return true;
	}

}
