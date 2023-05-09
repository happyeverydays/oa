package com.huizhixin.integrated.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huizhixin.integrated.cameradevice.entity.DeviceUser;
import com.huizhixin.integrated.cameradevice.service.DeviceUserService;
import com.huizhixin.integrated.comm.Base64Utils;
import com.huizhixin.integrated.entity.AttendDevice;
import com.huizhixin.integrated.entity.User;
import com.huizhixin.integrated.entity.result.DictItemBean;
import com.huizhixin.integrated.service.IAttendDeviceService;
import com.huizhixin.integrated.service.impl.AttendDeviceServiceImpl;
import com.huizhixin.smart.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.service.IEmployeeUserService;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description: 用户
 * @Author: jeecg-boot
 * @Date: 2019-08-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "用户")
@RestController
@RequestMapping("/integrated/user")
public class UserController {
    @Autowired
    private IEmployeeUserService userService;
    @Autowired
    private DeviceUserService deviceUserService;
    @Autowired
    private IAttendDeviceService attendDeviceService;

    @ApiOperation(value = "字典组列表")
    @RequestMapping(value = "/geyDictgroupList")
    public Result<List<DictItemBean>> geyDictgroupList(String dictname,
                                                       HttpServletRequest req) {
        Result<List<DictItemBean>> result = new Result<>();
        List<DictItemBean> list = userService.geyDictgroupList(dictname);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @ApiOperation(value = "保存字典组列表")
    @RequestMapping(value = "/saveDictgroupList")
    public Result<IPage<User>> saveDictgroupList(@RequestBody List<DictItemBean> list,
                                                 HttpServletRequest req) {
        Result<IPage<User>> result = new Result<IPage<User>>();
        userService.saveDictgroupList(list);
        result.setSuccess(true);
        return result;
    }

    /**
     * 分页列表查询
     *
     * @param user
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "用户-分页列表查询")
    @ApiOperation(value = "用户-分页列表查询", notes = "用户-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<User>> queryPageList(User user,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
        Result<IPage<User>> result = new Result<IPage<User>>();
        QueryWrapper<User> queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
        queryWrapper.getExpression().getOrderBy().clear();
        queryWrapper.orderByDesc("createTime");
        Page<User> page = new Page<User>(pageNo, pageSize);
        IPage<User> pageList = userService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @AutoLog(value = "用户-添加")
    @ApiOperation(value = "用户-添加", notes = "用户-添加")
    @PostMapping(value = "/add")
    public Result<User> add(@RequestBody User user) {
        Result<User> result = new Result<User>();
        try {
            user.setCreatetime(new Date());
            userService.save(user);
            addCardDevice(user);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    private void addCardDevice(User user) {
        List<AttendDevice> devices = attendDeviceService.list();
        devices.forEach(item -> {
            try {
                deviceUserService.setHost(item.getIp(), item.getPort() + "");
                DeviceUser deviceUser = new DeviceUser();
                deviceUser.setName(user.getName());
                deviceUser.setUsertype("white");
                String sex = "";
                if (user.getGender() != null && user.getGender() == 1) {
                    sex = "男";
                } else if (user.getGender() != null && user.getGender() == 2) {
                    sex = "女";
                }
                deviceUser.setSex(sex);
                deviceUser.setIcno(user.getCardno());
                if (!StringUtils.isEmpty(user.getPortrait())) {
                    deviceUser.setPassAlgo(true);
                    String path = user.getportraitPath();
                    String base64 = Base64Utils.GetImageStr(path);
                    deviceUser.setPicData1(base64);
                    deviceUser.setPicBase64(base64);
                } else {
                    deviceUser.setPassAlgo(false);
                }
                deviceUserService.addDeviceWhiteList(item.getPassword(), 100, 1, deviceUser);


            } catch (Exception e) {
            }
        });
    }

    /**
     * 编辑
     *
     * @param user
     * @return
     */
    @AutoLog(value = "用户-编辑")
    @ApiOperation(value = "用户-编辑", notes = "用户-编辑")
    @PutMapping(value = "/edit")
    public Result<User> edit(@RequestBody User user) {
        Result<User> result = new Result<User>();
        User userEntity = userService.getById(user.getId());
        if (userEntity == null) {
            result.error500("未找到对应实体");
        } else {

            boolean ok = userService.updateById(user);
            addCardDevice(user);
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
    @AutoLog(value = "用户-通过id删除")
    @ApiOperation(value = "用户-通过id删除", notes = "用户-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            userService.removeById(id);
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
    @AutoLog(value = "用户-批量删除")
    @ApiOperation(value = "用户-批量删除", notes = "用户-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<User> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<User> result = new Result<User>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.userService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "用户-通过id查询")
    @ApiOperation(value = "用户-通过id查询", notes = "用户-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<User> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<User> result = new Result<User>();
        User user = userService.getById(id);
        if (user == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(user);
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
        QueryWrapper<User> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                User user = JSON.parseObject(deString, User.class);
                queryWrapper = QueryGenerator.initQueryWrapper(user, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<User> pageList = userService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "用户列表");
        mv.addObject(NormalExcelConstants.CLASS, User.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用户列表数据", "导出人:Jeecg", "导出信息"));
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
                List<User> listUsers = ExcelImportUtil.importExcel(file.getInputStream(), User.class, params);
                String stockid = "";
                String stockname = "";
                List<DictItemBean> list = userService.geyDictgroupList("pepstocklist");
                for (DictItemBean bean : list) {
                    if (bean.getItemText().equals("userstockid")) {
                        stockid = bean.getItemValue();
                        stockname = bean.getDescription();
                    }
                }
                for (User user : listUsers) {
                    user.setStockid(stockid);
                    user.setStockname(stockname);
                    if (user.getName() == null || user.getName().length() == 0) {
                        user = null;
                        continue;
                    }
                    user.setDelflag(1);
                }
                userService.saveBatch(listUsers);
                return Result.ok("文件导入成功！数据行数:" + listUsers.size());
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
