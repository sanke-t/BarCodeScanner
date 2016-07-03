package com.example.sanket.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sanket on 13-04-2016.
 */
public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> List;
    public ListAdapter(Context context, ArrayList<Book> list) {

        this.context = context;
        List = list;
    }
    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = List.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);

        }
        TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_slno);
        tvSlNo.setText(book.getTitle());
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(String.valueOf(book.getQuantity()));
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
        tvPhone.setText(String.valueOf(book.getAuthor()));

        return convertView;
    }
}
