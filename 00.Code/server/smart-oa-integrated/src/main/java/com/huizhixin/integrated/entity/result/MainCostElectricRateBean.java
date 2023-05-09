package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class MainCostElectricRateBean {
    private String daycost;
    private String moonthcost;
    private List<CostElectricRateBean> costratelist;

    @Data
    @AllArgsConstructor
    public static class CostElectricRateBean {
        private String name;
        private String val;
    }
}
