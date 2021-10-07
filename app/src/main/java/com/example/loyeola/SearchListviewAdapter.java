package com.example.loyeola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListviewAdapter extends BaseAdapter {
    ArrayList<Character> items = new ArrayList<>();
    Context context;

    public void setItems(ArrayList<Character> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        Character character = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_search_item,parent, false);
        }

        TextView tvItemNickname = convertView.findViewById(R.id.tvItemNickname);
        TextView tvItemClasname = convertView.findViewById(R.id.tvItemClassname);
        TextView tvItemLevel = convertView.findViewById(R.id.tvItemLevel);

        tvItemNickname.setText(character.getNickname());
        tvItemClasname.setText(character.getClassname());
        tvItemLevel.setText(character.getLevel());

        return convertView;
    }
}
