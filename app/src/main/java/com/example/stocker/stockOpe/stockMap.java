package com.example.stocker.stockOpe;

import androidx.annotation.NonNull;

public class stockMap {

    /**
     * dm : 000001
     * mc : 平安银行
     * jys : sz
     */
    private String dm;
    private String mc;
    private String jys;


    @NonNull
    @Override
    public String toString() {
        return "stockMap{" +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                ", jys='" + jys + '\'' +
                '}';
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getJys() {
        return jys;
    }

    public void setJys(String jys) {
        this.jys = jys;
    }
}
