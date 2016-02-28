package sumyiren.qrwritten;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class enterurllink extends Activity {

    private EditText inputurl;
    private Button btnurl;
    private String imagefilename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_enterurllink);

        Intent intent = getIntent();
        imagefilename= intent.getStringExtra("imagefilename");
        Log.d("imagefilane",imagefilename);
        inputurl = (EditText) findViewById(R.id.inputurl);

        btnurl = (Button) findViewById(R.id.btnurl);


        btnurl.setOnClickListener(new View.OnClickListener(){


            public void onClick(View view){

                new MyAsyncTask().execute(imagefilename, inputurl.getText().toString());


                String text = "URL submitted!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                Intent i = new Intent(getApplicationContext(), ChooseActivity.class);
                startActivity(i);

            }
        });




    }


    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0], params[1]);
            Log.v("uuid", "sent");
            return null;
        }

        protected void onPostExecute(Double result) {

            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String imagefilename, String inputurl) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.137.1:8081//writeIT//inputurl.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("imagefilename", imagefilename));
                nameValuePairs.add(new BasicNameValuePair("inputurl", inputurl));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }

}
