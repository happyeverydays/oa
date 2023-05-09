package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huizhixin.integrated.comm.LocalFileServerUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarnInfoBean {
    // 时间
    private String dt;
    // 告警类型
    private String warnType;
    // 人员类型
    private String userType;
    // 监控
    private String monitor;
    // 照片1
    private String photo1;
    // 照片2
    private String photo2;

    public String getPhoto1() {
        if (photo1 == null) {
            return null;
        }
        return LocalFileServerUtil.getServerAddress() + "" + photo1;
    }

    public String getPhoto2() {
        if (photo2 == null) {
            return null;
        }
        return LocalFileServerUtil.getServerAddress() + "" + photo2;
    }
}
