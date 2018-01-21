package com.anime.rezero.webservicephpjson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zing on 1/19/2018.
 */

public class StdentAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Student> studentList;


    public StdentAdapter(MainActivity context, int layout, List<Student> studentList) {
        this.context = context;
        this.layout = layout;
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        private TextView txtTen,txtNamSinh,txtDiaChi;
        private ImageButton ibtnEdit,ibtnDelete;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.dong_du_lieu,null);
            viewHolder = new ViewHolder();
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.txt_ten);
            viewHolder.txtDiaChi = (TextView) convertView.findViewById(R.id.txt_diachi);
            viewHolder.txtNamSinh = (TextView) convertView.findViewById(R.id.txt_namsinh);
            viewHolder.ibtnDelete = (ImageButton) convertView.findViewById(R.id.ibtn_delete);
            viewHolder.ibtnEdit = (ImageButton) convertView.findViewById(R.id.ibtn_edit);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Student student = studentList.get(position);
        viewHolder.txtTen.setText(student.getHoten());
        viewHolder.txtNamSinh.setText("Nam sinh : "+student.getNamsinh());
        viewHolder.txtDiaChi.setText(student.getDiachi());
        //Bat su kien
        viewHolder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateSinhVien.class);
                intent.putExtra("dataSinhVien",student);
                context.startActivity(intent);
            }
        });

        viewHolder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(student.getId(),student.getHoten());
            }
        });
        return convertView;
    }

    private void XacNhanXoa(final int id,String ten){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Bạn có muốn xóa sinh viên "+ten+" này không!");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteStudent(id);
            }
        });

        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

}
