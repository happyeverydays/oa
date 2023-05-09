package com.huizhixin.integrated.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huizhixin.integrated.entity.Bigaddress;
import com.huizhixin.integrated.entity.Visitor;
import com.huizhixin.integrated.entity.result.*;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import java.util.List;
import java.util.Map;

/**
 * @Description: 访客
 * @Author: jeecg-boot
 * @Date: 2019-08-31
 * @Version: V1.0
 */
public interface VisitorMapper extends BaseMapper<Visitor> {

    List<Visitor> getpageList(Map<String, String> map);

    Integer getpageListcount(Map<String, String> map);

    PhotoByteBean getphoto(String rowautoid);

    PhotoByteBean getphoto1(String rowautoid);

    PhotoByteBean getphoto2(String rowautoid);

    List<InstanceUserBean> getinstanceUserList();

    SysDictItem getbigscreenaddress(String name);

    void updatedict(Map<String, String> map);

    Integer gettodayvistorcount();

    Integer getdayvistorcount(Map<String, String> map);

    Cameraaddress3dBean get3dcameraaddress(String id);

    Integer getfacecount(Map<String, String> map);

    List<VistiorTaskBean> getVistiorTaskList();

    void updateVistior(Map<String, String> map);

    List<PresentStatisBean> getpresentstatisList();

    void addalladdresslist(Map map);

    List<Bigaddress> getBigaddressList();

    Double getvideoaddressminutes();

    void updatevideoaddressminutes(Map<String, Double> map);

    void updatevideoaddressminutesdefault();

    void updatevideoaddressminutesdefault1();

    void delbigscreen(Map<String, String> map);

    List<VistorRecordIndustry.VistorBean> getVistorRecordIndustryList();

    void delbigscreenByid(String id);
}
