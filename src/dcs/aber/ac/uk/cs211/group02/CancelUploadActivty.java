package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CancelUploadActivty extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_upload_activty);
	}

	public void EvaluateUserAction() {


	}

	public void returnToMapView() {


	}

	public void deleteWalk() {


	}

	public void showCancelMessageToUser() {


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cancel_upload_activty, menu);
		return true;
	}



}
