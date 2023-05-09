package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huizhixin.integrated.comm.LocalFileServerUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceRecognition {
    // 时间
    private String dt;
    // 用户
    private String username;
    // 监控
    private String monitor;
    // 照片1
    private String photo1;
    // 照片2
    private String photo2;

    public String getPhoto1(){
      return   LocalFileServerUtil.getServerAddress()+""+photo1;
    }

    public String getPhoto2(){
        return   LocalFileServerUtil.getServerAddress()+""+photo2;
    }
}
