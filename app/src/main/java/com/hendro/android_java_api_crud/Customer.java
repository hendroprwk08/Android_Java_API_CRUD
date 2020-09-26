package com.hendro.android_java_api_crud;

public class Customer {
    String id, nama, telp;

    public Customer(String id, String nama, String telp) {
        this.id = id;
        this.nama = nama;
        this.telp = telp;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTelp() {
        return telp;
    }
}
