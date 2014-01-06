package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ServerResponse extends Activity {

	private String resp;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_response);
		Intent i = getIntent();
		resp = i.getExtras().getString("response");

		setTextValue();

	}

	public void setTextValue() {

		textView = (TextView) findViewById(R.id.serverResponseTextField);

		textView.setText(resp);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server_response, menu);
		return true;
	}

}
