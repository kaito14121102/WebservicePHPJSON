package com.anime.rezero.webservicephpjson;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView lvStudent;
    StdentAdapter adapter;
    List<Student> studentList;
    String urlDelte = "http://192.168.0.101:2018/androidwebservice/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        String url = "http://192.168.0.101:2018/androidwebservice/getdata.php";
        ReadJSON(url);
    }

    public void ReadJSON(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int id = object.getInt("ID");
                        String ten = object.getString("HoTen");
                        int namsinh = object.getInt("NamSinh");
                        String diachi = object.getString("DiaChi");
                        studentList.add(new Student(id, ten, namsinh, diachi));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    public void initWidget() {
        lvStudent = (ListView) findViewById(R.id.lv_stdudent);
        studentList = new ArrayList<>();
        adapter = new StdentAdapter(this, R.layout.dong_du_lieu, studentList);
        lvStudent.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_them) {
            startActivity(new Intent(MainActivity.this, AddSinhVien.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void DeleteStudent(final int idsv) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelte, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "lỗi!\n" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idSV", String.valueOf(idsv));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
