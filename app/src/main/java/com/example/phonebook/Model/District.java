package com.example.phonebook.Model;

import androidx.annotation.NonNull;

import java.util.List;

public class District {
    private String name;
    private int code;
    private String codename;
    private String division_type;
    private String short_codename;
    private List<Ward> wards;

    public District(int code, String codename, String division_type, String name, String short_codename, List<Ward> wards) {
        this.code = code;
        this.codename = codename;
        this.division_type = division_type;
        this.name = name;
        this.short_codename = short_codename;
        this.wards = wards;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_codename() {
        return short_codename;
    }

    public void setShort_codename(String short_codename) {
        this.short_codename = short_codename;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
