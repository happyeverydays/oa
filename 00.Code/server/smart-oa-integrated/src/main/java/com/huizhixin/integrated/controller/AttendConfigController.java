package com.huizhixin.integrated.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huizhixin.integrated.entity.AttendLateTimConf;
import com.huizhixin.integrated.entity.result.AttendConfigUserBen;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.AttendConfig;
import com.huizhixin.integrated.service.IAttendConfigService;

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
 * @Description: 打卡配置
 * @Author: smart-boot
 * @Date: 2020-01-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "打卡配置")
@RestController
@RequestMapping("/attendConfig")
public class AttendConfigController {
    @Autowired
    private IAttendConfigService attendConfigService;

    /**
     * 分页列表查询
     *
     * @param attendConfig
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "打卡配置-分页列表查询")
    @ApiOperation(value = "打卡配置-分页列表查询", notes = "打卡配置-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<AttendConfig>> queryPageList(AttendConfig attendConfig,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<IPage<AttendConfig>> result = new Result<IPage<AttendConfig>>();
        QueryWrapper<AttendConfig> queryWrapper = QueryGenerator.initQueryWrapper(attendConfig, req.getParameterMap());
        Page<AttendConfig> page = new Page<AttendConfig>(pageNo, pageSize);
        IPage<AttendConfig> pageList = attendConfigService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param attendConfig
     * @return
     */
    @AutoLog(value = "打卡配置-添加")
    @ApiOperation(value = "打卡配置-添加", notes = "打卡配置-添加")
    @PostMapping(value = "/add")
    public Result<AttendConfig> add(@RequestBody AttendConfig attendConfig) {
        Result<AttendConfig> result = new Result<AttendConfig>();
        try {
            attendConfigService.save(attendConfig);
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
     * @param attendConfig
     * @return
     */
    @AutoLog(value = "打卡配置-编辑")
    @ApiOperation(value = "打卡配置-编辑", notes = "打卡配置-编辑")
    @PutMapping(value = "/edit")
    public Result<AttendConfig> edit(@RequestBody AttendConfig attendConfig) {
        Result<AttendConfig> result = new Result<AttendConfig>();
        AttendConfig attendConfigEntity = attendConfigService.getById(attendConfig.getId());
        if (attendConfigEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = attendConfigService.updateById(attendConfig);
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
    @AutoLog(value = "打卡配置-通过id删除")
    @ApiOperation(value = "打卡配置-通过id删除", notes = "打卡配置-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            attendConfigService.removeById(id);
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
    @AutoLog(value = "打卡配置-批量删除")
    @ApiOperation(value = "打卡配置-批量删除", notes = "打卡配置-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<AttendConfig> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<AttendConfig> result = new Result<AttendConfig>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.attendConfigService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "打卡配置-通过id查询")
    @ApiOperation(value = "打卡配置-通过id查询", notes = "打卡配置-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<AttendConfig> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<AttendConfig> result = new Result<AttendConfig>();
        AttendConfig attendConfig = attendConfigService.getById(id);
        if (attendConfig == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(attendConfig);
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
        QueryWrapper<AttendConfig> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                AttendConfig attendConfig = JSON.parseObject(deString, AttendConfig.class);
                queryWrapper = QueryGenerator.initQueryWrapper(attendConfig, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<AttendConfig> pageList = attendConfigService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "打卡配置列表");
        mv.addObject(NormalExcelConstants.CLASS, AttendConfig.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("打卡配置列表数据", "导出人:Smart", "导出信息"));
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
                List<AttendConfig> listAttendConfigs = ExcelImportUtil.importExcel(file.getInputStream(), AttendConfig.class, params);
                attendConfigService.saveBatch(listAttendConfigs);
                return Result.ok("文件导入成功！数据行数:" + listAttendConfigs.size());
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


    @AutoLog(value = "用户打卡配置-分页列表查询")
    @ApiOperation(value = "打卡配置-分页列表查询", notes = "打卡配置-分页列表查询")
    @GetMapping(value = "/getUserCardConfigList")
    public Result<IPage<AttendConfigUserBen>> getUserCardConfigList(AttendConfig attendConfig,
                                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    HttpServletRequest req) {
        Result<IPage<AttendConfigUserBen>> result = new Result<>();
        Page<AttendConfigUserBen> page = new Page<>(pageNo, pageSize);
        IPage<AttendConfigUserBen> pageList = attendConfigService.getUserCardConfigList(page, attendConfig);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "用户打卡配置-分页列表查询")
    @ApiOperation(value = "打卡配置-分页列表查询", notes = "打卡配置-分页列表查询")
    @GetMapping(value = "/editUserCardConfig")
    public Result<IPage<AttendConfigUserBen>> editUserCardConfig(String id, String attendType,
                                                                 HttpServletRequest req) {
        Result<IPage<AttendConfigUserBen>> result = new Result<>();
        attendConfigService.editUserCardConfig(id, attendType);
        result.setSuccess(true);
        return result;
    }


    @ApiOperation(value = "打卡算迟到配置")
    @GetMapping(value = "/getAttendConfList")
    public Result<IPage<AttendLateTimConf>> getAttendConfList(String code, HttpServletRequest req) {
        Result<IPage<AttendLateTimConf>> result = new Result<>();
        Page<AttendLateTimConf> page = new Page<>(1, 10);
        List<AttendLateTimConf> list = attendConfigService.getAttendConfList(code);
        page.setRecords(list);
        page.setTotal(list.size());
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @ApiOperation(value = "修改打卡算迟到配置")
    @PostMapping(value = "/updateAttendConf")
    public Result<IPage<AttendLateTimConf>> updateAttendConf(@RequestBody AttendLateTimConf model, HttpServletRequest req) {
        Result<IPage<AttendLateTimConf>> result = new Result<>();
        attendConfigService.updateAttendConf(model);
        return result;
    }

}
