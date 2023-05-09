package com.huizhixin.integrated.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.entity.AreaPermission;
import com.huizhixin.integrated.entity.Bigaddress;
import com.huizhixin.integrated.entity.MeasureAlarm;
import com.huizhixin.integrated.entity.result.*;
import com.huizhixin.integrated.service.IMonitorAlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.service.IBigScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Api(tags = "门")
@RestController
@RequestMapping("/bigscreen")
@Slf4j
public class BigScreenController {

    @Autowired
    private IBigScreenService bigScreenService;

    @Autowired
    private IMonitorAlarmService monitorAlarmService;

    @AutoLog(value = "测量告警-分页列表查询")
    @ApiOperation(value = "测量告警-分页列表查询", notes = "测量告警-分页列表查询")
    @GetMapping(value = "/bigScreenList")
    public Result<IPage<MyCompany>> queryPageList(MyCompany measureAlarm,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req) {
        Result<IPage<MyCompany>> result = new Result<>();
        QueryWrapper<MyCompany> queryWrapper = QueryGenerator.initQueryWrapper(measureAlarm, req.getParameterMap());
        Page<MyCompany> page = new Page<MyCompany>(pageNo, pageSize);
        String id = measureAlarm.getId();
        if (id != null) {
            measureAlarm.setId(null);
            String ids = monitorAlarmService.getChildCompany(id);
            queryWrapper.in("id", ids.split(","));
        }

        IPage<MyCompany> pageList = bigScreenService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    // 获取公司树
    @RequestMapping(value = "/getMyCompanyThree")
    public Result<List<MyCompanyThreeBean>> getMyCompanyThree(String companyId) {
        Result<List<MyCompanyThreeBean>> result = new Result<>();
        try {
            List<MyCompanyThreeBean> list = bigScreenService.getMyCompanyThreeList(companyId);
            result.setResult(list);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    // 获取公司和区域树
    @RequestMapping(value = "/getMyCompanyAreaThree")
    public Result<List<MyCompanyThreeBean>> getMyCompanyAreaThree(String companyId) {
        Result<List<MyCompanyThreeBean>> result = new Result<>();
        try {
            List<MyCompanyThreeBean> list = bigScreenService.getMyCompanyAreaThree(companyId);
            result.setResult(list);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * 添加
     *
     * @param areaPermission
     * @return
     */
    @AutoLog(value = "1-添加")
    @ApiOperation(value = "1-添加", notes = "1-添加")
    @PostMapping(value = "/add")
    public Result<MyCompany> add(@RequestBody MyCompany areaPermission) {
        Result<MyCompany> result = new Result<>();
        try {
            bigScreenService.save(areaPermission);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param areaPermission
     * @return
     */
    @AutoLog(value = "1-编辑")
    @ApiOperation(value = "1-编辑", notes = "1-编辑")
    @PutMapping(value = "/edit")
    public Result<MyCompany> edit(@RequestBody MyCompany areaPermission) {
        Result<MyCompany> result = new Result<>();
        MyCompany areaPermissionEntity = bigScreenService.getById(areaPermission.getId());
        if (areaPermissionEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = bigScreenService.updateById(areaPermission);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "1-通过id删除")
    @ApiOperation(value = "1-通过id删除", notes = "1-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            bigScreenService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "1-批量删除")
    @ApiOperation(value = "1-批量删除", notes = "1-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<MyCompany> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MyCompany> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.bigScreenService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }


}
