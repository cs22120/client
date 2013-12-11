package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditWalksInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_walks_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_walks_info, menu);
		return true;
	}
	
	public String getWalkTitleText() {

		return "DEFAULT VALUE";
	}

	public String getWalkShortDescText() {

		return "DEFAULT VALUE";
	}

	public String getWalkLongDescext() {

		return "DEFAULT VALUE";
	}

}
