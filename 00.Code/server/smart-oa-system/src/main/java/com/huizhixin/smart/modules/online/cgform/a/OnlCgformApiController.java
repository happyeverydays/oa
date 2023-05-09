package com.huizhixin.smart.modules.online.cgform.a;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.poi.ss.usermodel.Workbook;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.aspect.annotation.PermissionData;
import com.huizhixin.smart.common.system.api.ISysBaseAPI;
import com.huizhixin.smart.common.system.vo.DictModel;
import com.huizhixin.smart.common.util.BrowserUtils;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.common.util.UUIDGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.modules.online.cgform.b.b;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformButton;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJs;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.model.OnlGenerateModel;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformFieldService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformHeadService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformSqlService;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.online.config.util.d;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController("onlCgformApiController")
@RequestMapping({"/online/cgform/api"})
public class OnlCgformApiController {
	private static final Logger a = LoggerFactory.getLogger(OnlCgformApiController.class);
	@Autowired
	private IOnlCgformHeadService onlCgformHeadService;
	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;
	@Autowired
	private IOnlCgformSqlService onlCgformSqlService;
	@Autowired
	private ISysBaseAPI sysBaseAPI;

	@PostMapping({"/addAll"})
	public Result<?> a(@RequestBody com.huizhixin.smart.modules.online.cgform.model.a var1) {
		try {
			String var2 = var1.getHead().getTableName();
			return d.a(var2) ? Result.error("数据库表[" + var2 + "]已存在,请从数据库导入表单") : this.onlCgformHeadService.addAll(var1);
		} catch (Exception var3) {
			a.error("OnlCgformApiController.addAll()发生异常：" + var3.getMessage());
			return Result.error("操作失败");
		}
	}

	@PutMapping({"/editAll"})
	public Result<?> b(@RequestBody com.huizhixin.smart.modules.online.cgform.model.a var1) {
		try {
			return this.onlCgformHeadService.editAll(var1);
		} catch (Exception var3) {
			a.error("OnlCgformApiController.editAll()发生异常：" + var3.getMessage());
			return Result.error("操作失败");
		}
	}

