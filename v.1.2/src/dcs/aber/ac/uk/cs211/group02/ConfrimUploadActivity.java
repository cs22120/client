package dcs.aber.ac.uk.cs211.group02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ConfrimUploadActivity extends Activity {

	private Context context;
	private ImageButton helpButton;
	private Button confirmButton, cancelButton;
	private String result = "", walkName, walkSDesc, walkLdesc;

	private TextView nameTextView, shortDescTextView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confrim_upload);
		context = this;
		addOnClickListeners();
		locateUIElements();
		Bundle bundle = this.getIntent().getExtras();

		if(bundle != null){

			walkName = bundle.getString("walk name");
			walkSDesc = bundle.getString("walk short");
			walkLdesc = bundle.getString("walk long");

			nameTextView.setText(walkName);
			shortDescTextView.setText(walkSDesc);

		}



	}

	public void buildUserPrompt() {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					uploadToServer(WalkRecording.getPois());
					showUploadMessage();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					buildDeletionPrompt();

					break;
				}
			}

			
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Which do you want to do?").setPositiveButton("Upload walk"
				, dialogClickListener)
				.setNegativeButton("Delete walk", dialogClickListener).show();
	}

	public void buildDeletionPrompt(){

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					break;

				case DialogInterface.BUTTON_NEGATIVE:

					break;
				}
			}
		};


		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Deleting the walk will delete al information"
				+ "associated with the walk, including any images/points on"
				+ "the walk already. Continue anyway?").setPositiveButton("Yes"
						, dialogClickListener)
						.setNegativeButton("No", dialogClickListener).show();

	}


	private void showUploadMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Upload complete").show();
		
	}
	
	public void locateUIElements(){

		nameTextView = (TextView) findViewById(R.id.confirmUploadWalkTitleValueTextView);

		shortDescTextView = (TextView) findViewById(R.id.confirmUploadWalkDescValueTextView);

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



		confirmButton = (Button) findViewById(R.id.confrimUploadConfirmButton);

		confirmButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){
				buildUserPrompt();
			}
		});


	}

	public void uploadToServer(Vector<PointOfInterest> pois)  {

		try {
			JSONObject parent = new JSONObject();

			JSONObject auth = new JSONObject();
			JSONObject walk = new JSONObject();

			JSONArray points = new JSONArray();
			JSONArray images = new JSONArray();



			auth.put("hash","3b6decebf0bab0e0a08c18a94849d6df1e536d65");
			auth.put("salt", "dave");

			for(int i = 0; i < pois.size(); i ++){

				JSONObject location = new JSONObject();
				JSONArray descriptions = new JSONArray();

				location.put("name", pois.get(i).getName());
				location.put("timestamp", 0);
				location.put("latitude",pois.get(i).getLattitude());
				location.put("longitude",pois.get(i).getLongitude());
				descriptions.put(pois.get(i).getDescription());

				images.put(pois.get(i).getImagePath());
				location.put("time stamp", pois.get(i).getTimeStamp());
				location.put("images", images);
				location.put("descriptions", descriptions);

				points.put(location);


			}

			walk.put("locations", points);
			walk.put("title", walkName);
			walk.put("shortDesc", walkSDesc);
			walk.put("longDesc", walkLdesc);

			parent.put("walk", walk);
			parent.put("authorization", auth);

			UploadASyncTask upload = new UploadASyncTask();
			upload.execute(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confrim_upload, menu);
		return true;
	}

	private class UploadASyncTask extends AsyncTask<JSONObject, Void, Void>{

		@Override
		protected Void doInBackground(JSONObject...json) {
			try{

				HttpParams params = new BasicHttpParams();

				HttpClient httpclient = new DefaultHttpClient(params);

				HttpPost httpPost = new HttpPost("http://users.aber.ac.uk/thk11/chris/server/upload.php");

				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				postParams.add(new BasicNameValuePair("data", json[0].toString()));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
				entity.setContentEncoding(HTTP.UTF_8);
				httpPost.setEntity(entity);

				httpPost.setHeader("User-Agent", "WalkingTourCreator/1.0");

				HttpResponse httpResponse = httpclient.execute(httpPost);

				InputStream inputStream = httpResponse.getEntity().getContent();


				if(inputStream != null){
					result = convertInputStreamToString(inputStream);

				}
				else{
					result = "Did not work!";

				}

				Log.d("AUTH OBJECT", json[0].toString());
				Log.d("RESULT", result);


			}catch(Exception e){

				Log.e("ERROR IN SEVER UPLOAD", e.getMessage());
			}
			return null;


		}


	}

}
