package com.example.baitapvandung_1102;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    ListView lstContact;
    ArrayList<Contacts> arrContacts;
    ArrayList<Contacts> arrSelected;
    MyAdapter adapter;

    CheckBox ckbDelete;
    Button btnAdd, btnDelete;

    int positionSelected = -1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(MainActivity.this);
        inflater.inflate(R.menu.menuitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSort:
                Collections.sort(arrContacts, new NameComparator());
                for (Contacts contacts: arrContacts) {
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this, "Đã sắp xếp theo họ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuAdd:
                //TODO
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent, 100);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(MainActivity.this);
        inflater.inflate(R.menu.contextmenuitem, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        if (requestCode == 100 && resultCode == 200) {
            //Đặt vào listdata
            arrContacts.add(new Contacts(id, name, phone, false));
            adapter.notifyDataSetChanged();
        }
        if (resultCode == 201) {
            if (id == arrContacts.get(positionSelected).getId()) {
                arrContacts.get(positionSelected).setName(name);
                arrContacts.get(positionSelected).setPhoneNumber(phone);
                arrContacts.get(positionSelected).setStatus(false);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuEdit:
                //Tạo đối tượng Intent để gọi tới AddContact
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", arrContacts.get(positionSelected).getId());
                bundle.putString("Name", arrContacts.get(positionSelected).getName());
                bundle.putString("Phone", arrContacts.get(positionSelected).getPhoneNumber());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.mnuDelete:
                //TODO
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.editName);
        lstContact = findViewById(R.id.lsvContact);
        ckbDelete = findViewById(R.id.ckbDelete);
        btnDelete = findViewById(R.id.btnDelete);
        arrContacts = new ArrayList<>();
        arrSelected = new ArrayList<>();

        registerForContextMenu(lstContact);

        arrContacts.add(new Contacts(0, "Lê Minh Đạt", "0965325125", false));
        arrContacts.add(new Contacts(1, "Phạm Tiến Anh", "0965325125", false));
        arrContacts.add(new Contacts(2, "Phạm Tiến Hải", "0965325125", false));
        arrContacts.add(new Contacts(3, "Nguyễn Xuân Bách", "0965325125", false));
        arrContacts.add(new Contacts(4, "Nguyễn Thị Thuỷ", "0965325125", false));

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

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                adapter.getFilter().filter(charSequence.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionSelected = i;
                return false;
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