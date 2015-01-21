package com.jackkates.flashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jackkates.flashcards.data.Set;

import java.util.List;

/**
 * Created by jackkates on 1/18/15.
 */
public class QuizletSetListAdapter extends ArrayAdapter<Set> {


    public QuizletSetListAdapter(Context context, List<Set> objects) {
        super(context, R.layout.set_row, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.set_row, parent, false);

            holder = new ViewHolder();
            holder.setTitleText = (TextView) convertView.findViewById(R.id.set_title);
            holder.termCountText = (TextView) convertView.findViewById(R.id.term_count);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Set set = getItem(position);

        if (set != null) {
            holder.setTitleText.setText(set.getTitle());
            holder.termCountText.setText(set.getCards().size() + " terms");
        }

        convertView.setTag(holder);
        return convertView;
    }

    static class ViewHolder {
        TextView setTitleText;
        TextView termCountText;
    }
}


