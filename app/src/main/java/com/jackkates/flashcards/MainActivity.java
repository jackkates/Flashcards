package com.jackkates.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jackkates.flashcards.data.Set;
import com.jackkates.flashcards.quizlet.QuizletDatabase;
import com.jackkates.flashcards.quizlet.SetListRequest;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String SET_ACTIVITY_ID_KEY = "SetActivityIDKey";
    private final String TAG = "MainActivity";

    ListView listView;
    List<Set> sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(view, position, id);
            }
        });

        fireRequest();
    }


    private void fireRequest() {
        String url = "https://api.quizlet.com/2.0/users/jackkates11/sets?access_token=819b7ac70db0f2bb9c2b4e8a0a22879dab663b14&whitespace=1";

        RequestQueue queue = Volley.newRequestQueue(this);

        SetListRequest request = new SetListRequest(url, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                sets = (List<Set>) response;
                storeSets();
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

    private void storeSets() {
        QuizletDatabase.putAllSets(sets);
    }

    protected void onListItemClick(View v, int position, long id) {
        int setID = sets.get(position).getID();

        Intent intent = new Intent(MainActivity.this, CardListActivity.class);
        intent.putExtra(SET_ACTIVITY_ID_KEY, setID);
        startActivity(intent);
    }

    private void updateListView() {
        listView.setAdapter(new QuizletSetListAdapter(this, sets));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
}
