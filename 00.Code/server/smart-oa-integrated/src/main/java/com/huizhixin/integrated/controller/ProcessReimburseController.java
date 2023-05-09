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
import com.huizhixin.integrated.entity.ProcessReimburse;
import com.huizhixin.integrated.service.IProcessReimburseService;
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
 * @Description: 报销
 * @Author: smart-boot
 * @Date:   2020-01-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="报销")
@RestController
@RequestMapping("/processReimburse")
public class ProcessReimburseController {
	@Autowired
	private IProcessReimburseService processReimburseService;
	
	/**
	  * 分页列表查询
	 * @param processReimburse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "报销-分页列表查询")
	@ApiOperation(value="报销-分页列表查询", notes="报销-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ProcessReimburse>> queryPageList(ProcessReimburse processReimburse,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<ProcessReimburse>> result = new Result<IPage<ProcessReimburse>>();
		QueryWrapper<ProcessReimburse> queryWrapper = QueryGenerator.initQueryWrapper(processReimburse, req.getParameterMap());
		Page<ProcessReimburse> page = new Page<ProcessReimburse>(pageNo, pageSize);
		IPage<ProcessReimburse> pageList = processReimburseService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param processReimburse
	 * @return
	 */
	@AutoLog(value = "报销-添加")
	@ApiOperation(value="报销-添加", notes="报销-添加")
	@PostMapping(value = "/add")
	public Result<ProcessReimburse> add(@RequestBody ProcessReimburse processReimburse) {
		Result<ProcessReimburse> result = new Result<ProcessReimburse>();
		try {
			processReimburseService.save(processReimburse);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param processReimburse
	 * @return
	 */
	@AutoLog(value = "报销-编辑")
	@ApiOperation(value="报销-编辑", notes="报销-编辑")
	@PutMapping(value = "/edit")
	public Result<ProcessReimburse> edit(@RequestBody ProcessReimburse processReimburse) {
		Result<ProcessReimburse> result = new Result<ProcessReimburse>();
		ProcessReimburse processReimburseEntity = processReimburseService.getById(processReimburse.getId());
		if(processReimburseEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = processReimburseService.updateById(processReimburse);
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
	@AutoLog(value = "报销-通过id删除")
	@ApiOperation(value="报销-通过id删除", notes="报销-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			processReimburseService.removeById(id);
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
	@AutoLog(value = "报销-批量删除")
	@ApiOperation(value="报销-批量删除", notes="报销-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<ProcessReimburse> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<ProcessReimburse> result = new Result<ProcessReimburse>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.processReimburseService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报销-通过id查询")
	@ApiOperation(value="报销-通过id查询", notes="报销-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ProcessReimburse> queryById(@RequestParam(name="id",required=true) String id) {
		Result<ProcessReimburse> result = new Result<ProcessReimburse>();
		ProcessReimburse processReimburse = processReimburseService.getById(id);
		if(processReimburse==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(processReimburse);
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
      QueryWrapper<ProcessReimburse> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              ProcessReimburse processReimburse = JSON.parseObject(deString, ProcessReimburse.class);
              queryWrapper = QueryGenerator.initQueryWrapper(processReimburse, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<ProcessReimburse> pageList = processReimburseService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "报销列表");
      mv.addObject(NormalExcelConstants.CLASS, ProcessReimburse.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("报销列表数据", "导出人:Smart", "导出信息"));
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
              List<ProcessReimburse> listProcessReimburses = ExcelImportUtil.importExcel(file.getInputStream(), ProcessReimburse.class, params);
              processReimburseService.saveBatch(listProcessReimburses);
              return Result.ok("文件导入成功！数据行数:" + listProcessReimburses.size());
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
