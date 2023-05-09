package com.huizhixin.integrated.entity.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyCompanyThreeBean {
    private String key;
    private String title;
    private List<MyCompanyThreeBean> children = new ArrayList<>();
}
