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
import com.huizhixin.integrated.entity.PersonResume;
import com.huizhixin.integrated.service.IPersonResumeService;
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
 * @Description: 简历管理
 * @Author: smart-boot
 * @Date:   2020-01-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="简历管理")
@RestController
@RequestMapping("/integrated/personResume")
public class PersonResumeController {
	@Autowired
	private IPersonResumeService personResumeService;
	
	/**
	  * 分页列表查询
	 * @param personResume
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "简历管理-分页列表查询")
	@ApiOperation(value="简历管理-分页列表查询", notes="简历管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PersonResume>> queryPageList(PersonResume personResume,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<PersonResume>> result = new Result<IPage<PersonResume>>();
		QueryWrapper<PersonResume> queryWrapper = QueryGenerator.initQueryWrapper(personResume, req.getParameterMap());
		Page<PersonResume> page = new Page<PersonResume>(pageNo, pageSize);
		IPage<PersonResume> pageList = personResumeService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param personResume
	 * @return
	 */
	@AutoLog(value = "简历管理-添加")
	@ApiOperation(value="简历管理-添加", notes="简历管理-添加")
	@PostMapping(value = "/add")
	public Result<PersonResume> add(@RequestBody PersonResume personResume) {
		Result<PersonResume> result = new Result<PersonResume>();
		try {
			personResumeService.save(personResume);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param personResume
	 * @return
	 */
	@AutoLog(value = "简历管理-编辑")
	@ApiOperation(value="简历管理-编辑", notes="简历管理-编辑")
	@PutMapping(value = "/edit")
	public Result<PersonResume> edit(@RequestBody PersonResume personResume) {
		Result<PersonResume> result = new Result<PersonResume>();
		PersonResume personResumeEntity = personResumeService.getById(personResume.getId());
		if(personResumeEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = personResumeService.updateById(personResume);
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
	@AutoLog(value = "简历管理-通过id删除")
	@ApiOperation(value="简历管理-通过id删除", notes="简历管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			personResumeService.removeById(id);
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
	@AutoLog(value = "简历管理-批量删除")
	@ApiOperation(value="简历管理-批量删除", notes="简历管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<PersonResume> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<PersonResume> result = new Result<PersonResume>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.personResumeService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "简历管理-通过id查询")
	@ApiOperation(value="简历管理-通过id查询", notes="简历管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PersonResume> queryById(@RequestParam(name="id",required=true) String id) {
		Result<PersonResume> result = new Result<PersonResume>();
		PersonResume personResume = personResumeService.getById(id);
		if(personResume==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(personResume);
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
      QueryWrapper<PersonResume> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              PersonResume personResume = JSON.parseObject(deString, PersonResume.class);
              queryWrapper = QueryGenerator.initQueryWrapper(personResume, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<PersonResume> pageList = personResumeService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "简历管理列表");
      mv.addObject(NormalExcelConstants.CLASS, PersonResume.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("简历管理列表数据", "导出人:Smart", "导出信息"));
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
              List<PersonResume> listPersonResumes = ExcelImportUtil.importExcel(file.getInputStream(), PersonResume.class, params);
              personResumeService.saveBatch(listPersonResumes);
              return Result.ok("文件导入成功！数据行数:" + listPersonResumes.size());
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