	@GetMapping({"/getColumns/{code}"})
	public Result<Map<String, Object>> a(@PathVariable("code") String var1) {
		Result var2 = new Result();
		OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var3 == null) {
			var2.error500("实体不存在");
			return var2;
		} else {
			LambdaQueryWrapper<OnlCgformField> var4 = new LambdaQueryWrapper();
			var4.eq(OnlCgformField::getCgformHeadId, var1);
			var4.eq(OnlCgformField::getIsShowList, 1);
			var4.orderByAsc(OnlCgformField::getOrderNum);
			List var5 = this.onlCgformFieldService.list(var4);
			List var6 = this.onlCgformFieldService.selectOnlineHideColumns(var3.getTableName());
			ArrayList var7 = new ArrayList();
			HashMap var8 = new HashMap();
			Iterator var9 = var5.iterator();

			while (true) {
				OnlCgformField var10;
				String var11;
				do {
					do {
						if (!var9.hasNext()) {
							HashMap var16 = new HashMap();
							var16.put("columns", var7);
							var16.put("dictOptions", var8);
							var16.put("formTemplate", var3.getFormTemplate());
							var16.put("description", var3.getTableTxt());
							var16.put("currentTableName", var3.getTableName());
							List var17 = this.onlCgformHeadService.queryButtonList(var1, true);
							ArrayList var18 = new ArrayList();
							Iterator var19 = var17.iterator();

							while (var19.hasNext()) {
								OnlCgformButton var21 = (OnlCgformButton) var19.next();
								if (!var6.contains(var21.getButtonCode())) {
									var18.add(var21);
								}
							}

							var16.put("cgButtonList", var18);
							OnlCgformEnhanceJs var20 = this.onlCgformHeadService.queryEnhanceJs(var1, "list");
							if (var20 != null && oConvertUtils.isNotEmpty(var20.getCgJs())) {
								var16.put("enhanceJs", b.b(var20.getCgJs(), var17));
							}

							var16.put("hideColumns", var6);
							if ("Y".equals(var3.getIsTree())) {
								var16.put("pidField", var3.getTreeParentIdField());
								var16.put("hasChildrenField", var3.getTreeIdField());
								var16.put("textField", var3.getTreeFieldname());
							}

							var2.setResult(var16);
							return var2;
						}

						var10 = (OnlCgformField) var9.next();
						var11 = var10.getDbFieldName();
					} while ("id".equals(var11));
				} while (var6.contains(var11));

				HashMap var12 = new HashMap(3);
				var12.put("title", var10.getDbFieldTxt());
				var12.put("dataIndex", var11);
				var12.put("align", "center");
				String var13 = var10.getDictField();
				if (oConvertUtils.isNotEmpty(var13) && !"popup".equals(var10.getFieldShowType())) {
					Object var14 = new ArrayList();
					if (oConvertUtils.isNotEmpty(var10.getDictTable())) {
						var14 = this.sysBaseAPI.queryTableDictItemsByCode(var10.getDictTable(), var10.getDictText(),
								var13);
					} else if (oConvertUtils.isNotEmpty(var10.getDictField())) {
						var14 = this.sysBaseAPI.queryDictItemsByCode(var13);
					}

					var8.put(var11, var14);
					var12.put("customRender", var11);
				}

				if ("sel_depart".equals(var10.getFieldShowType())) {
					List var22 = this.sysBaseAPI.queryAllDepartBackDictModel();
					var8.put(var11, var22);
					var12.put("customRender", var11);
				}

				String var23 = var10.getFieldShowType();
				JSONObject var15;
				if (var23.indexOf("file") >= 0) {
					var15 = new JSONObject();
					var15.put("customRender", "fileSlot");
					var12.put("scopedSlots", var15);
				} else if (var23.indexOf("image") >= 0) {
					var15 = new JSONObject();
					var15.put("customRender", "imgSlot");
					var12.put("scopedSlots", var15);
				} else if (var23.indexOf("editor") >= 0) {
					var15 = new JSONObject();
					var15.put("customRender", "htmlSlot");
					var12.put("scopedSlots", var15);
				} else if ("date".equals(var23)) {
					var15 = new JSONObject();
					var15.put("customRender", "dateSlot");
					var12.put("scopedSlots", var15);
				}

				String var24 = var10.getDbType();
				if ("int".equals(var24) || "double".equals(var24) || "BigDecimal".equals(var24)
						|| "Date".equals(var24)) {
					var12.put("sorter", true);
				}

				var7.add(var12);
			}
		}
	}

	@GetMapping({"/getData/{code}"})
	@PermissionData
	public Result<Map<String, Object>> a(@PathVariable("code") String var1, HttpServletRequest var2) {
		Result var3 = new Result();
		OnlCgformHead var4 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var4 == null) {
			var3.error500("实体不存在");
			return var3;
		} else {
			try {
				String var5 = var4.getTableName();
				Map var6 = b.a(var2);
				Map var7 = this.onlCgformFieldService.queryAutolistPage(var5, var1, var6, (List) null);
				var3.setResult(var7);
			} catch (Exception var8) {
				a.error(var8.getMessage(), var8);
				var3.error500("数据库查询失败" + var8.getMessage());
			}

			return var3;
		}
	}

	@GetMapping({"/getFormItem/{code}"})
	public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2) {
		OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var3 == null) {
			Result.error("表不存在");
		}

		Result var4 = new Result();
		JSONObject var5 = new JSONObject();
		OnlCgformEnhanceJs var6 = this.onlCgformHeadService.queryEnhanceJs(var1, "form");
		List var7 = this.onlCgformFieldService.queryAvailableFields(var3.getId(), var3.getTableName(), false);
		List var8 = this.onlCgformFieldService.queryDisabledFields(var3.getTableName());
		b.a(var6, var3.getTableName(), var7, true);
		com.huizhixin.smart.modules.online.cgform.model.b var9 = null;
		if ("Y".equals(var3.getIsTree())) {
			var9 = new com.huizhixin.smart.modules.online.cgform.model.b();
			var9.setCodeField("id");
			var9.setFieldName(var3.getTreeParentIdField());
			var9.setPidField(var3.getTreeParentIdField());
			var9.setPidValue("0");
			var9.setHsaChildField(var3.getTreeIdField());
			var9.setTableName(var3.getTableName());
			var9.setTextField(var3.getTreeFieldname());
		}

		JSONObject var10 = b.a(var7, var8, var9);
		var10.put("table", var3.getTableName());
		if (var3.getTableType() == 2) {
			String var11 = var3.getSubTableStr();
			if (oConvertUtils.isNotEmpty(var11)) {
				String[] var12 = var11.split(",");
				int var13 = var12.length;

				for (int var14 = 0; var14 < var13; ++var14) {
					String var15 = var12[var14];
					OnlCgformHead var16 = (OnlCgformHead) this.onlCgformHeadService
							.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var15));
					if (var16 != null) {
						List var17 = this.onlCgformFieldService.queryAvailableFields(var16.getId(),
								var16.getTableName(), false);
						b.a(var6, var16.getTableName(), var17);
						JSONObject var18 = new JSONObject();
						if (1 == var16.getRelationType()) {
							var18 = b.a(var17, (List) null, (com.huizhixin.smart.modules.online.cgform.model.b) null);
						} else {
							var18.put("columns", b.c(var17));
						}

						var18.put("columns", b.c(var17));
						var18.put("describe", var16.getTableTxt());
						var18.put("key", var16.getTableName());
						var18.put("view", "tab");
						var18.put("relationType", var16.getRelationType());
						var10.getJSONObject("properties").put(var16.getTableName(), var18);
					}
				}
			}
		}

		var5.put("schema", var10);
		var5.put("head", var3);
		List var19 = this.onlCgformHeadService.queryButtonList(var1, false);
		if (var19 != null && var19.size() > 0) {
			var5.put("cgButtonList", var19);
		}

		if (var6 != null && oConvertUtils.isNotEmpty(var6.getCgJs())) {
			var5.put("enhanceJs", b.c(b.c(var6.getCgJs(), var19)));
		}

		var4.setResult(var5);
		return var4;
	}

	@GetMapping({"/getFormItemBytbname/{table}"})
	public Result<?> b(@PathVariable("table") String var1) {
		Result var2 = new Result();
		LambdaQueryWrapper<OnlCgformHead> var3 = new LambdaQueryWrapper();
		var3.eq(OnlCgformHead::getTableName, var1);
		OnlCgformHead var4 = (OnlCgformHead) this.onlCgformHeadService.getOne(var3);
		if (var4 == null) {
			Result.error("表不存在");
		}

		JSONObject var5 = new JSONObject();
		var5.put("schema", this.onlCgformHeadService.queryFormItem(var4));
		var5.put("head", var4);
		var2.setResult(var5);
		return var2;
	}

	@GetMapping({"/getEnhanceJs/{code}"})
	public Result<?> c(@PathVariable("code") String var1, HttpServletRequest var2) {
		String var3 = "";
		OnlCgformEnhanceJs var4 = this.onlCgformHeadService.queryEnhanceJs(var1, "form");
		if (var4 != null && oConvertUtils.isNotEmpty(var4.getCgJs())) {
			var3 = b.b(var4.getCgJs(), (List) null);
		}

		return Result.ok(var3);
	}

	@GetMapping({"/form/{code}/{id}"})
	public Result<?> a(@PathVariable("code") String var1, @PathVariable("id") String var2) {
		try {
			Map var3 = this.onlCgformHeadService.queryManyFormData(var1, var2);
			return Result.ok(b.a(var3));
		} catch (Exception var4) {
			a.error("Online表单查询异常：" + var4.getMessage(), var4);
			return Result.error("查询失败");
		}
	}

	@GetMapping({"/subform/{table}/{mainId}"})
	public Result<?> b(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
		try {
			Map var3 = this.onlCgformHeadService.querySubFormData(var1, var2);
			return Result.ok(b.a(var3));
		} catch (Exception var4) {
			a.error("Online表单查询异常：" + var4.getMessage(), var4);
			return Result.error("查询失败" + var4.getMessage());
		}
	}

	@GetMapping({"/subform/list/{table}/{mainId}"})
	public Result<?> c(@PathVariable("table") String var1, @PathVariable("mainId") String var2) {
		try {
			return Result.ok(this.onlCgformHeadService.queryManySubFormData(var1, var2));
		} catch (Exception var4) {
			a.error("Online表单查询异常：" + var4.getMessage(), var4);
			return Result.error("查询失败" + var4.getMessage());
		}
	}

	@GetMapping({"/form/table_name/{tableName}/{dataId}"})
	public Result<?> d(@PathVariable("tableName") String var1, @PathVariable("dataId") String var2) {
		try {
			LambdaQueryWrapper<OnlCgformHead> var3 = new LambdaQueryWrapper();
			var3.eq(OnlCgformHead::getTableName, var1);
			OnlCgformHead var4 = (OnlCgformHead) this.onlCgformHeadService.getOne(var3);
			if (var4 == null) {
				throw new Exception("OnlCgform tableName: " + var1 + " 不存在！");
			} else {
				return this.a(var4.getId(), var2);
			}
		} catch (Exception var5) {
			a.error("Online表单查询异常：" + var5.getMessage(), var5);
			return Result.error("查询失败");
		}
	}

	@PostMapping({"/form/{code}"})
	public Result<?> a(@PathVariable("code") String var1, @RequestBody JSONObject var2) {
		try {
			this.onlCgformHeadService.saveManyFormData(var1, var2);
		} catch (Exception var4) {
			a.error("OnlCgformApiController.formAdd()发生异常：" + var4.getMessage());
			return Result.error("保存失败");
		}

		return Result.ok("保存成功!");
	}

	@PutMapping({"/form/{code}"})
	public Result<?> b(@PathVariable("code") String var1, @RequestBody JSONObject var2) {
		try {
			this.onlCgformHeadService.editManyFormData(var1, var2);
		} catch (Exception var4) {
			a.error("OnlCgformApiController.formEdit()发生异常：" + var4.getMessage());
			return Result.error("修改失败");
		}

		return Result.ok("修改成功!");
	}

	@DeleteMapping({"/form/{code}/{id}"})
	public Result<?> e(@PathVariable("code") String var1, @PathVariable("id") String var2) {
		try {
			if (var2.indexOf(",") > 0) {
				OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
				if (var3 == null) {
					return Result.error("实体不存在");
				}

				String var4 = var3.getTableName();
				this.onlCgformFieldService.deleteAutoList(var4, var2);
			} else {
				this.onlCgformHeadService.deleteOneTableInfo(var1, var2);
			}
		} catch (Exception var5) {
			a.error("OnlCgformApiController.formEdit()发生异常：" + var5.getMessage());
			return Result.error("删除失败");
		}

		return Result.ok("删除成功!");
	}

	@GetMapping({"/getQueryInfo/{code}"})
	public Result<?> c(@PathVariable("code") String var1) {
		try {
			List var2 = this.onlCgformFieldService.getAutoListQueryInfo(var1);
			return Result.ok(var2);
		} catch (Exception var3) {
			a.error("OnlCgformApiController.getQueryInfo()发生异常：" + var3.getMessage());
			return Result.error("查询失败");
		}
	}

	@PostMapping({"/doDbSynch/{code}/{synMethod}"})
	public Result<?> f(@PathVariable("code") String var1, @PathVariable("synMethod") String var2) {
		try {
			this.onlCgformHeadService.doDbSynch(var1, var2);
		} catch (Exception var4) {
			a.error("OnlCgformApiController.doDbSynch()发生异常：" + var4.getMessage());
			var4.printStackTrace();
			return Result.error("同步数据库失败");
		}

		return Result.ok("同步数据库成功!");
	}

	@GetMapping({"/exportXls/{code}"})
	 public void a(@PathVariable("code") final String s, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadService.getById((Serializable)s);
        if (onlCgformHead == null) {
            return;
        }
        final String tableTxt = onlCgformHead.getTableTxt();
        final String parameter = httpServletRequest.getParameter("paramsStr");
        Map<String, Object> map2 = new HashMap<String, Object>();
        String decode = null;
        if (oConvertUtils.isNotEmpty((Object)parameter)) {
            try {
                decode = URLDecoder.decode(parameter, "UTF-8");
            }
            catch (UnsupportedEncodingException ex) {
                com.huizhixin.smart.modules.online.cgform.a.OnlCgformApiController.a.error(ex.getMessage(), (Throwable)ex);
            }
            if (decode != null) {
                map2 = (Map<String, Object>)JSONObject.parseObject(decode, (Class)Map.class);
            }
        }
        final LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, s);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        final List list = this.onlCgformFieldService.list((Wrapper)lambdaQueryWrapper);
        final String a = b.a(onlCgformHead.getTableName(), list, map2);
        com.huizhixin.smart.modules.online.cgform.a.OnlCgformApiController.a.info("-----------\u52a8\u6001\u5217\u8868\u67e5\u8be2sql\u300b\u300b" + a);
        final List<Map<String, Object>> queryListData = this.onlCgformHeadService.queryListData(a);
        List<Object> list2 = new ArrayList<Object>();
        String var14 = ((Map) map2).get("selections") == null ? null : ((Map) map2).get("selections").toString();
        if (oConvertUtils.isNotEmpty(var14)) {
            final String s2;
            list2 = queryListData.stream().filter(map -> Arrays.asList(var14.split(",")).contains(map.get("id"))).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList());
        }
        else {
            list2.addAll(queryListData);
        }
        final List<ExcelExportEntity> a2 = this.a(list, "id");
        if (onlCgformHead.getTableType() == 2) {
            final String subTableStr = onlCgformHead.getSubTableStr();
            if (oConvertUtils.isNotEmpty((Object)subTableStr)) {
                final String[] split = subTableStr.split(",");
                for (int length = split.length, i = 0; i < length; ++i) {
                    this.a(split[i], map2, (List)list2, a2);
                }
            }
        }
        final Workbook exportExcel = ExcelExportUtil.exportExcel(new ExportParams((String)null, tableTxt), (List)a2, (Collection)list2);
        final OutputStream outputStream = null;
        try {
            httpServletResponse.setContentType("application/x-msdownload;charset=utf-8");
            final String checkBrowse = BrowserUtils.checkBrowse(httpServletRequest);
            final String string = onlCgformHead.getTableTxt() + "-v" + onlCgformHead.getTableVersion();
            if ("MSIE".equalsIgnoreCase(checkBrowse.substring(0, 4))) {
                httpServletResponse.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(string, "UTF-8") + ".xls");
            }
            else {
                httpServletResponse.setHeader("content-disposition", "attachment;filename=" + new String(string.getBytes("UTF-8"), "ISO8859-1") + ".xls");
            }
            exportExcel.write((OutputStream)httpServletResponse.getOutputStream());
            httpServletResponse.flushBuffer();
        }
        catch (Exception ex2) {
        	com.huizhixin.smart.modules.online.cgform.a.OnlCgformApiController.a.error("--\u901a\u8fc7\u6d41\u7684\u65b9\u5f0f\u83b7\u53d6\u6587\u4ef6\u5f02\u5e38--" + ex2.getMessage());
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException ex3) {
                	com.huizhixin.smart.modules.online.cgform.a.OnlCgformApiController.a.error(ex3.getMessage(), (Throwable)ex3);
                }
            }
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException ex4) {
                	com.huizhixin.smart.modules.online.cgform.a.OnlCgformApiController.a.error(ex4.getMessage(), (Throwable)ex4);
                }
            }
        }
    }

	@PostMapping({"/importXls/{code}"})
	public Result<?> b(@PathVariable("code") String var1, HttpServletRequest var2, HttpServletResponse var3) {
		long var4 = System.currentTimeMillis();
		Result var6 = new Result();
		String var7 = "";

		try {
			OnlCgformHead var8 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
			if (var8 == null) {
				return Result.error("数据库不存在该表记录");
			}

			LambdaQueryWrapper<OnlCgformField> var9 = new LambdaQueryWrapper();
			var9.eq(OnlCgformField::getCgformHeadId, var1);
			List var10 = this.onlCgformFieldService.list(var9);
			MultipartHttpServletRequest var11 = (MultipartHttpServletRequest) var2;
			Map var12 = var11.getFileMap();
			DataSource var13 = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
			String var14 = d.a(var13);
			Iterator var15 = var12.entrySet().iterator();

			while (true) {
				while (var15.hasNext()) {
					Entry var16 = (Entry) var15.next();
					MultipartFile var17 = (MultipartFile) var16.getValue();
					ImportParams var18 = new ImportParams();
					var18.setDataHanlder(new com.huizhixin.smart.modules.online.cgform.b.a(var10));
					List var19 = ExcelImportUtil.importExcel(var17.getInputStream(), Map.class, var18);
					if (var19 == null) {
						var7 = "识别模版数据错误";
						a.error(var7);
					} else {
						Object var20 = "";
						ArrayList var21 = new ArrayList();

						Map var23;
						for (Iterator var22 = var19.iterator(); var22.hasNext(); var23.put("$mainTable$id", var20)) {
							var23 = (Map) var22.next();
							boolean var24 = false;
							Set var25 = var23.keySet();
							HashMap var26 = new HashMap();
							Iterator var27 = var25.iterator();

							while (var27.hasNext()) {
								String var28 = (String) var27.next();
								if (var28.indexOf("$subTable$") == -1) {
									if (var28.indexOf("$mainTable$") != -1
											&& oConvertUtils.isNotEmpty(var23.get(var28).toString())) {
										var24 = true;
										var20 = this.a(var8, var13, var14);
									}

									var26.put(var28.replace("$mainTable$", ""), var23.get(var28));
								}
							}

							if (var24) {
								this.a((Map) var26, (List) var10);
								var26.put("id", var20);
								var21.add(var26);
								var20 = var26.get("id");
							}
						}

						this.onlCgformSqlService.saveBatchOnlineTable(var8.getTableName(), var10, var21);
						if (var8.getTableType() == 2 && oConvertUtils.isNotEmpty(var8.getSubTableStr())) {
							String[] var42 = var8.getSubTableStr().split(",");
							int var43 = var42.length;

							for (int var44 = 0; var44 < var43; ++var44) {
								String var45 = var42[var44];
								OnlCgformHead var46 = (OnlCgformHead) this.onlCgformHeadService.getOne(
										(Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var45));
								if (var46 != null) {
									LambdaQueryWrapper<OnlCgformField> var47 = new LambdaQueryWrapper();
									var47.eq(OnlCgformField::getCgformHeadId, var46.getId());
									List var48 = this.onlCgformFieldService.list(var47);
									String var29 = var46.getTableTxt();
									Iterator var30 = var19.iterator();

									while (var30.hasNext()) {
										Map var31 = (Map) var30.next();
										boolean var32 = false;
										HashMap var33 = new HashMap();
										Iterator var34 = var48.iterator();

										while (var34.hasNext()) {
											OnlCgformField var35 = (OnlCgformField) var34.next();
											String var36 = var35.getMainTable();
											String var37 = var35.getMainField();
											boolean var38 = var8.getTableName().equals(var36)
													&& oConvertUtils.isNotEmpty(var37);
											String var39 = var29 + "_" + var35.getDbFieldTxt();
											if (var38) {
												var33.put(var35.getDbFieldName(), var31.get("$mainTable$" + var37));
											}

											Object var40 = var31.get("$subTable$" + var39);
											if (null != var40 && oConvertUtils.isNotEmpty(var40.toString())) {
												var32 = true;
												var33.put(var35.getDbFieldName(), var40);
											}
										}

										if (var32) {
											this.a((Map) var33, (List) var48);
											var33.put("id", this.a(var46, var13, var14));
											String var49 = JSON.toJSONString(var33);
											this.onlCgformFieldService.saveFormData(var48, var46.getTableName(),
													JSONObject.parseObject(var49));
										}
									}
								}
							}
						}

						var7 = "文件导入成功！";
					}
				}

				var6.setSuccess(true);
				var6.setMessage("导入成功!");
				break;
			}
		} catch (Exception var41) {
			var6.setSuccess(false);
			var6.setMessage(var41.getMessage());
			a.error(var41.getMessage(), var41);
		}

		a.info("=====online导入数据完成,耗时:" + (System.currentTimeMillis() - var4) + "毫秒=====");
		return var6;
	}

	@PostMapping({"/doButton"})
	public Result<?> a(@RequestBody JSONObject var1) {
		String var2 = var1.getString("formId");
		String var3 = var1.getString("dataId");
		String var4 = var1.getString("buttonCode");

		try {
			this.onlCgformHeadService.executeCustomerButton(var4, var2, var3);
		} catch (Exception var6) {
			a.error(var6.getMessage(), var6);
			return Result.error("执行失败!");
		}

		return Result.ok("执行成功!");
	}

	public Object a(OnlCgformHead var1, DataSource var2, String var3) throws SQLException, DBException {
		Object var4 = null;
		String var5 = var1.getIdType();
		String var6 = var1.getIdSequence();
		if (oConvertUtils.isNotEmpty(var5) && "UUID".equalsIgnoreCase(var5)) {
			var4 = UUIDGenerator.generate();
		} else {
			PostgreSQLSequenceMaxValueIncrementer var7;
			OracleSequenceMaxValueIncrementer var13;
			if (oConvertUtils.isNotEmpty(var5) && "NATIVE".equalsIgnoreCase(var5)) {
				if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
					var13 = new OracleSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");

					try {
						var4 = var13.nextLongValue();
					} catch (Exception var12) {
						a.error(var12.getMessage(), var12);
					}
				} else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
					var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, "HIBERNATE_SEQUENCE");

					try {
						var4 = var7.nextLongValue();
					} catch (Exception var11) {
						a.error(var11.getMessage(), var11);
					}
				} else {
					var4 = null;
				}
			} else if (oConvertUtils.isNotEmpty(var5) && "SEQUENCE".equalsIgnoreCase(var5)) {
				if (oConvertUtils.isNotEmpty(var3) && "oracle".equalsIgnoreCase(var3)) {
					var13 = new OracleSequenceMaxValueIncrementer(var2, var6);

					try {
						var4 = var13.nextLongValue();
					} catch (Exception var10) {
						a.error(var10.getMessage(), var10);
					}
				} else if (oConvertUtils.isNotEmpty(var3) && "postgres".equalsIgnoreCase(var3)) {
					var7 = new PostgreSQLSequenceMaxValueIncrementer(var2, var6);

					try {
						var4 = var7.nextLongValue();
					} catch (Exception var9) {
						a.error(var9.getMessage(), var9);
					}
				} else {
					var4 = null;
				}
			} else {
				var4 = UUIDGenerator.generate();
			}
		}

		return var4;
	}

	private void a(Map var1, List<OnlCgformField> var2) {
		Iterator var3 = var2.iterator();

		while (true) {
			OnlCgformField var4;
			String var5;
			String var6;
			String var7;
			do {
				do {
					if (!var3.hasNext()) {
						return;
					}

					var4 = (OnlCgformField) var3.next();
					var5 = var4.getDictTable();
					var6 = var4.getDictField();
					var7 = var4.getDictText();
				} while (oConvertUtils.isEmpty(var5) && oConvertUtils.isEmpty(var6));
			} while ("popup".equals(var4.getFieldShowType()));

			String var9 = String.valueOf(var1.get(var4.getDbFieldName()));
			List var8;
			if (oConvertUtils.isEmpty(var5)) {
				var8 = this.sysBaseAPI.queryDictItemsByCode(var6);
			} else {
				var8 = this.sysBaseAPI.queryTableDictItemsByCode(var5, var7, var6);
			}

			Iterator var10 = var8.iterator();

			while (var10.hasNext()) {
				DictModel var11 = (DictModel) var10.next();
				if (var9.equals(var11.getText())) {
					var1.put(var4.getDbFieldName(), var11.getValue());
				}
			}
		}
	}

	private List<ExcelExportEntity> a(List<OnlCgformField> var1, String var2) {
		ArrayList var3 = new ArrayList();

		for (int var4 = 0; var4 < var1.size(); ++var4) {
			if ((null == var2 || !var2.equals(((OnlCgformField) var1.get(var4)).getDbFieldName()))
					&& ((OnlCgformField) var1.get(var4)).getIsShowList() == 1) {
				ExcelExportEntity var5 = new ExcelExportEntity(((OnlCgformField) var1.get(var4)).getDbFieldTxt(),
						((OnlCgformField) var1.get(var4)).getDbFieldName());
				int var6 = ((OnlCgformField) var1.get(var4)).getDbLength() == 0
						? 12
						: (((OnlCgformField) var1.get(var4)).getDbLength() > 30
								? 30
								: ((OnlCgformField) var1.get(var4)).getDbLength());
				if (((OnlCgformField) var1.get(var4)).getFieldShowType().equals("date")) {
					var5.setFormat("yyyy-MM-dd");
				} else if (((OnlCgformField) var1.get(var4)).getFieldShowType().equals("datetime")) {
					var5.setFormat("yyyy-MM-dd HH:mm:ss");
				}

				var5.setWidth((double) var6);
				String var7 = ((OnlCgformField) var1.get(var4)).getDictField();
				if (oConvertUtils.isNotEmpty(var7)) {
					ArrayList var8 = new ArrayList();
					List var9 = this.sysBaseAPI.queryDictItemsByCode(var7);
					Iterator var10 = var9.iterator();

					while (var10.hasNext()) {
						DictModel var11 = (DictModel) var10.next();
						var8.add(var11.getText() + "_" + var11.getValue());
					}

					var5.setReplace((String[]) var8.toArray(new String[var8.size()]));
				}

				var3.add(var5);
			}
		}

		return var3;
	}

	private void a(String var1, Map<String, Object> var2, List<Map<String, Object>> var3,
			List<ExcelExportEntity> var4) {
		OnlCgformHead var5 = (OnlCgformHead) this.onlCgformHeadService
				.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var1));
		LambdaQueryWrapper<OnlCgformField> var6 = new LambdaQueryWrapper();
		var6.eq(OnlCgformField::getCgformHeadId, var5.getId());
		var6.orderByAsc(OnlCgformField::getOrderNum);
		List var7 = this.onlCgformFieldService.list(var6);
		String var8 = "";
		String var9 = "";
		Iterator var10 = var7.iterator();

		while (var10.hasNext()) {
			OnlCgformField var11 = (OnlCgformField) var10.next();
			if (oConvertUtils.isNotEmpty(var11.getMainField())) {
				var8 = var11.getMainField();
				var9 = var11.getDbFieldName();
				break;
			}
		}

		ExcelExportEntity var14 = new ExcelExportEntity(var5.getTableTxt(), var1);
		var14.setList(this.a(var7, "id"));
		var4.add(var14);

		for (int var15 = 0; var15 < var3.size(); ++var15) {
			var2.put(var9, ((Map) var3.get(var15)).get(var8));
			String var12 = b.a(var5.getTableName(), var7, var2);
			a.info("-----------动态列表查询子表sql》》" + var12);
			List var13 = this.onlCgformHeadService.queryListData(var12);
			((Map) var3.get(var15)).put(var1, var13);
		}

	}

	@GetMapping({"/checkOnlyTable"})
	public Result<?> g(@RequestParam("tbname") String var1, @RequestParam("id") String var2) {
		if (oConvertUtils.isEmpty(var2)) {
			if (d.a(var1)) {
				return Result.ok(-1);
			}
		} else {
			OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var2);
			if (!var1.equals(var3.getTableName()) && d.a(var1)) {
				return Result.ok(-1);
			}
		}

		return Result.ok(1);
	}

	@PostMapping({"/codeGenerate"})
	public Result<?> b(@RequestBody JSONObject var1) {
		OnlGenerateModel var2 = (OnlGenerateModel) JSONObject.parseObject(var1.toJSONString(), OnlGenerateModel.class);

		try {
			if ("1".equals(var2.getJformType())) {
				this.onlCgformHeadService.generateCode(var2);
			} else {
				this.onlCgformHeadService.generateOneToMany(var2);
			}

			return Result.ok(1);
		} catch (Exception var4) {
			var4.printStackTrace();
			return Result.error(var4.getMessage());
		}
	}

	@GetMapping({"/getTreeData/{code}"})
	@PermissionData
	public Result<Map<String, Object>> d(@PathVariable("code") String var1, HttpServletRequest var2) {
		Result var3 = new Result();
		OnlCgformHead var4 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var4 == null) {
			var3.error500("实体不存在");
			return var3;
		} else {
			try {
				String var5 = var4.getTableName();
				String var6 = var4.getTreeIdField();
				String var7 = var4.getTreeParentIdField();
				ArrayList var8 = Lists.newArrayList(new String[]{var6, var7});
				Map var9 = b.a(var2);
				if (var9.get(var7) != null && !"0".equals(var9.get(var7))) {
					var9.put("pageSize", -521);
					var9.put(var7, var9.get("pid"));
				} else {
					var9.put(var7, "0");
				}

				Map var10 = this.onlCgformFieldService.queryAutolistPage(var5, var1, var9, var8);
				var3.setResult(var10);
			} catch (Exception var11) {
				a.error(var11.getMessage(), var11);
				var3.error500("数据库查询失败" + var11.getMessage());
			}

			return var3;
		}
	}

	@PostMapping({"/crazyForm/{name}"})
	public Result<?> c(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
		Result var3 = new Result();

		try {
			String var4 = UUIDGenerator.generate();
			var2.put("id", var4);
			this.onlCgformHeadService.addCrazyFormData(var1, var2);
			var3.setResult(var4);
			var3.setMessage("保存成功");
			return var3;
		} catch (Exception var5) {
			a.error("OnlCgformApiController.formAddForDesigner()发生异常：" + var5.getMessage());
			return Result.error("保存失败");
		}
	}

	@PutMapping({"/crazyForm/{name}"})
	public Result<?> d(@PathVariable("name") String var1, @RequestBody JSONObject var2) {
		try {
			this.onlCgformHeadService.editCrazyFormData(var1, var2);
		} catch (Exception var4) {
			a.error("OnlCgformApiController.formEditForDesigner()发生异常：" + var4.getMessage());
			return Result.error("保存失败");
		}

		return Result.ok("保存成功!");
	}
}