package com.huizhixin.smart.modules.online.cgform.a;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.system.util.JwtUtil;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.modules.online.cgform.b.b;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJava;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJs;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceSql;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformFieldService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformHeadService;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import org.jeecgframework.codegenerate.a.a;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("onlCgformHeadController")
@RequestMapping({"/online/cgform/head"})
public class OnlCgformHeadController {
	private static final Logger a = LoggerFactory.getLogger(OnlCgformHeadController.class);
	@Autowired
	private IOnlCgformHeadService onlCgformHeadService;
	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;
	private static String b;

	@GetMapping({"/list"})
	public Result<IPage<OnlCgformHead>> a(OnlCgformHead var1,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer var2,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer var3, HttpServletRequest var4) {
		Result var5 = new Result();
		QueryWrapper var6 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
		Page var7 = new Page((long) var2, (long) var3);
		IPage var8 = this.onlCgformHeadService.page(var7, var6);
		var5.setSuccess(true);
		var5.setResult(var8);
		return var5;
	}

	@PostMapping({"/add"})
	public Result<OnlCgformHead> a(@RequestBody OnlCgformHead var1) {
		Result var2 = new Result();

		try {
			this.onlCgformHeadService.save(var1);
			var2.success("添加成功！");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			var2.error500("操作失败");
		}

		return var2;
	}

	@PutMapping({"/edit"})
	public Result<OnlCgformHead> b(@RequestBody OnlCgformHead var1) {
		Result var2 = new Result();
		OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1.getId());
		if (var3 == null) {
			var2.error500("未找到对应实体");
		} else {
			boolean var4 = this.onlCgformHeadService.updateById(var1);
			if (var4) {
				var2.success("修改成功!");
			}
		}

