package com.huizhixin.integrated.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.collections.impl.bimap.mutable.HashBiMap;
import com.huizhixin.integrated.entity.AttendDevice;
import com.huizhixin.integrated.entity.result.AttendStaticsBean;
import com.huizhixin.integrated.service.IAttendDeviceService;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.AttendRecord;
import com.huizhixin.integrated.service.IAttendRecordService;

import java.util.Date;
import java.util.logging.SimpleFormatter;

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
 * @Description: 考勤记录
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "考勤记录")
@RestController
@RequestMapping("/attendRecord")
public class AttendRecordController {
    @Autowired
    private IAttendRecordService attendRecordService;

    @Autowired
    private IAttendDeviceService attendDeviceService;

    /**
     * 分页列表查询
     *
     * @param attendRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "考勤记录-分页列表查询")
    @ApiOperation(value = "考勤记录-分页列表查询", notes = "考勤记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<AttendRecord>> queryPageList(AttendRecord attendRecord,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<IPage<AttendRecord>> result = new Result<IPage<AttendRecord>>();
        QueryWrapper<AttendRecord> queryWrapper = QueryGenerator.initQueryWrapper(attendRecord, req.getParameterMap());
        Page<AttendRecord> page = new Page<AttendRecord>(pageNo, pageSize);
        IPage<AttendRecord> pageList = attendRecordService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param attendRecord
     * @return
     */
    @AutoLog(value = "考勤记录-添加")
    @ApiOperation(value = "考勤记录-添加", notes = "考勤记录-添加")
    @PostMapping(value = "/add")
    public Result<AttendRecord> add(@RequestBody AttendRecord attendRecord) {
        Result<AttendRecord> result = new Result<AttendRecord>();
        try {
            AttendRecord data = attendRecordService.getDataByUserId(attendRecord.getUserid());
            attendRecord.setUsername(data.getUsername());
            attendRecord.setDeptId(data.getDeptId());
            attendRecord.setDeptName(data.getDeptName());
            attendRecord.setAttendType(data.getAttendType());
            AttendDevice device = attendDeviceService.getById(attendRecord.getDeviceId());
            if (device != null) {
                attendRecord.setLocation(device.getLocation());
            }
            attendRecordService.save(attendRecord);
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
     * @param attendRecord
     * @return
     */
    @AutoLog(value = "考勤记录-编辑")
    @ApiOperation(value = "考勤记录-编辑", notes = "考勤记录-编辑")
    @PutMapping(value = "/edit")
    public Result<AttendRecord> edit(@RequestBody AttendRecord attendRecord) {
        Result<AttendRecord> result = new Result<AttendRecord>();
        AttendRecord attendRecordEntity = attendRecordService.getById(attendRecord.getId());
        if (attendRecordEntity == null) {
            result.error500("未找到对应实体");
        } else {
            AttendRecord data = attendRecordService.getDataByUserId(attendRecord.getUserid());
            attendRecord.setUsername(data.getUsername());
            attendRecord.setDeptId(data.getDeptId());
            attendRecord.setDeptName(data.getDeptName());
            attendRecord.setAttendType(data.getAttendType());
            AttendDevice device = attendDeviceService.getById(attendRecord.getDeviceId());
            if (device != null) {
                attendRecord.setLocation(device.getLocation());
            }
            boolean ok = attendRecordService.updateById(attendRecord);
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
    @AutoLog(value = "考勤记录-通过id删除")
    @ApiOperation(value = "考勤记录-通过id删除", notes = "考勤记录-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            attendRecordService.removeById(id);
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
    @AutoLog(value = "考勤记录-批量删除")
    @ApiOperation(value = "考勤记录-批量删除", notes = "考勤记录-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<AttendRecord> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<AttendRecord> result = new Result<AttendRecord>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.attendRecordService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "考勤记录-通过id查询")
    @ApiOperation(value = "考勤记录-通过id查询", notes = "考勤记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<AttendRecord> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<AttendRecord> result = new Result<AttendRecord>();
        AttendRecord attendRecord = attendRecordService.getById(id);
        if (attendRecord == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(attendRecord);
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
        QueryWrapper<AttendRecord> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                AttendRecord attendRecord = JSON.parseObject(deString, AttendRecord.class);
                queryWrapper = QueryGenerator.initQueryWrapper(attendRecord, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<AttendRecord> pageList = attendRecordService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "考勤记录列表");
        mv.addObject(NormalExcelConstants.CLASS, AttendRecord.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("考勤记录列表数据", "导出人:Smart", "导出信息"));
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
                List<AttendRecord> listAttendRecords = ExcelImportUtil.importExcel(file.getInputStream(), AttendRecord.class, params);
                attendRecordService.saveBatch(listAttendRecords);
                return Result.ok("文件导入成功！数据行数:" + listAttendRecords.size());
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


    @AutoLog(value = "考勤统计")
    @ApiOperation(value = "考勤统计-通过id查询", notes = "考勤记录-通过id查询")
    @RequestMapping(value = "/getAttendStaticsList")
    public Result<IPage<AttendStaticsBean>> getAttendStaticsList(AttendRecord attendRecord,
                                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, String starttime, String endtime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int total = 0;
        try {
            Date dt1 = sdf.parse(starttime);
            Date dt2 = sdf.parse(endtime);
            total = (int) ((dt2.getTime() - dt1.getTime()) / 3600 / 24000) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Result<IPage<AttendStaticsBean>> result = new Result<>();
        Page<AttendStaticsBean> page = new Page<>(pageNo, pageSize);
        Map<String, Object> map = new HashBiMap<>();
        if (starttime != null) {
            map.put("starttime", starttime);
        }
        if (endtime != null) {
            map.put("endtime", endtime);
        }
        if (attendRecord.getUsername() != null) {
            map.put("username", attendRecord.getUsername());
        }
        if (attendRecord.getDeptName() != null) {
            map.put("deptname", attendRecord.getDeptName());
        }
        IPage<AttendStaticsBean> pageList = attendRecordService.getAttendStatics(page, map);
//        for (AttendStaticsBean bean : pageList.getRecords()) {
//            bean.setTotaldays(total);
//            bean.setAbsenceDuty(total - bean.getDakacs());
//        }

        result.setResult(pageList);
        result.setSuccess(true);

        return result;
    }

}
