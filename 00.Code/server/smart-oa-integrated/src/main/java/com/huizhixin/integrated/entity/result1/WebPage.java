package com.huizhixin.integrated.entity.result1;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WebPage {
    private String currentpage="";
    private String nextPage="";
    private  List<WebPageBean> list=new ArrayList<>();
}
