package com.hendro.android_java_api_crud;

public class Supplier {
    String id, nama, alamat, telp, email, pic;

    public Supplier(String id, String nama, String alamat, String telp, String email, String pic) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.email = email;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelp() {
        return telp;
    }

    public String getEmail() {
        return email;
    }

    public String getPic() {
        return pic;
    }
}
