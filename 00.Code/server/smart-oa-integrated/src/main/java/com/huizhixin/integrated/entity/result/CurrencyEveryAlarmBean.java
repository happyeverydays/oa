package com.huizhixin.integrated.entity.result;

import lombok.Data;

@Data
public class CurrencyEveryAlarmBean {

    private String name;
    private Integer alarmcount;

    public CurrencyEveryAlarmBean() {
    }

    public CurrencyEveryAlarmBean(String name, Integer alarmcount) {
        this.name = name;
        this.alarmcount = alarmcount;
    }
}
