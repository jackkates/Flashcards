package com.jackkates.flashcards;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jackkates.flashcards.data.Card;
import com.jackkates.flashcards.data.Set;
import com.jackkates.flashcards.quizlet.QuizletDatabase;

import java.util.ArrayList;
import java.util.List;


public class CardListActivity extends ActionBarActivity {

    private final String TAG = "CardListActivity";

    private List<Card> cardList;

    private ListView listView;
    private CardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        listView = (ListView) findViewById(R.id.list_view);
        cardList = new ArrayList<Card>();

        int id = getIntent().getIntExtra(MainActivity.SET_ACTIVITY_ID_KEY, 0);

        Set set = QuizletDatabase.getSet(id);

        if (set != null) {
            cardList.addAll(set.getCards());
        }

        adapter = new CardListAdapter(this, cardList);
        listView.setAdapter(adapter);
    }

    private static class CardListAdapter extends ArrayAdapter<Card> {

        public CardListAdapter(Context context, List<Card> objects) {
            super(context, R.layout.term_row, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.term_row, parent, false);
                holder = new ViewHolder();

                holder.termText = (TextView) convertView.findViewById(R.id.term_text);
                holder.definitionText = (TextView) convertView.findViewById(R.id.definition_text);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Card card = getItem(position);

            holder.termText.setText(card.getTerm());
            holder.definitionText.setText(card.getDefinition());

            convertView.setTag(holder);
            return convertView;
        }

        private class ViewHolder {
            TextView termText;
            TextView definitionText;
        }
    }
}
