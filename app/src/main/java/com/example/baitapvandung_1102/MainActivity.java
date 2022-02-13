package com.example.baitapvandung_1102;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lstContact;
    ArrayList<Contacts> arrContacts;
    ArrayList<Contacts> arrSelected;
    MyAdapter adapter;

    CheckBox ckbDelete;
    Button btnAdd, btnDelete;

    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstContact = findViewById(R.id.lsvContact);
        ckbDelete = findViewById(R.id.ckbDelete);
        btnDelete = findViewById(R.id.btnDelete);
        arrContacts = new ArrayList<>();
        arrSelected = new ArrayList<>();

        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Lê Minh Đạt", "0965325125", false));


        adapter = new MyAdapter(MainActivity.this, arrContacts, new MyAdapter.onClick() {
            @Override
            public void onClickItem(Contacts contacts, Boolean isChecked) {
                if(isChecked) {
                    arrSelected.add(contacts);
                } else {
                    arrSelected.remove(contacts);
                }
            }
        });

        lstContact.setAdapter(adapter);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrSelected.size() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Xoá liên lạc");
                    builder.setMessage("Bạn có chắc chắn muốn xoá liên lạc này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (Contacts contacts:arrSelected){
                                arrContacts.remove(contacts);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            
                        }
                    });
                    
                    builder.create();
                    builder.show();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng chọn liên lạc muốn xoá!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void onDeleteClick(Object position) {
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//                        // Yes button clicked
//                        if (ckbDelete.isChecked()) {
//                            adapter.remove(position);
//                            Toast.makeText(MainActivity.this, "Xoá thành công!", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        // No button clicked
//                        // do nothing
//                        Toast.makeText(MainActivity.this, "No Clicked", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure?")
//                .setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener);
//    }
}