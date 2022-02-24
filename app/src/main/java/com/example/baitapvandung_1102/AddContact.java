package com.example.baitapvandung_1102;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    EditText edtID, edtName, edtPhone;
    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        edtID = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnAdd);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!= null) {
            int id = bundle.getInt("Id");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            edtID.setText(String.valueOf(id));
            edtName.setText(name);
            edtPhone.setText(phone);
            btnAdd.setText("Edit");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo Intent trở về MainActivity
                Intent intent = new Intent();
                //Tạo bundle là đối tượng chứa dữ liệu
                Bundle bundle = new Bundle();

                //bundle hoạt động như một Java Map các phần tử phân biệt theo key
                //bundle có các hàng put.. trong đó ... là kiểu dữ liệu tương ứng
                bundle.putInt("Id", Integer.parseInt(edtID.getText().toString()));
                bundle.putString("Name", edtName.getText().toString());
                bundle.putString("Phone", edtPhone.getText().toString());
                //có thể đặt cả đối tượng lên bundle bằng hàm putSerilizable
                //đặt bundle lên intent
                intent.putExtras(bundle);
                //trả về bằng hàm setResult
                //tham số thứ nhất là resultCode để quản lý phiên
                //tham số thứ hai là intent chứa dữ liệu gửi về
                setResult(200, intent);
                if (btnAdd.getText() == "Edit") {
                    setResult(201, intent);

                }
                finish();
            }
        });

    }
}