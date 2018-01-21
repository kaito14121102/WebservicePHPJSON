package com.anime.rezero.webservicephpjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateSinhVien extends AppCompatActivity {
    EditText editTen, editNamSinh, editDiaChi;
    Button btnCapNhat, btnHuy;
    int id = 0;
    String url = "http://192.168.0.101:2018/androidwebservice/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sinh_vien);
        initWidget();
        getData();
        SetEven();
    }

    public void getData() {
        Intent intent = getIntent();
        Student hs = (Student) intent.getSerializableExtra("dataSinhVien");
        editTen.setText(hs.getHoten());
        editDiaChi.setText(hs.getDiachi());
        editNamSinh.setText(hs.getNamsinh() + "");
        id = hs.getId();
    }

    public void initWidget() {
        editTen = (EditText) findViewById(R.id.edit_ten);
        editDiaChi = (EditText) findViewById(R.id.edit_diachi);
        editNamSinh = (EditText) findViewById(R.id.edit_namsinh);
        btnCapNhat = (Button) findViewById(R.id.btn_capnhat);
        btnHuy = (Button) findViewById(R.id.btn_huy);
    }

    public void UpdateData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(UpdateSinhVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateSinhVien.this, MainActivity.class));
                } else {
                    Toast.makeText(UpdateSinhVien.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateSinhVien.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "lỗi!\n" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idSV", id + "");
                param.put("hotenSV", editTen.getText().toString().trim());
                param.put("diachiSV", editDiaChi.getText().toString().trim());
                param.put("namsinhSV", editNamSinh.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void SetEven() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTen.getText().length() == 0 || editNamSinh.getText().length() == 0 || editDiaChi.getText().length() == 0) {
                    Toast.makeText(UpdateSinhVien.this, "Vui lòng nhập thông tin đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateData(url);
                }
            }
        });
    }
}
