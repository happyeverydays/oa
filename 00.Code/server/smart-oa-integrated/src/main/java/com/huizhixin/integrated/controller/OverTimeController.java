package com.huizhixin.integrated.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.integrated.entity.OverTime;
import com.huizhixin.integrated.service.IOverTimeService;
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
 * @Description: 请假
 * @Author: smart-boot
 * @Date:   2020-01-14
 * @Version: V1.0
 */
@Slf4j
@Api(tags="请假")
@RestController
@RequestMapping("/overTime")
public class OverTimeController {
	@Autowired
	private IOverTimeService overTimeService;
	
	/**
	  * 分页列表查询
	 * @param overTime
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "请假-分页列表查询")
	@ApiOperation(value="请假-分页列表查询", notes="请假-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OverTime>> queryPageList(OverTime overTime,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<OverTime>> result = new Result<IPage<OverTime>>();
		QueryWrapper<OverTime> queryWrapper = QueryGenerator.initQueryWrapper(overTime, req.getParameterMap());
		Page<OverTime> page = new Page<OverTime>(pageNo, pageSize);
		IPage<OverTime> pageList = overTimeService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param overTime
	 * @return
	 */
	@AutoLog(value = "请假-添加")
	@ApiOperation(value="请假-添加", notes="请假-添加")
	@PostMapping(value = "/add")
	public Result<OverTime> add(@RequestBody OverTime overTime) {
		Result<OverTime> result = new Result<OverTime>();
		try {
			overTimeService.save(overTime);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param overTime
	 * @return
	 */
	@AutoLog(value = "请假-编辑")
	@ApiOperation(value="请假-编辑", notes="请假-编辑")
	@PutMapping(value = "/edit")
	public Result<OverTime> edit(@RequestBody OverTime overTime) {
		Result<OverTime> result = new Result<OverTime>();
		OverTime overTimeEntity = overTimeService.getById(overTime.getId());
		if(overTimeEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = overTimeService.updateById(overTime);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "请假-通过id删除")
	@ApiOperation(value="请假-通过id删除", notes="请假-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			overTimeService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "请假-批量删除")
	@ApiOperation(value="请假-批量删除", notes="请假-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<OverTime> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<OverTime> result = new Result<OverTime>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.overTimeService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "请假-通过id查询")
	@ApiOperation(value="请假-通过id查询", notes="请假-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OverTime> queryById(@RequestParam(name="id",required=true) String id) {
		Result<OverTime> result = new Result<OverTime>();
		OverTime overTime = overTimeService.getById(id);
		if(overTime==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(overTime);
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
      QueryWrapper<OverTime> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              OverTime overTime = JSON.parseObject(deString, OverTime.class);
              queryWrapper = QueryGenerator.initQueryWrapper(overTime, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<OverTime> pageList = overTimeService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "请假列表");
      mv.addObject(NormalExcelConstants.CLASS, OverTime.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("请假列表数据", "导出人:Smart", "导出信息"));
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
              List<OverTime> listOverTimes = ExcelImportUtil.importExcel(file.getInputStream(), OverTime.class, params);
              overTimeService.saveBatch(listOverTimes);
              return Result.ok("文件导入成功！数据行数:" + listOverTimes.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
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
