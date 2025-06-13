package app.model;

import com.google.gson.annotations.SerializedName;

public class Student implements java.io.Serializable {

    @SerializedName("student_no")
    public String ogrenciNo;

    @SerializedName("name")
    public String adSoyad;

    @SerializedName("department")
    public String bolum;

    public Student(String ogrenciNo, String adSoyad, String bolum) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        this.bolum = bolum;
    }
}