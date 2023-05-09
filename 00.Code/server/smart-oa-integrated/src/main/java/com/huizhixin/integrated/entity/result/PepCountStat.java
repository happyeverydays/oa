package com.huizhixin.integrated.entity.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PepCountStat {
    private Integer totalcount;
    private Integer floor1count;
    private Integer floor3count;
    private Integer floor5count;
    private Integer floor6count;
}
