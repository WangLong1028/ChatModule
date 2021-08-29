package com.wl.leetcode.test;

import com.wl.leetcode.test.CityBean;

public class PersonBean {
    private String name;
    private CityBean city;

    public PersonBean(String name, CityBean city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }
}
