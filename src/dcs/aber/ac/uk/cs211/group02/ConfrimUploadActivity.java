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
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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



		confirmButton = (Button) findViewById(R.id.confrimUploadConfirmButton);

		confirmButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){
				EvaluateUserAction();
			}
		});


	}

	public void EvaluateUserAction() {

		Toast check = Toast.makeText(context,"TOAST CHECK FOR SEVER UPLOAD", Toast.LENGTH_LONG);

		check.show();

		uploadToServer(WalkRecording.getPois());

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

				images.put("image1");
				images.put("image2");

				location.put("images", images);
				location.put("descriptions", descriptions);

				points.put(location);


			}

			walk.put("locations", points);
			walk.put("title", "test walk title");
			walk.put("shortDesc", "test short desc");
			walk.put("longDesc", "test long desc");

			parent.put("walk", walk);
			parent.put("authorization", auth);







			UploadASyncTask upload = new UploadASyncTask();
			upload.execute(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void showUploadMessageToUser() {



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

				HttpPost httpPost = new HttpPost("http://project.chippy.ch/upload.php");

				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				postParams.add(new BasicNameValuePair("data", json[0].toString()));

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
				entity.setContentEncoding(HTTP.UTF_8);
				httpPost.setEntity(entity);

				httpPost.setHeader("User-Agent", "WalkingTourCreator/1.0");

				HttpResponse httpResponse = httpclient.execute(httpPost);

				InputStream inputStream = httpResponse.getEntity().getContent();
				String result = "";

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