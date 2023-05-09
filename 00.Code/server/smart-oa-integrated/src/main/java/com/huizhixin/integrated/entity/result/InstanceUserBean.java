package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstanceUserBean {
    private String rowAutoID;
    //时间
    private String dt;
    // 用户
    private String username;
    // 公司
    private String company;
    // 注意
    private String attention;
}
