package com.example.baitapvandung_1102;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends BaseAdapter implements Filterable {

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (dataBackup == null) {
                    dataBackup = new ArrayList<>(data);
                }
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = dataBackup.size();
                    filterResults.values = dataBackup;
                } else {
                    ArrayList<Contacts> newData = new ArrayList<>();
                    for (Contacts contacts : dataBackup) {
                        if (contacts.getName().toLowerCase().contains(
                          charSequence.toString().toLowerCase())) {
                            newData.add(contacts);
                        }
                    }
                    filterResults.count = newData.size();
                    filterResults.values = newData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = new ArrayList<Contacts>();
                ArrayList<Contacts> tmp = (ArrayList<Contacts>) filterResults.values;
                for (Contacts contacts : tmp) {
//                    Contacts contact = new Contacts(contacts.getId(), contacts.getName(), contacts.getPhoneNumber(), contacts.isStatus());
                    data.add(contacts);
                }
                notifyDataSetChanged();
            }
        };
        return f;
    }

    public interface onClick {
        public void onClickItem(Contacts contacts, Boolean isChecked);

    }
    onClick clickSelected;

    public MyAdapter(Activity activity, ArrayList<Contacts> data, onClick clickSelected) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.clickSelected = clickSelected;
    }

    private Activity activity;
    private ArrayList<Contacts> data;
    private ArrayList<Contacts> dataBackup;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Contacts getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        Contacts contacts = data.get(i);
        return contacts.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_contact, null);
        }

        Contacts object = (Contacts) getItem(i);

        if (object != null) {
            CheckBox ckbDelete = view.findViewById(R.id.ckbDelete);
            TextView txtName = view.findViewById(R.id.txtName);
            TextView txtPhone = view.findViewById(R.id.txtPhone);


            txtName.setText(object.getName());
            txtPhone.setText(object.getPhoneNumber());
            ckbDelete.setChecked(object.isStatus());

            ckbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    clickSelected.onClickItem(object, b);
                }
            });

        }
        return view;
    }

}
