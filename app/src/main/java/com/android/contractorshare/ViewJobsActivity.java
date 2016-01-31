package com.android.contractorshare;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Roger on 30/01/2016.
 */
public class ViewJobsActivity extends ListActivity {

    static final String[] Jobs =
            new String[]{};
    public ArrayList<HashMap<String, String>> jobsList;
    private ListView listView;
    private GetUserJobsTask mAuthTask = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobsList = new ArrayList<HashMap<String, String>>();
        int clientId = getIntent().getExtras().getInt("UserId");
        GetClientJobs(clientId);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.job_item, Jobs));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void GetClientJobs(int clientId) {
        mAuthTask = new GetUserJobsTask(clientId);
        mAuthTask.execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetUserJobsTask extends AsyncTask<Void, Void, Boolean> {
        private final int mClientId;

        public GetUserJobsTask(int clientId) {
            mClientId = clientId;
        }

        //TODO: Unify that and the Login class into a separate FindMyHandyMan client class: Same way it's done in here: http://loopj.com/android-async-http/
        public void invokeJobsWS(JSONObject params, int clientId) {
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            String webServiceUrl = "http://contractorshare.apphb.com/ContractorShare/GetMyCurrentServices?clientId=" + clientId;
            Log.v("Trying to call: %s", webServiceUrl);

            Context context = getApplicationContext();
            client.addHeader("content-type", "application/json");
            client.get(context, webServiceUrl,
                    new AsyncHttpResponseHandler(Looper.getMainLooper()) {
                        // When the response returned by REST is  '200'
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            try {
                                Log.v("Success: Response code", String.valueOf(statusCode));
                                // JSON Object
                                JSONArray jsonArray = new JSONArray(new String(response, "UTF-8"));
                                ParseJsontoHashMap(jsonArray);
                                // When the JSON response has status boolean value assigned with true

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                                e.printStackTrace();

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        private void ParseJsontoHashMap(JSONArray jsonArray) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jobObj = jsonArray.getJSONObject(i);
                                    String address = jobObj.getString("Address");
                                    String categoryId = jobObj.getString("CategoryId");
                                    String city = jobObj.getString("City");
                                    String name = jobObj.getString("Name");
                                    String clientId = jobObj.getString("ClientId");
//                                  String contractorId = jobObj.getString("ContractorId");


                                    // tmp hashmap for single contact
                                    HashMap<String, String> job = new HashMap<String, String>();

                                    // adding each child node to HashMap key => value
                                    job.put("Address", address);
                                    job.put("CategoryId", categoryId);
                                    job.put("City", city);
                                    job.put("ClientId", clientId);
                                    job.put("Name", name);
                                    // adding contact to contact list
                                    jobsList.add(job);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Log.v("Failure: Response code", String.valueOf(statusCode));
                            if (statusCode == 404) {
                                Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                            }
                            // When Http response code is '500'
                            else if (statusCode == 500) {
                                Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                            }
                            // When Http response code other than 404, 500
                            else {
                                Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JSONObject jsonParams = new JSONObject();

            invokeJobsWS(jsonParams, mClientId);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ViewJobsActivity.this, jobsList,
                    R.layout.job_item, new String[]{"Name", "City",
                    "ClientId"}, new int[]{R.id.name,
                    R.id.city, R.id.client_id});

            setListAdapter(adapter);
        }

        @Override
        protected void onCancelled() {

        }
    }
}
