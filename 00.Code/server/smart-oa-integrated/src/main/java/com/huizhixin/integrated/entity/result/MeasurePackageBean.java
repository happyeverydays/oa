package com.huizhixin.integrated.entity.result;

import com.huizhixin.integrated.entity.DeviceProduct;
import com.huizhixin.integrated.entity.MeasurePoints;
import lombok.Data;

import java.util.List;

@Data
public class MeasurePackageBean {
    private List<MeasurePoints> list;
    private DeviceProduct deviceProduct;
}
