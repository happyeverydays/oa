package com.huizhixin.integrated.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huizhixin.integrated.entity.MonitorCamera;
import com.huizhixin.integrated.entity.result.CompanyListBean;
import com.huizhixin.integrated.service.IMonitorAlarmService;
import com.huizhixin.integrated.service.IMonitorCameraService;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.StringUtils;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.MonitorCameraCompany;
import com.huizhixin.integrated.service.IMonitorCameraCompanyService;

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
 * @Description: 分屏
 * @Author: smart-boot
 * @Date: 2020-01-08
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "分屏")
@RestController
@RequestMapping("/monitorCameraCompany")
public class MonitorCameraCompanyController {
    @Autowired
    private IMonitorCameraCompanyService monitorCameraCompanyService;
    @Autowired
    private IMonitorCameraService monitorCameraService;
    @Autowired
    private IMonitorAlarmService monitorAlarmService;

    /**
     * 分页列表查询
     *
     * @param monitorCameraCompany
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "分屏-分页列表查询")
    @ApiOperation(value = "分屏-分页列表查询", notes = "分屏-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<MonitorCameraCompany>> queryPageList(MonitorCameraCompany monitorCameraCompany,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        Result<IPage<MonitorCameraCompany>> result = new Result<IPage<MonitorCameraCompany>>();
        String companyId = monitorCameraCompany.getCompanyId();
        if (!StringUtils.isEmpty(companyId)) {
            monitorCameraCompany.setCompanyId(null);
        }
        QueryWrapper<MonitorCameraCompany> queryWrapper = QueryGenerator.initQueryWrapper(monitorCameraCompany, req.getParameterMap());
        if (!StringUtils.isEmpty(companyId)) {
            String ids = monitorAlarmService.getChildCompany(companyId);
            queryWrapper.in("company_id", ids.split(","));
        }
        queryWrapper.getExpression().getOrderBy().clear();
        queryWrapper.orderByAsc("company_id");
        queryWrapper.orderByAsc("ctype");
        queryWrapper.orderByAsc("sort");
        Page<MonitorCameraCompany> page = new Page<MonitorCameraCompany>(pageNo, pageSize);
        IPage<MonitorCameraCompany> pageList = monitorCameraCompanyService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }



    /**
     * 添加
     *
     * @param monitorCameraCompany
     * @return
     */
    @AutoLog(value = "分屏-添加")
    @ApiOperation(value = "分屏-添加", notes = "分屏-添加")
    @PostMapping(value = "/add")
    public Result<MonitorCameraCompany> add(@RequestBody MonitorCameraCompany monitorCameraCompany) {
        Result<MonitorCameraCompany> result = new Result<MonitorCameraCompany>();
        try {
            MonitorCamera monitorCamera = monitorCameraService.getById(monitorCameraCompany.getCameraId());
            if (monitorCamera != null) {
                monitorCameraCompany.setCname(monitorCamera.getName());
                monitorCameraCompany.setCameraUrl(monitorCamera.getUrl());
            }
            monitorCameraCompanyService.save(monitorCameraCompany);
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
     * @param monitorCameraCompany
     * @return
     */
    @AutoLog(value = "分屏-编辑")
    @ApiOperation(value = "分屏-编辑", notes = "分屏-编辑")
    @PutMapping(value = "/edit")
    public Result<MonitorCameraCompany> edit(@RequestBody MonitorCameraCompany monitorCameraCompany) {
        Result<MonitorCameraCompany> result = new Result<MonitorCameraCompany>();
        MonitorCameraCompany monitorCameraCompanyEntity = monitorCameraCompanyService.getById(monitorCameraCompany.getId());
        if (monitorCameraCompanyEntity == null) {
            result.error500("未找到对应实体");
        } else {
            MonitorCamera monitorCamera = monitorCameraService.getById(monitorCameraCompany.getCameraId());
            if (monitorCamera != null) {
                monitorCameraCompany.setCname(monitorCamera.getName());
                monitorCameraCompany.setCameraUrl(monitorCamera.getUrl());
            }
            boolean ok = monitorCameraCompanyService.updateById(monitorCameraCompany);
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
    @AutoLog(value = "分屏-通过id删除")
    @ApiOperation(value = "分屏-通过id删除", notes = "分屏-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            monitorCameraCompanyService.removeById(id);
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
    @AutoLog(value = "分屏-批量删除")
    @ApiOperation(value = "分屏-批量删除", notes = "分屏-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<MonitorCameraCompany> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MonitorCameraCompany> result = new Result<MonitorCameraCompany>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.monitorCameraCompanyService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "分屏-通过id查询")
    @ApiOperation(value = "分屏-通过id查询", notes = "分屏-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<MonitorCameraCompany> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<MonitorCameraCompany> result = new Result<MonitorCameraCompany>();
        MonitorCameraCompany monitorCameraCompany = monitorCameraCompanyService.getById(id);
        if (monitorCameraCompany == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(monitorCameraCompany);
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
        QueryWrapper<MonitorCameraCompany> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                MonitorCameraCompany monitorCameraCompany = JSON.parseObject(deString, MonitorCameraCompany.class);
                queryWrapper = QueryGenerator.initQueryWrapper(monitorCameraCompany, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<MonitorCameraCompany> pageList = monitorCameraCompanyService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "分屏列表");
        mv.addObject(NormalExcelConstants.CLASS, MonitorCameraCompany.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("分屏列表数据", "导出人:Smart", "导出信息"));
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
                List<MonitorCameraCompany> listMonitorCameraCompanys = ExcelImportUtil.importExcel(file.getInputStream(), MonitorCameraCompany.class, params);
                monitorCameraCompanyService.saveBatch(listMonitorCameraCompanys);
                return Result.ok("文件导入成功！数据行数:" + listMonitorCameraCompanys.size());
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