		return var2;
	}

	@DeleteMapping({"/delete"})
	public Result<?> a(@RequestParam(name = "id", required = true) String var1) {
		try {
			this.onlCgformHeadService.deleteRecordAndTable(var1);
		} catch (DBException var3) {
			return Result.error("删除失败" + var3.getMessage());
		} catch (SQLException var4) {
			return Result.error("删除失败" + var4.getMessage());
		}

		return Result.ok("删除成功!");
	}

	@DeleteMapping({"/removeRecord"})
	public Result<OnlCgformHead> b(@RequestParam(name = "id", required = true) String var1) {
		Result var2 = new Result();
		OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var3 == null) {
			var2.error500("未找到对应实体");
		} else {
			boolean var4 = this.onlCgformHeadService.removeById(var1);
			if (var4) {
				var2.success("删除成功!");
			}
		}

		return var2;
	}

	@DeleteMapping({"/deleteBatch"})
	public Result<OnlCgformHead> c(@RequestParam(name = "ids", required = true) String var1) {
		Result var2 = new Result();
		if (var1 != null && !"".equals(var1.trim())) {
			this.onlCgformHeadService.removeByIds(Arrays.asList(var1.split(",")));
			var2.success("删除成功!");
		} else {
			var2.error500("参数不识别！");
		}

		return var2;
	}

	@GetMapping({"/queryById"})
	public Result<OnlCgformHead> d(@RequestParam(name = "id", required = true) String var1) {
		Result var2 = new Result();
		OnlCgformHead var3 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var3 == null) {
			var2.error500("未找到对应实体");
		} else {
			var2.setResult(var3);
			var2.setSuccess(true);
		}

		return var2;
	}

	@GetMapping({"/queryByTableNames"})
	public Result<?> e(@RequestParam(name = "tableNames", required = true) String var1) {
		LambdaQueryWrapper<OnlCgformHead> var2 = new LambdaQueryWrapper();
		String[] var3 = var1.split(",");
		var2.in(OnlCgformHead::getTableName, Arrays.asList(var3));
		List var4 = this.onlCgformHeadService.list(var2);
		return var4 == null ? Result.error("未找到对应实体") : Result.ok(var4);
	}

	@PostMapping({"/enhanceJs/{code}"})
	public Result<?> a(@PathVariable("code") String var1, @RequestBody OnlCgformEnhanceJs var2) {
		try {
			var2.setCgformHeadId(var1);
			this.onlCgformHeadService.saveEnhance(var2);
			return Result.ok("保存成功!");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@GetMapping({"/enhanceJs/{code}"})
	public Result<?> a(@PathVariable("code") String var1, HttpServletRequest var2) {
		try {
			String var3 = var2.getParameter("type");
			OnlCgformEnhanceJs var4 = this.onlCgformHeadService.queryEnhance(var1, var3);
			return var4 == null ? Result.error("查询为空") : Result.ok(var4);
		} catch (Exception var5) {
			a.error(var5.getMessage(), var5);
			return Result.error("查询失败!");
		}
	}

	@PutMapping({"/enhanceJs/{code}"})
	public Result<?> b(@PathVariable("code") String var1, @RequestBody OnlCgformEnhanceJs var2) {
		try {
			var2.setCgformHeadId(var1);
			this.onlCgformHeadService.editEnhance(var2);
			return Result.ok("保存成功!");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@GetMapping({"/enhanceButton/{formId}"})
	public Result<?> b(@PathVariable("formId") String var1, HttpServletRequest var2) {
		try {
			List var3 = this.onlCgformHeadService.queryButtonList(var1);
			return var3 != null && var3.size() != 0 ? Result.ok(var3) : Result.error("查询为空");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("查询失败!");
		}
	}

	@RequiresRoles({"admin"})
	@PostMapping({"/enhanceSql/{formId}"})
	public Result<?> a(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceSql var2) {
		try {
			var2.setCgformHeadId(var1);
			this.onlCgformHeadService.saveEnhance(var2);
			return Result.ok("保存成功!");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@GetMapping({"/enhanceSql/{formId}"})
	public Result<?> c(@PathVariable("formId") String var1, HttpServletRequest var2) {
		try {
			String var3 = var2.getParameter("buttonCode");
			OnlCgformEnhanceSql var4 = this.onlCgformHeadService.queryEnhanceSql(var1, var3);
			return var4 == null ? Result.error("查询为空") : Result.ok(var4);
		} catch (Exception var5) {
			a.error(var5.getMessage(), var5);
			return Result.error("查询失败!");
		}
	}

	@RequiresRoles({"admin"})
	@PutMapping({"/enhanceSql/{formId}"})
	public Result<?> b(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceSql var2) {
		try {
			var2.setCgformHeadId(var1);
			this.onlCgformHeadService.editEnhance(var2);
			return Result.ok("保存成功!");
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@PostMapping({"/enhanceJava/{formId}"})
	public Result<?> a(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceJava var2) {
		try {
			if (!com.huizhixin.smart.modules.online.cgform.b.b.a(var2)) {
				return Result.error("类实例化失败，请检查!");
			} else {
				var2.setCgformHeadId(var1);
				if (this.onlCgformHeadService.checkOnlyEnhance(var2)) {
					this.onlCgformHeadService.saveEnhance(var2);
					return Result.ok("保存成功!");
				} else {
					return Result.error("保存失败,数据不唯一!");
				}
			}
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@GetMapping({"/enhanceJava/{formId}"})
	public Result<?> b(@PathVariable("formId") String var1, OnlCgformEnhanceJava var2) {
		try {
			var2.setCgformHeadId(var1);
			OnlCgformEnhanceJava var3 = this.onlCgformHeadService.queryEnhanceJava(var2);
			return var3 == null ? Result.error("查询为空") : Result.ok(var3);
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("查询失败!");
		}
	}

	@PutMapping({"/enhanceJava/{formId}"})
	public Result<?> c(@PathVariable("formId") String var1, @RequestBody OnlCgformEnhanceJava var2) {
		try {
			if (!com.huizhixin.smart.modules.online.cgform.b.b.a(var2)) {
				return Result.error("类实例化失败，请检查!");
			} else {
				var2.setCgformHeadId(var1);
				if (this.onlCgformHeadService.checkOnlyEnhance(var2)) {
					this.onlCgformHeadService.editEnhance(var2);
					return Result.ok("保存成功!");
				} else {
					return Result.error("保存失败,数据不唯一!");
				}
			}
		} catch (Exception var4) {
			a.error(var4.getMessage(), var4);
			return Result.error("保存失败!");
		}
	}

	@GetMapping({"/queryTables"})
	public Result<?> a(HttpServletRequest var1) {
		String var2 = JwtUtil.getUserNameByToken(var1);
		if (!"admin".equals(var2)) {
			return Result.error("noadminauth");
		} else {
			new ArrayList();

			List var3;
			try {
				var3 = DbReadTableUtil.a();
			} catch (SQLException var9) {
				a.error(var9.getMessage(), var9);
				return Result.error("同步失败，未获取数据库表信息");
			}

			com.huizhixin.smart.modules.online.cgform.b.b.a(var3);
			List var4 = this.onlCgformHeadService.queryOnlinetables();
			var3.removeAll(var4);
			ArrayList var5 = new ArrayList();
			Iterator var6 = var3.iterator();

			while (var6.hasNext()) {
				String var7 = (String) var6.next();
				HashMap var8 = new HashMap();
				var8.put("id", var7);
				var5.add(var8);
			}

			return Result.ok(var5);
		}
	}

	@PostMapping({"/transTables/{tbnames}"})
	@RequiresRoles({"admin"})
	public Result<?> d(@PathVariable("tbnames") String var1, HttpServletRequest var2) {
		String var3 = JwtUtil.getUserNameByToken(var2);
		if (!"admin".equals(var3)) {
			return Result.error("noadminauth");
		} else if (oConvertUtils.isEmpty(var1)) {
			return Result.error("未识别的表名信息");
		} else if (b != null && b.equals(var1)) {
			return Result.error("不允许重复生成!");
		} else {
			b = var1;
			String[] var4 = var1.split(",");

			for (int var5 = 0; var5 < var4.length; ++var5) {
				if (oConvertUtils.isNotEmpty(var4[var5])) {
					int var6 = this.onlCgformHeadService
							.count((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var4[var5]));
					if (var6 <= 0) {
						a.info("[IP] [online数据库导入表]   --表名：" + var4[var5]);
						this.onlCgformHeadService.saveDbTable2Online(var4[var5]);
					}
				}
			}

			b = null;
			return Result.ok("同步完成!");
		}
	}

	@GetMapping({"/rootFile"})
	public Result<?> a() {
		JSONArray var1 = new JSONArray();
		File[] var2 = File.listRoots();
		File[] var3 = var2;
		int var4 = var2.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			File var6 = var3[var5];
			JSONObject var7 = new JSONObject();
			if (var6.isDirectory()) {
				System.out.println(var6.getPath());
				var7.put("key", var6.getAbsolutePath());
				var7.put("title", var6.getPath());
				var7.put("opened", false);
				JSONObject var8 = new JSONObject();
				var8.put("icon", "custom");
				var7.put("scopedSlots", var8);
				var7.put("isLeaf", var6.listFiles() == null || var6.listFiles().length == 0);
			}

			var1.add(var7);
		}

		return Result.ok(var1);
	}

	@GetMapping({"/fileTree"})
	public Result<?> f(@RequestParam(name = "parentPath", required = true) String var1) {
		JSONArray var2 = new JSONArray();
		File var3 = new File(var1);
		File[] var4 = var3.listFiles();
		File[] var5 = var4;
		int var6 = var4.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			File var8 = var5[var7];
			if (var8.isDirectory() && oConvertUtils.isNotEmpty(var8.getPath())) {
				JSONObject var9 = new JSONObject();
				System.out.println(var8.getPath());
				var9.put("key", var8.getAbsolutePath());
				var9.put("title", var8.getPath().substring(var8.getPath().lastIndexOf(File.separator) + 1));
				var9.put("isLeaf", var8.listFiles() == null || var8.listFiles().length == 0);
				var9.put("opened", false);
				JSONObject var10 = new JSONObject();
				var10.put("icon", "custom");
				var9.put("scopedSlots", var10);
				var2.add(var9);
			}
		}

		return Result.ok(var2);
	}

	@GetMapping({"/tableInfo"})
	public Result<?> g(@RequestParam(name = "code", required = true) String var1) {
		OnlCgformHead var2 = (OnlCgformHead) this.onlCgformHeadService.getById(var1);
		if (var2 == null) {
			return Result.error("未找到对应实体");
		} else {
			HashMap var3 = new HashMap();
			var3.put("main", var2);
			if (var2.getTableType() == 2) {
				String var4 = var2.getSubTableStr();
				if (oConvertUtils.isNotEmpty(var4)) {
					ArrayList var5 = new ArrayList();
					String[] var6 = var4.split(",");
					String[] var7 = var6;
					int var8 = var6.length;

					for (int var9 = 0; var9 < var8; ++var9) {
						String var10 = var7[var9];
						LambdaQueryWrapper<OnlCgformHead> var11 = new LambdaQueryWrapper();
						var11.eq(OnlCgformHead::getTableName, var10);
						OnlCgformHead var12 = (OnlCgformHead) this.onlCgformHeadService.getOne(var11);
						var5.add(var12);
					}

					var3.put("sub", var5);
				}
			}

			var3.put("projectPath", org.jeecgframework.codegenerate.a.a.m());
			return Result.ok(var3);
		}
	}
}