package com.huizhixin.integrated.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huizhixin.integrated.entity.result.CompanyListBean;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.StringUtils;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.MonitorAlarm;
import com.huizhixin.integrated.service.IMonitorAlarmService;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 告警
 * @Author: smart-boot
 * @Date: 2020-01-06
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "告警")
@RestController
@RequestMapping("/monitorAlarm")
public class MonitorAlarmController {
    @Autowired
    private IMonitorAlarmService monitorAlarmService;

    /**
     * 分页列表查询
     *
     * @param monitorAlarm
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "告警-分页列表查询")
    @ApiOperation(value = "告警-分页列表查询", notes = "告警-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<MonitorAlarm>> queryPageList(MonitorAlarm monitorAlarm,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        String companyId = monitorAlarm.getCompanyId();
        if (!StringUtils.isEmpty(companyId)) {
            monitorAlarm.setCompanyId(null);
        }
        Result<IPage<MonitorAlarm>> result = new Result<IPage<MonitorAlarm>>();
        QueryWrapper<MonitorAlarm> queryWrapper = QueryGenerator.initQueryWrapper(monitorAlarm, req.getParameterMap());
        if (!StringUtils.isEmpty(companyId)) {
            String ids = monitorAlarmService.getChildCompany(companyId);
            queryWrapper.in("company_id", ids.split(","));
        }
        Page<MonitorAlarm> page = new Page<MonitorAlarm>(pageNo, pageSize);
        IPage<MonitorAlarm> pageList = monitorAlarmService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "分屏-分页列表查询")
    @ApiOperation(value = "分屏-分页列表查询", notes = "分屏-分页列表查询")
    @GetMapping(value = "/getCompanyList")
    public Result<List<CompanyListBean>> getCompanyList(String companyId, HttpServletRequest req) {
        Result<List<CompanyListBean>> result = new Result<>();
        List<CompanyListBean> list = monitorAlarmService.getCompanyList(companyId);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 添加
     *
     * @param monitorAlarm
     * @return
     */
    @AutoLog(value = "告警-添加")
    @ApiOperation(value = "告警-添加", notes = "告警-添加")
    @PostMapping(value = "/add")
    public Result<MonitorAlarm> add(@RequestBody MonitorAlarm monitorAlarm) {
        Result<MonitorAlarm> result = new Result<MonitorAlarm>();
        try {
            monitorAlarmService.save(monitorAlarm);
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
     * @param monitorAlarm
     * @return
     */
    @AutoLog(value = "告警-编辑")
    @ApiOperation(value = "告警-编辑", notes = "告警-编辑")
    @PutMapping(value = "/edit")
    public Result<MonitorAlarm> edit(@RequestBody MonitorAlarm monitorAlarm) {
        Result<MonitorAlarm> result = new Result<MonitorAlarm>();
        MonitorAlarm monitorAlarmEntity = monitorAlarmService.getById(monitorAlarm.getId());
        if (monitorAlarmEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = monitorAlarmService.updateById(monitorAlarm);
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
    @AutoLog(value = "告警-通过id删除")
    @ApiOperation(value = "告警-通过id删除", notes = "告警-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            monitorAlarmService.removeById(id);
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
    @AutoLog(value = "告警-批量删除")
    @ApiOperation(value = "告警-批量删除", notes = "告警-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<MonitorAlarm> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MonitorAlarm> result = new Result<MonitorAlarm>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.monitorAlarmService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "告警-通过id查询")
    @ApiOperation(value = "告警-通过id查询", notes = "告警-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<MonitorAlarm> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<MonitorAlarm> result = new Result<MonitorAlarm>();
        MonitorAlarm monitorAlarm = monitorAlarmService.getById(id);
        if (monitorAlarm == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(monitorAlarm);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<MonitorAlarm> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                MonitorAlarm monitorAlarm = JSON.parseObject(deString, MonitorAlarm.class);
                queryWrapper = QueryGenerator.initQueryWrapper(monitorAlarm, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<MonitorAlarm> pageList = monitorAlarmService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "告警列表");
        mv.addObject(NormalExcelConstants.CLASS, MonitorAlarm.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("告警列表数据", "导出人:Smart", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<MonitorAlarm> listMonitorAlarms = ExcelImportUtil.importExcel(file.getInputStream(), MonitorAlarm.class, params);
                monitorAlarmService.saveBatch(listMonitorAlarms);
                return Result.ok("文件导入成功！数据行数:" + listMonitorAlarms.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

}
