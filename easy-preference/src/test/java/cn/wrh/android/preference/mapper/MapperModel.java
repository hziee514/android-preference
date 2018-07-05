package cn.wrh.android.preference.mapper;

import cn.wrh.android.preference.annotation.Precision;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
public class MapperModel {

    Boolean bool1;

    boolean bool2;

    String str;

    Integer int1;

    int int2;

    Long long1;

    long long2;

    @Precision
    Double double1;

    @Precision
    double double2;

    public Boolean getBool1() {
        return bool1;
    }

    public void setBool1(Boolean bool1) {
        this.bool1 = bool1;
    }

    public boolean isBool2() {
        return bool2;
    }

    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public Long getLong1() {
        return long1;
    }

    public void setLong1(Long long1) {
        this.long1 = long1;
    }

    public long getLong2() {
        return long2;
    }

    public void setLong2(long long2) {
        this.long2 = long2;
    }

    public Double getDouble1() {
        return double1;
    }

    public void setDouble1(Double double1) {
        this.double1 = double1;
    }

    public double getDouble2() {
        return double2;
    }

    public void setDouble2(double double2) {
        this.double2 = double2;
    }
}
