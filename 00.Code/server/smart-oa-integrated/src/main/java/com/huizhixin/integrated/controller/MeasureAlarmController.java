package com.huizhixin.integrated.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huizhixin.integrated.entity.MeasureAlarm;
import com.huizhixin.integrated.service.IMeasureAlarmService;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.aspect.annotation.AutoLog;
import com.huizhixin.smart.common.util.oConvertUtils;
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
 * @Description: 测量告警
 * @Author: smart-boot
 * @Date:   2019-11-07
 * @Version: V1.0
 */
@Slf4j
@Api(tags="测量告警")
@RestController
@RequestMapping("/integrated/measureAlarm")
public class MeasureAlarmController {
	@Autowired
	private IMeasureAlarmService measureAlarmService;
	
	/**
	  * 分页列表查询
	 * @param measureAlarm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "测量告警-分页列表查询")
	@ApiOperation(value="测量告警-分页列表查询", notes="测量告警-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MeasureAlarm>> queryPageList(MeasureAlarm measureAlarm,
													 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													 HttpServletRequest req) {
		Result<IPage<MeasureAlarm>> result = new Result<IPage<MeasureAlarm>>();
		QueryWrapper<MeasureAlarm> queryWrapper = QueryGenerator.initQueryWrapper(measureAlarm, req.getParameterMap());
		Page<MeasureAlarm> page = new Page<MeasureAlarm>(pageNo, pageSize);
		IPage<MeasureAlarm> pageList = measureAlarmService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param measureAlarm
	 * @return
	 */
	@AutoLog(value = "测量告警-添加")
	@ApiOperation(value="测量告警-添加", notes="测量告警-添加")
	@PostMapping(value = "/add")
	public Result<MeasureAlarm> add(@RequestBody MeasureAlarm measureAlarm) {
		Result<MeasureAlarm> result = new Result<MeasureAlarm>();
		try {
			measureAlarmService.save(measureAlarm);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param measureAlarm
	 * @return
	 */
	@AutoLog(value = "测量告警-编辑")
	@ApiOperation(value="测量告警-编辑", notes="测量告警-编辑")
	@PutMapping(value = "/edit")
	public Result<MeasureAlarm> edit(@RequestBody MeasureAlarm measureAlarm) {
		Result<MeasureAlarm> result = new Result<MeasureAlarm>();
		MeasureAlarm measureAlarmEntity = measureAlarmService.getById(measureAlarm.getId());
		if(measureAlarmEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = measureAlarmService.updateById(measureAlarm);
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
	@AutoLog(value = "测量告警-通过id删除")
	@ApiOperation(value="测量告警-通过id删除", notes="测量告警-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			measureAlarmService.removeById(id);
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
	@AutoLog(value = "测量告警-批量删除")
	@ApiOperation(value="测量告警-批量删除", notes="测量告警-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<MeasureAlarm> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MeasureAlarm> result = new Result<MeasureAlarm>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.measureAlarmService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "测量告警-通过id查询")
	@ApiOperation(value="测量告警-通过id查询", notes="测量告警-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MeasureAlarm> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MeasureAlarm> result = new Result<MeasureAlarm>();
		MeasureAlarm measureAlarm = measureAlarmService.getById(id);
		if(measureAlarm==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(measureAlarm);
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
      QueryWrapper<MeasureAlarm> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MeasureAlarm measureAlarm = JSON.parseObject(deString, MeasureAlarm.class);
              queryWrapper = QueryGenerator.initQueryWrapper(measureAlarm, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MeasureAlarm> pageList = measureAlarmService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "测量告警列表");
      mv.addObject(NormalExcelConstants.CLASS, MeasureAlarm.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("测量告警列表数据", "导出人:Smart", "导出信息"));
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
              List<MeasureAlarm> listMeasureAlarms = ExcelImportUtil.importExcel(file.getInputStream(), MeasureAlarm.class, params);
              measureAlarmService.saveBatch(listMeasureAlarms);
              return Result.ok("文件导入成功！数据行数:" + listMeasureAlarms.size());
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
