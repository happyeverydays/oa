package com.huizhixin.integrated.entity.result;

import com.huizhixin.integrated.entity.ObjModel;
import com.huizhixin.integrated.entity.ObjModelDetail;
import lombok.Data;

import java.util.List;

@Data
public class ObjModelBean {

    private ObjModel objModel;

    private List<ObjModelDetail> list;


}
