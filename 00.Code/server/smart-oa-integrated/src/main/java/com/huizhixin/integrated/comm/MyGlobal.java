package com.huizhixin.integrated.comm;

import lombok.Data;

@Data
public class MyGlobal {
    private static String imgPrix="http://192.168.2.193:8080/jeecgboot/upload/";
    public static String getImgPrix(){
        return imgPrix;
    }



}

















