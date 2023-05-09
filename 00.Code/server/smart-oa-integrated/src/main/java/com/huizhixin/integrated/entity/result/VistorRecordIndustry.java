package com.huizhixin.integrated.entity.result;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class VistorRecordIndustry {
    // 今日访客数
    private Integer todayvistorcount;
    // 访客一周记录
    private List<VistorlistBean> recordList;
    // 访客列表
    private List<VistorBean> vistorlist;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VistorlistBean {
        private String time;
        private Integer count;
    }

    @Data
    public static class VistorBean {
        // 时间
        private String time;
        //姓名
        private String name;
        // 公司
        private String company;
        //原因
        private String reason;
    }
}
