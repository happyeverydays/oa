package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class EnvirMonitorList {

    private List<EnvirMonitorBean> list;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvirMonitorBean {
        // 点位信息
        private String name;
        // 实际参数
        private String realparms;
        // 运行状态
        private String runstate;
    }
}
