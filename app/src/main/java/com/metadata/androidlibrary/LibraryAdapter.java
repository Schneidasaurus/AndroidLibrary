package com.metadata.androidlibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.metadata.LibraryDomain.InventoryItem;

import java.util.ArrayList;

/**
 * Created by paolee on 4/20/17.
 */

public class LibraryAdapter extends BaseAdapter
{
    private Context context;
    ArrayList<LibraryItem> lt;

    public LibraryAdapter(Context context, ArrayList<LibraryItem> lt) {
        this.context = context;
        this.lt =lt;
    }

    @Override
    public int getCount() {
        return lt.size();
    }

    @Override
    public Object getItem(int position) {
        return lt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.custom_list_view, null);
        TextView id = (TextView) view.findViewById(R.id.item_id);
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView type = (TextView) view.findViewById(R.id.item_type);

        id.setText("Item ID:          " + lt.get(position).getItemId());
        name.setText("Item Name:   " + lt.get(position).getItemName());
        type.setText("Item Type:      " + lt.get(position).getItemType());

        view.setTag(lt.get(position).getItemId());

        return view;
    }
}
