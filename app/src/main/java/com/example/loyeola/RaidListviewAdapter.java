package com.example.loyeola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RaidListviewAdapter extends BaseAdapter {
    ArrayList<Raid> items = new ArrayList<>();
    Context context;

    public void setItems(ArrayList<Raid> items) {
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
        Raid raid = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_raid_item,parent, false);
        }

        TextView tvItemSub = convertView.findViewById(R.id.tvItemSub);
        TextView tvItemMain = convertView.findViewById(R.id.tvItemMain);
        tvItemSub.setText(raid.getCategory_sub());
        tvItemMain.setText(raid.getCategory_main());
        return convertView;
    }
}
