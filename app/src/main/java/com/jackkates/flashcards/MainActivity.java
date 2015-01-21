package com.jackkates.flashcards;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jackkates.flashcards.data.Set;
import com.jackkates.flashcards.quizlet.SetListRequest;

import java.util.List;


public class MainActivity extends ListActivity {

    private final String TAG = "MainActivity";
    List<Set> sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireRequest();
    }


    private void fireRequest() {
        String url = getString(R.string.quizlet_url);

        RequestQueue queue = Volley.newRequestQueue(this);

        SetListRequest request = new SetListRequest(url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                sets = (List<Set>) response;
                updateListView();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Networking Error.");
            }
        });

        queue.add(request);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Set set = sets.get(position);
    }

    private void updateListView() {
        setListAdapter(new QuizletSetListAdapter(this, sets));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void authorizePressed(View view) {
        Uri uri = Uri.parse("https://quizlet.com/authorize?response_type=code&client_id=c29cpp29Gf" +
                "&scope=read&state=RANDOM_STRING&redirect_uri=http://jackkates.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


    }
}
