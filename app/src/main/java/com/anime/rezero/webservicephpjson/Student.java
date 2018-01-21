package com.anime.rezero.webservicephpjson;

import java.io.Serializable;

/**
 * Created by zing on 1/19/2018.
 */

public class Student implements Serializable{
    private int id;
    private String hoten;
    private int namsinh;
    private String diachi;

    public Student() {
    }

    public Student(int id, String hoten, int namsinh, String diachi) {
        this.id = id;
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.diachi = diachi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
