package com.huizhixin.integrated.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huizhixin.integrated.entity.Area;
import com.huizhixin.integrated.entity.result.MyCompany;
import com.huizhixin.integrated.entity.result.MyCompanyThreeBean;
import com.huizhixin.integrated.mapper.MyCompanyMapper;
import com.huizhixin.integrated.service.IAreaService;
import com.huizhixin.smart.common.constant.CommonConstant;
import com.huizhixin.smart.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import com.huizhixin.integrated.service.IBigScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BigScreenServiceImpl extends ServiceImpl<MyCompanyMapper, MyCompany> implements IBigScreenService {

    @Autowired
    private IAreaService areaService;

    @Override
    public List<MyCompanyThreeBean> getMyCompanyThreeList(String companyId) {
        QueryWrapper<MyCompany> query = new QueryWrapper<>();
        query.eq("id", companyId);
        List<MyCompany> list = this.list(query);
        // 调用wrapTreeDataToTreeList方法生成树状数据
        List<MyCompanyThreeBean> listResult = wrapTreeDataToDepartIdTreeList(list);
        return listResult;
    }

    @Override
    public List<MyCompanyThreeBean> getMyCompanyAreaThree(String companyId) {
        QueryWrapper<MyCompany> query = new QueryWrapper<>();
        query.eq("id", companyId);
        List<MyCompanyThreeBean> list1 = getMyCompanyThreeBeanList(list(query));
        List<MyCompanyThreeBean> listResult = findCompanyAreaChildren_top(list1);
        return listResult;
    }

    private List<MyCompanyThreeBean> findCompanyAreaChildren_top(List<MyCompanyThreeBean> recordList) {

        for (int i = 0; i < recordList.size(); i++) {
            MyCompanyThreeBean bean = recordList.get(i);
            QueryWrapper<MyCompany> query = new QueryWrapper<>();
            query.eq("parent_id", bean.getKey());
            List<MyCompanyThreeBean> threelist = getMyCompanyThreeBeanList(list(query));
            if (threelist.size() > 0) {
                bean.setChildren(threelist);
                findCompanyAreaChildren(threelist);
            }

            QueryWrapper<Area> query1 = new QueryWrapper<>();
            query1.eq("company_id", bean.getKey());
            query.isNull("parent_id");
            List<Area> list1 = areaService.list(query1);
            List<MyCompanyThreeBean> list2 = getAreaMyCompanyThreeBeanList(list1);
            bean.getChildren().addAll(list2);
            findAreaChildren(list2);


        }
        return recordList;
    }

    private List<MyCompanyThreeBean> findCompanyAreaChildren(List<MyCompanyThreeBean> recordList) {

        for (int i = 0; i < recordList.size(); i++) {
            MyCompanyThreeBean bean = recordList.get(i);
            QueryWrapper<MyCompany> query = new QueryWrapper<>();
            query.eq("parent_id", bean.getKey());
            query.orderByAsc("depart_order");
            List<MyCompany> list = this.list(query);
            List<MyCompanyThreeBean> threelist = getMyCompanyThreeBeanList(list);
            if (list.size() > 0) {
                bean.setChildren(threelist);
                findCompanyAreaChildren(threelist);
            }
            QueryWrapper<Area> query1 = new QueryWrapper<>();
            query1.eq("company_id", bean.getKey());
            query1.isNull("parent_id");
            List<Area> list1 = areaService.list(query1);
            List<MyCompanyThreeBean> list2 = getAreaMyCompanyThreeBeanList(list1);
            bean.getChildren().addAll(list2);
            findAreaChildren(list2);
        }
        findAreaChildren(recordList);
        return recordList;
    }

    private List<MyCompanyThreeBean> findAreaChildren(List<MyCompanyThreeBean> recordList) {

        for (int i = 0; i < recordList.size(); i++) {
            MyCompanyThreeBean bean = recordList.get(i);
            QueryWrapper<Area> query = new QueryWrapper<>();
            if (!bean.getKey().contains("area-")) {
                continue;
            }
            query.eq("parent_id", bean.getKey().replace("area-", ""));
            List<Area> list = areaService.list(query);
            List<MyCompanyThreeBean> threelist = getAreaMyCompanyThreeBeanList(list);
            if (list.size() > 0) {
                bean.setChildren(threelist);
                findChildren(threelist);
            }
        }
        return recordList;
    }


    public List<MyCompanyThreeBean> wrapTreeDataToDepartIdTreeList(List<MyCompany> recordList) {
        List<MyCompanyThreeBean> list = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            MyCompany depart = recordList.get(i);
            MyCompanyThreeBean bean = new MyCompanyThreeBean();
            bean.setKey(depart.getId());
            bean.setTitle(depart.getDepartName());
            list.add(bean);
        }
        List<MyCompanyThreeBean> newlist = findChildren(list);
        return newlist;
    }


    private List<MyCompanyThreeBean> findChildren(List<MyCompanyThreeBean> recordList) {

        for (int i = 0; i < recordList.size(); i++) {
            MyCompanyThreeBean bean = recordList.get(i);
            QueryWrapper<MyCompany> query = new QueryWrapper<>();
            query.eq("parent_id", bean.getKey());
            query.orderByAsc("depart_order");
            List<MyCompany> list = this.list(query);
            List<MyCompanyThreeBean> threelist = new ArrayList<>();
            for (MyCompany company : list) {
                MyCompanyThreeBean bean1 = new MyCompanyThreeBean();
                bean1.setKey(company.getId());
                bean1.setTitle(company.getDepartName());
                threelist.add(bean1);
            }
            if (list.size() > 0) {
                bean.setChildren(threelist);
                findChildren(threelist);
            }
        }
        return recordList;
    }


    private List<MyCompanyThreeBean> getMyCompanyThreeBeanList(List<MyCompany> list) {
        List<MyCompanyThreeBean> threelist = new ArrayList<>();
        for (MyCompany company : list) {
            MyCompanyThreeBean bean1 = new MyCompanyThreeBean();
            bean1.setKey(company.getId());
            bean1.setTitle(company.getDepartName());
            threelist.add(bean1);
        }
        return threelist;
    }

    private List<MyCompanyThreeBean> getAreaMyCompanyThreeBeanList(List<Area> list1) {
        List<MyCompanyThreeBean> list2 = new ArrayList<>();
        for (Area bean2 : list1) {
            MyCompanyThreeBean three = new MyCompanyThreeBean();
            three.setKey("area-" + bean2.getId());
            three.setTitle(bean2.getName());
            list2.add(three);
        }
        return list2;
    }


}
