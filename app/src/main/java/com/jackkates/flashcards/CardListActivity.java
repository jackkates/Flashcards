package com.jackkates.flashcards;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jackkates.flashcards.data.Card;
import com.jackkates.flashcards.data.Set;
import com.jackkates.flashcards.quizlet.QuizletDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackkates on 1/21/15.
 */
public class CardListActivity extends ListActivity {

    private final String TAG = "CardListActivity";

    private List<Card> cardList;
    private CardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra(MainActivity.SET_ACTIVITY_ID_KEY, 0);

        Set set = QuizletDatabase.getSet(id);
        Log.d(TAG, set.getTitle());

        if (set != null) {
            Log.d(TAG, set.getTitle());
            cardList = set.getCards();
        } else {
            cardList = new ArrayList<>();
        }

        adapter = new CardListAdapter(this, cardList);
        setListAdapter(adapter);
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
