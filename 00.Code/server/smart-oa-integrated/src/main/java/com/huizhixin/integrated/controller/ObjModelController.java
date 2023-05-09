package com.huizhixin.integrated.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huizhixin.integrated.comm.LocalFileServerUtil;
import com.huizhixin.integrated.entity.ObjModelDetail;
import com.huizhixin.integrated.entity.result.ObjModelBean;
import com.huizhixin.integrated.service.IObjModelDetailService;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.ObjModel;
import com.huizhixin.integrated.service.IObjModelService;

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
 * @Description: 三维管理
 * @Author: smart-boot
 * @Date: 2019-12-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "三维管理")
@RestController
@RequestMapping("/objModel")
public class ObjModelController {
    @Autowired
    private IObjModelService objModelService;

    /**
     * 分页列表查询
     *
     * @param objModel
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "三维管理-分页列表查询")
    @ApiOperation(value = "三维管理-分页列表查询", notes = "三维管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ObjModel>> queryPageList(ObjModel objModel,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req) {
        Result<IPage<ObjModel>> result = new Result<IPage<ObjModel>>();
        QueryWrapper<ObjModel> queryWrapper = QueryGenerator.initQueryWrapper(objModel, req.getParameterMap());
        Page<ObjModel> page = new Page<ObjModel>(pageNo, pageSize);
        IPage<ObjModel> pageList = objModelService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param objModel
     * @return
     */
    @AutoLog(value = "三维管理-添加")
    @ApiOperation(value = "三维管理-添加", notes = "三维管理-添加")
    @PostMapping(value = "/add")
    public Result<ObjModel> add(@RequestBody ObjModel objModel) {
        Result<ObjModel> result = new Result<ObjModel>();
        try {
            String backgroundImg = objModel.getBackgroundImg();
            if (backgroundImg != null) {
                backgroundImg = backgroundImg.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
            }
            if (backgroundImg != null && backgroundImg.length() > 0) {
                objModel.setBackgroundImg(backgroundImg);
            }
            String objFile = objModel.getObjFile();
            if (objFile != null) {
                objFile = objFile.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
            }
            if (objFile != null && objFile.length() > 0) {
                objModel.setObjFile(objFile);
            }
            objModelService.save(objModel);
            result.success("添加成功！");
            result.setResult(objModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param objModel
     * @return
     */
    @AutoLog(value = "三维管理-编辑")
    @ApiOperation(value = "三维管理-编辑", notes = "三维管理-编辑")
    @PutMapping(value = "/edit")
    public Result<ObjModel> edit(@RequestBody ObjModel objModel) {
        Result<ObjModel> result = new Result<ObjModel>();
        ObjModel objModelEntity = objModelService.getById(objModel.getId());
        if (objModelEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = objModelService.updateById(objModel);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    private void moveModelDest(Integer objId) {
        ObjModel objModel = objModelService.getById(objId);
        String destPath = LocalFileServerUtil.getModel3dbasedir() + objId + "/";
        File destPathFile = new File(destPath);
        if (!destPathFile.exists()) {
            destPathFile.mkdirs();
        }
        try {
            File file = new File(LocalFileServerUtil.getBasedir());
            String replcaename = file.getCanonicalPath().replaceAll("\\\\", "/");
            if (objModel.getObjFile() != null) {
                String sourcePath = LocalFileServerUtil.getBasedir() + objModel.getObjFile();
                try {
                    File objFile = new File(sourcePath);
                    String newobjFilepath = destPath + objFile.getName();
                    File newobjFile = new File(newobjFilepath);
                    objFile.renameTo(newobjFile);
                    String replacePathnewObj = newobjFile.getCanonicalPath().replaceAll("\\\\", "/");
                    String respPath1 = replacePathnewObj.replaceAll(replcaename, "").replaceAll("^/", "");
                    objModel.setObjFile(respPath1);
                    objModelService.updateById(objModel);
                } catch (Exception e) {
                }
            }
            if (objModel.getBackgroundImg() != null && objModel.getBackgroundImg().length() > 0) {
                String sourcePath = LocalFileServerUtil.getBasedir() + objModel.getBackgroundImg();
                try {
                    File objFile = new File(sourcePath);
                    String newobjFilepath = destPath + objFile.getName();
                    File newobjFile = new File(newobjFilepath);
                    objFile.renameTo(newobjFile);
                    String replacePathnewObj = newobjFile.getCanonicalPath().replaceAll("\\\\", "/");
                    String respPath1 = replacePathnewObj.replaceAll(replcaename, "").replaceAll("^/", "");
                    objModel.setBackgroundImg(respPath1);
                    objModelService.updateById(objModel);
                } catch (Exception e) {
                }

            }
            if (objModel.getMtlUrl() != null && objModel.getMtlUrl().length() > 0) {
                String sourcePath = LocalFileServerUtil.getBasedir() + objModel.getMtlUrl();
                try {
                    File objFile = new File(sourcePath);
                    String newobjFilepath = destPath + objFile.getName();
                    File newobjFile = new File(newobjFilepath);
                    objFile.renameTo(newobjFile);
                    String replacePathnewObj = newobjFile.getCanonicalPath().replaceAll("\\\\", "/");
                    String respPath1 = replacePathnewObj.replaceAll(replcaename, "").replaceAll("^/", "");
                    objModel.setMtlUrl(respPath1);
                    objModelService.updateById(objModel);
                } catch (Exception e) {
                }


            }
        } catch (Exception e) {
        }
    }

    @AutoLog(value = "三维管理-编辑")
    @ApiOperation(value = "三维管理-编辑", notes = "三维管理-编辑")
    @PutMapping(value = "/editmodule")
    public Result<ObjModel> editmodule(@RequestBody ObjModel objModel) {
        Result<ObjModel> result = new Result<ObjModel>();
        ObjModel objModelEntity = objModelService.getById(objModel.getId());
        String backgroundImg = objModel.getBackgroundImg();
        if (objModel.getName() != null && objModel.getName().length() > 0) {
            objModelEntity.setName(objModel.getName());
        }
        if (backgroundImg != null) {
            backgroundImg = backgroundImg.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
        }
        if (backgroundImg != null && backgroundImg.length() > 0) {
            objModelEntity.setBackgroundImg(backgroundImg);
        }
        if (objModel.getRotateActive() != null) {
            objModelEntity.setRotateActive(objModel.getRotateActive());
        }
        String objFile = objModel.getObjFile();
        if (objFile != null) {
            objFile = objFile.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
        }
        if (objFile != null && objFile.length() > 0) {
            objModelEntity.setObjFile(objFile);
        }
        if (objModel.getAngleX() != null) {
            objModelEntity.setAngleX(objModel.getAngleX());
        }
        if (objModel.getAngleY() != null) {
            objModelEntity.setAngleY(objModel.getAngleY());
        }
        if (objModel.getAngleZ() != null) {
            objModelEntity.setAngleZ(objModel.getAngleZ());
        }
        if (objModel.getMtlUrl() != null) {
            objModelEntity.setMtlUrl(objModel.getMtlUrl().replaceAll(LocalFileServerUtil.getWebbasedir(), ""));
        }
        if (objModel.getSelectedColor() != null) {
            objModelEntity.setSelectedColor(objModel.getSelectedColor());
        }
        String path = objModel.getPath();
        if (path != null && path.length() > 0) {
            objModelEntity.setPath(path);
        }
        if (objModel.getBackgroundColor() != null) {
            objModelEntity.setBackgroundColor(objModel.getBackgroundColor());
        }
        if (objModel.getIsMovable() != null) {
            objModelEntity.setIsMovable(objModel.getIsMovable());
        }
        if (objModelEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = objModelService.updateById(objModelEntity);
          //  moveModelDest(objModelEntity.getId());
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }


    @AutoLog(value = "三维管理-编辑")
    @ApiOperation(value = "三维管理-修改背景图", notes = "三维管理-编辑")
    @PutMapping(value = "/editbackground")
    public Result<ObjModel> editbackground(@RequestBody ObjModel objModel) {
        Result<ObjModel> result = new Result<ObjModel>();
        ObjModel objModelEntity = objModelService.getById(objModel.getId());
        String backgroundImg = objModel.getBackgroundImg();

        if (backgroundImg != null) {
            backgroundImg = backgroundImg.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
        }
        objModelEntity.setBackgroundImg(backgroundImg);

        objModelEntity.setBackgroundColor(objModel.getBackgroundColor());

        if (objModelEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = objModelService.updateById(objModelEntity);
          //  moveModelDest(objModelEntity.getId());
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    @AutoLog(value = "三维管理-编辑")
    @ApiOperation(value = "三维管理-编辑", notes = "三维管理-编辑")
    @PutMapping(value = "/editRelevant")
    public Result<ObjModel> editRelevant(@RequestBody ObjModel objModel) {
        Result<ObjModel> result = new Result<ObjModel>();
        ObjModel objModelEntity = objModelService.getById(objModel.getId());
        objModelEntity.setFov(objModel.getFov());
        objModelEntity.setQuaternion(objModel.getQuaternion());
        objModelEntity.setAngleX(objModel.getAngleX());
        objModelEntity.setAngleY(objModel.getAngleY());
        objModelEntity.setAngleZ(objModel.getAngleZ());
        String backgroundImg = objModel.getBackgroundImg();
        if (backgroundImg != null) {
            backgroundImg = backgroundImg.replaceAll(LocalFileServerUtil.getWebbasedir(), "");
        }
        objModelEntity.setBackgroundImg(backgroundImg);
        objModelEntity.setBackgroundColor(objModel.getBackgroundColor());

        if (objModelEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = objModelService.updateById(objModelEntity);
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
    @AutoLog(value = "三维管理-通过id删除")
    @ApiOperation(value = "三维管理-通过id删除", notes = "三维管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            objModelService.removeById(id);
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
    @AutoLog(value = "三维管理-批量删除")
    @ApiOperation(value = "三维管理-批量删除", notes = "三维管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<ObjModel> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ObjModel> result = new Result<ObjModel>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.objModelService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "三维管理-通过id查询")
    @ApiOperation(value = "三维管理-通过id查询", notes = "三维管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ObjModel> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ObjModel> result = new Result<ObjModel>();
        ObjModel objModel = objModelService.getById(id);
        if (objModel == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(objModel);
            result.setSuccess(true);
        }
        return result;
    }

    @Autowired
    private IObjModelDetailService modelDetailService;

    @AutoLog(value = "三维管理-通过id查询")
    @ApiOperation(value = "三维管理-通过id查询", notes = "三维管理-通过id查询")
    @GetMapping(value = "/queryDetailsById")
    public Result<ObjModelBean> queryDetailsById(@RequestParam(name = "id", required = true) String id) {
        Result<ObjModelBean> result = new Result<ObjModelBean>();
        ObjModelBean detail = new ObjModelBean();
        ObjModel objModel = objModelService.getById(id);
        detail.setObjModel(objModel);
        QueryWrapper<ObjModelDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("obj_id", id);
        List<ObjModelDetail> detailslist = modelDetailService.list(queryWrapper);
        detail.setList(detailslist);
        if (objModel == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(detail);
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
        QueryWrapper<ObjModel> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                ObjModel objModel = JSON.parseObject(deString, ObjModel.class);
                queryWrapper = QueryGenerator.initQueryWrapper(objModel, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<ObjModel> pageList = objModelService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "三维管理列表");
        mv.addObject(NormalExcelConstants.CLASS, ObjModel.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("三维管理列表数据", "导出人:Smart", "导出信息"));
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
                List<ObjModel> listObjModels = ExcelImportUtil.importExcel(file.getInputStream(), ObjModel.class, params);
                objModelService.saveBatch(listObjModels);
                return Result.ok("文件导入成功！数据行数:" + listObjModels.size());
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


    public static void main(String[] args) {
        String backgroundImg = "aaaaabb".replaceAll("aaaaa", "");
        int a = 1;
    }
}
