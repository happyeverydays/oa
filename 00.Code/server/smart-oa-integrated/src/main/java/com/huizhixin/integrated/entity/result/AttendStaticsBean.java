package com.huizhixin.integrated.entity.result;

import lombok.Data;

@Data
public class AttendStaticsBean {
    //用户id
    private String userid;
    // 工号
    private String jobNumber;
    // 用户
    private String username;
    // 班次
    private String attendType;
    // 部门
    private String deptName;
    private String deptId;
    // 正常天数
    private Integer acceptcard;
    // 加班小时
    private String addhours;
    // 加班天数
    private String adddays;

    // 缺卡
    private Integer misscard;
    // 总天
    private Integer totaldays;
    // 迟到时长
    private String mindtarrivedt;
    // 迟到天数
    private String mindtarrivedays;
    // 早退时长
    private String maxdtleavedt;
    // 早退天数
    private String maxdtleavedays;
    // 打卡天数
    private Integer dakacs;
    // 缺勤
    private Integer absenceDuty;

}
