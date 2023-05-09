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
import com.huizhixin.integrated.entity.MonitorAlarmFire;
import com.huizhixin.integrated.service.IMonitorAlarmFireService;
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
 * @Description: 火警
 * @Author: smart-boot
 * @Date:   2020-01-08
 * @Version: V1.0
 */
@Slf4j
@Api(tags="火警")
@RestController
@RequestMapping("/monitorAlarmFire")
public class MonitorAlarmFireController {
	@Autowired
	private IMonitorAlarmFireService monitorAlarmFireService;
	
	/**
	  * 分页列表查询
	 * @param monitorAlarmFire
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "火警-分页列表查询")
	@ApiOperation(value="火警-分页列表查询", notes="火警-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<MonitorAlarmFire>> queryPageList(MonitorAlarmFire monitorAlarmFire,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MonitorAlarmFire>> result = new Result<IPage<MonitorAlarmFire>>();
		QueryWrapper<MonitorAlarmFire> queryWrapper = QueryGenerator.initQueryWrapper(monitorAlarmFire, req.getParameterMap());
		Page<MonitorAlarmFire> page = new Page<MonitorAlarmFire>(pageNo, pageSize);
		IPage<MonitorAlarmFire> pageList = monitorAlarmFireService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param monitorAlarmFire
	 * @return
	 */
	@AutoLog(value = "火警-添加")
	@ApiOperation(value="火警-添加", notes="火警-添加")
	@PostMapping(value = "/add")
	public Result<MonitorAlarmFire> add(@RequestBody MonitorAlarmFire monitorAlarmFire) {
		Result<MonitorAlarmFire> result = new Result<MonitorAlarmFire>();
		try {
			monitorAlarmFireService.save(monitorAlarmFire);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param monitorAlarmFire
	 * @return
	 */
	@AutoLog(value = "火警-编辑")
	@ApiOperation(value="火警-编辑", notes="火警-编辑")
	@PutMapping(value = "/edit")
	public Result<MonitorAlarmFire> edit(@RequestBody MonitorAlarmFire monitorAlarmFire) {
		Result<MonitorAlarmFire> result = new Result<MonitorAlarmFire>();
		MonitorAlarmFire monitorAlarmFireEntity = monitorAlarmFireService.getById(monitorAlarmFire.getId());
		if(monitorAlarmFireEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = monitorAlarmFireService.updateById(monitorAlarmFire);
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
	@AutoLog(value = "火警-通过id删除")
	@ApiOperation(value="火警-通过id删除", notes="火警-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			monitorAlarmFireService.removeById(id);
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
	@AutoLog(value = "火警-批量删除")
	@ApiOperation(value="火警-批量删除", notes="火警-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<MonitorAlarmFire> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MonitorAlarmFire> result = new Result<MonitorAlarmFire>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.monitorAlarmFireService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "火警-通过id查询")
	@ApiOperation(value="火警-通过id查询", notes="火警-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MonitorAlarmFire> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MonitorAlarmFire> result = new Result<MonitorAlarmFire>();
		MonitorAlarmFire monitorAlarmFire = monitorAlarmFireService.getById(id);
		if(monitorAlarmFire==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(monitorAlarmFire);
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
      QueryWrapper<MonitorAlarmFire> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MonitorAlarmFire monitorAlarmFire = JSON.parseObject(deString, MonitorAlarmFire.class);
              queryWrapper = QueryGenerator.initQueryWrapper(monitorAlarmFire, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MonitorAlarmFire> pageList = monitorAlarmFireService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "火警列表");
      mv.addObject(NormalExcelConstants.CLASS, MonitorAlarmFire.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("火警列表数据", "导出人:Smart", "导出信息"));
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
              List<MonitorAlarmFire> listMonitorAlarmFires = ExcelImportUtil.importExcel(file.getInputStream(), MonitorAlarmFire.class, params);
              monitorAlarmFireService.saveBatch(listMonitorAlarmFires);
              return Result.ok("文件导入成功！数据行数:" + listMonitorAlarmFires.size());
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
