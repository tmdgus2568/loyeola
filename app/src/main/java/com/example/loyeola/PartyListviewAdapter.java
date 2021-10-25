package com.example.loyeola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PartyListviewAdapter extends BaseAdapter {
    ArrayList<Party> items = new ArrayList<>();
    Context context;

    public void setItems(ArrayList<Party> items){
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
        Party party = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_party_item,parent, false);
        }

        TextView tvItemTitle = convertView.findViewById(R.id.tvItemTitle);
        TextView tvItemLeader = convertView.findViewById(R.id.tvItemLeader);
        tvItemTitle.setText(party.getTitle());
        tvItemLeader.setText(party.getLeader_id());
        return convertView;
    }
}
