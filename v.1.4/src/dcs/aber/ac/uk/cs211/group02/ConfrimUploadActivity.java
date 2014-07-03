package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Activity handles the final stages and function requirements of the project
 * specification. This Activity provides the utility and tools required in order
 * to upload a recorded work to the server, ready for viewing on the Walking Tours
 * web application.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
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
	private Button confirmButton;
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

		/* If there are no extras passed to this intent do not attempt this code*/
		if(bundle != null){

			walkName = bundle.getString("walk name");
			walkSDesc = bundle.getString("walk short");
			walkLdesc = bundle.getString("walk long");

			nameTextView.setText(walkName);
			shortDescTextView.setText(walkSDesc);

		}



	}

	/**
	 * This method displays a alertDialog prompt with negative/positive 
	 * user interaction options. This DialogInterface has its on onClickListener
	 * and performs different actions depending on the nature of the
	 * selected response (the negative response has a different set of results
	 * to the positive response).
	 * 
	 * If the positive option is displayed then the walk will be uploaded to the server.
	 * If the negative option is displayed, then the buildDeletionPrompt() method will be called.
	 *
	 */

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

	/**
	 * 
	 * This method is only called when the negative action is chosen in the
	 * buildUserPrompt() method. This method again produces another user prompt,
	 * with both negative and positive responses, and it also has its own
	 * custom DialogInter OnClickListener.
	 * 
	 */

	public void buildDeletionPrompt(){

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					/* deliberately NULL body*/
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

	/**
	 * This method displays a message to the user confirming the upload action has
	 * taken place. This method does not perform any server upload error checking.
	 * 
	 */

	private void showUploadMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Upload complete").show();

	}

	/**
	 * This method is used to locate and instantiate the user interface elements
	 * which do not require any special listeners or onClick events.
	 * 
	 * The main UI elements this method will deal with it TextView's and EditText's
	 * This method is called after onCreate() in order to ensure there are no NullPointerExceptions
	 * when interacting with the user interface.
	 * 
	 */


	public void locateUIElements(){

		nameTextView = (TextView) findViewById(R.id.confirmUploadWalkTitleValueTextView);

		shortDescTextView = (TextView) findViewById(R.id.confirmUploadWalkDescValueTextView);

	}

	/**
	 *This method is designed to enable the user interface to be interactive.
	 *It first locates the UI elements (Buttons) and correct associates them
	 *with the desired onClickListener and resulting action. 
	 * 
	 * Any newly added UI elements that require onClickEvents should be handled
	 * within this method.
	 * 
	 */

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

	/**
	 * This method is responsible for upload the walks data to the server. This
	 * method takes a Vector of all PointsOfInterest on the current walking route as
	 * a parameter argument.
	 * 
	 * The process of server upload in this instance is fairly simple and it uses the
	 * HTTP POST request method of validation as opposed to GET for security reasons.
	 * 
	 * The server expects the walks data to be sent as a JSON Object, which is composed
	 * of several other JSON Objects/Arrays depending on the information it is responsible for.
	 * 
	 * The server upload is then handled through the execution of a AsyncTask, as you cannot perform
	 * network connections through the main or UI Threads in Android development.
	 * 
	 * @param Vector<PointOfInterest> points
	 */

	@SuppressLint("NewApi")
	public void uploadToServer(Vector<PointOfInterest> pois)  {

		try {
			JSONObject parent = new JSONObject();

			JSONObject auth = new JSONObject();
			JSONObject walk = new JSONObject();
			JSONArray points = new JSONArray();
			




			/*This hash and salt are for server validation and security purposes*/
			auth.put("hash","3b6decebf0bab0e0a08c18a94849d6df1e536d65");
			auth.put("salt", "dave");

			for(int i = 0; i < pois.size(); i ++){

				int result = conertTimeStringToSeconds(pois.get(i).getTimeStamp());

				String bitMapEncoded = fileToBitmapAndEncode(pois.get(i).getImage());
				JSONArray imageArray = new JSONArray();
				JSONObject location = new JSONObject();
				JSONArray descriptions = new JSONArray();
				JSONArray subDesc = new JSONArray();
				JSONArray outerArray = new JSONArray();

				location.put("name", pois.get(i).getName());
				location.put("latitude",pois.get(i).getLattitude());
				location.put("longitude",pois.get(i).getLongitude());
				subDesc.put(pois.get(i).getName());
				subDesc.put(pois.get(i).getDescription());
				descriptions.put(subDesc);

				imageArray.put(pois.get(i).getName() + ".jpeg");
				imageArray.put(bitMapEncoded);

				outerArray.put(imageArray);
				location.put("timestamp", result);
				location.put("descriptions", descriptions);
				location.put("images", outerArray);
				points.put(location);
				
				//outerArray.remove(0);
				


			}
			walk.put("locations", points);
			walk.put("title", walkName);
			walk.put("shortDesc", walkSDesc);
			walk.put("longDesc", walkLdesc);


			parent.put("walk", walk);
			parent.put("authorization", auth);

			/*ASyncTask because network connection cannot be made on other Threads*/
			UploadASyncTask upload = new UploadASyncTask();
			upload.execute(parent);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * convertInputStreamToString is used to check the response returned from the server.
	 * This string is then output to the LogCat for the purposes of debugger and checking
	 * data/JSON validity.
	 * 
	 * A BufferedReader is used to provide thread safe parsing of the server repsonse.
	 * 
	 * @param inputStream - server response
	 * @return String - server response in String form
	 * @throws IOException 
	 * @see IOException
	 */

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

	/**
	 * This nested Class is used to handle the network connections. The reason a 
	 * Separate Class is used is because you cannot perform network connection
	 * activities on the Main Thread in Android development, thus you have to
	 * use other methods of doing so.
	 * 
	 * This Class extends AsyncTask<JSONObject, Void, Void> and these parameters should
	 * not be changed. AsyncTask's run in the background, which prevents any possible complications
	 * such as reduced application performance when the user is uploading to the server.
	 * 
	 * The doInBackground method of the AsyncTasks first of all takes one or many JSON Objects,
	 * and locates the URL of the expecting server. It then uses name-value pairs
	 * to send these JSONObjects, over HTTP POST protocols to the expecting server.
	 * 
	 * Finally the servers response is converted from an InputStream into a String, for error checking
	 * and debuggin purposes.
	 * 
	 * @author chris (che16@aber.ac.uk)
	 *
	 */

	private class UploadASyncTask extends AsyncTask<JSONObject, Void, Void>{

		@Override
		protected Void doInBackground(JSONObject...json) {
			try{

				HttpParams params = new BasicHttpParams();

				HttpClient httpclient = new DefaultHttpClient(params);

				HttpPost httpPost = new HttpPost("http://users.aber.ac.uk/che16/php/server/upload.php");

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

	/**
	 * This method turns a PointOfInterests File type,which is used to store the image
	 * into a BitMap image using BitmapFactory. This image in then encoded into a base
	 * 64 String, which can be sent to the server as part of the JSON.
	 * 
	 * @param File - the PointOfInterest's Image
	 * @return String - the base64 encoded Bitmap String
	 */

	private String fileToBitmapAndEncode(File file) {

		Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		Bitmap result = getResizedBitmap(myBitmap, 300,300);
		return getStringFromBitmap(result);

	}

	/**
	 * This method is used to convert a Bitmap into a base 64 encoded String. This
	 * String is then returned and can applied to the JSON Object and sent to the server
	 * using a HTTP POST request protocol.
	 * 
	 * @param Bitmap - the Bitmap you want to encode
	 * @return String - the base64 encoded Bitmap String
	 */

	private static String getStringFromBitmap(Bitmap bitmapPicture) {

		final int COMPRESSION_QUALITY = 100;
		String encodedImage;
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,
				byteArrayBitmapStream);
		byte[] b = byteArrayBitmapStream.toByteArray();
		encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	/**
	 * The method resizes a bitmap based on the given parameters. This is used
	 * when creating the Bitmap for the GoogleMap Marker as a full size image returned
	 * is far too big to comfortably fit on the GoogleMap.
	 * 
	 * @param Bitmap - the Bitmap you wish to resize
	 * @param int - the resulting height of the resized Bitmap 
	 * @param int - the resulting width of the resized Bitmap 
	 * @return Bitmap - the resulting resized Bitmap
	 */
	public Bitmap getResizedBitmap(Bitmap bitmap, int resultHeight, int resultWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleWidth = ((float) resultHeight) / width;
		float scaleHeight = ((float) resultWidth) / height;

		// used in the manipulation
		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
		return resizedBitmap;

	}

	public int conertTimeStringToSeconds(String timeString){

		String[] parts = timeString.split(":");
		int hours = Integer.parseInt(parts[0]);
		int mins = Integer.parseInt(parts[1]);
		int secs = Integer.parseInt(parts[2]);

		int totalTimeSecs = (hours*3600) + (mins*60) + secs;

		return totalTimeSecs;

	}

}
