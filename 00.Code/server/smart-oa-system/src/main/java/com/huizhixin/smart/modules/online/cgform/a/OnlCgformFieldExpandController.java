package com.huizhixin.smart.modules.online.cgform.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.system.vo.DictModel;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.entity.PageFormField;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformFieldService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformHeadService;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.system.service.ISysDictService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("onlCgformFieldExpandController")
@RequestMapping({ "/online/cgform/fieldExpand" })
public class OnlCgformFieldExpandController {
	private static final Logger a = LoggerFactory.getLogger(OnlCgformFieldExpandController.class);
	@Autowired
	private IOnlCgformHeadService onlCgformHeadService;
	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;
	@Autowired
	private ISysDictService sysDictService;

	@GetMapping({ "/listByHeadCode" })
	public Result<?> a(@RequestParam("formCode") String tableName, @RequestParam("businessKey") String businessKey) {
		LambdaQueryWrapper<OnlCgformHead> var2 = new LambdaQueryWrapper();
		QueryWrapper<OnlCgformField> var4 = new QueryWrapper();
		List<PageFormField> list = new ArrayList<PageFormField>();
		Map formData = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			var2.eq(OnlCgformHead::getTableName, tableName);
			OnlCgformHead formHead = (OnlCgformHead) this.onlCgformHeadService.getOne(var2);
			var4.eq("cgform_head_id", formHead.getId());
			var4.eq("is_show_form", "1");
			var4.orderByAsc("order_num");
			List<OnlCgformField> formFieldList = this.onlCgformFieldService.list(var4);

			for (OnlCgformField onlCgformField : formFieldList) {
				onlCgformField.getFieldShowType();
				PageFormField pageForm = new PageFormField();
				String dictField = onlCgformField.getDictField();
				String dictTable = onlCgformField.getDictTable();
				String dictText = onlCgformField.getDictText();
				// 查询字典数据
				if (StringUtils.isNotEmpty(dictField) && StringUtils.isEmpty(dictTable)) {
					List<DictModel> dictList = sysDictService.queryDictItemsByCode(onlCgformField.getDictField());
					pageForm.setOptinons(dictList);
				} else if (StringUtils.isNotEmpty(dictField) && StringUtils.isNotEmpty(dictTable)) {
					List<DictModel> dictList = sysDictService.queryTableDictItemsByCode(dictTable, dictText, dictField);
					pageForm.setOptinons(dictList);
				}

				pageForm.setLabel(onlCgformField.getDbFieldTxt());
				pageForm.setDataType(onlCgformField.getDbType());
				pageForm.setDefaultValue(onlCgformField.getDbDefaultVal());
				pageForm.setPlaceholder("请输入" + onlCgformField.getDbFieldTxt());
				pageForm.setRequired(false);
				// pageForm.getOptions().setPattern(onlCgformField.get);
				pageForm.setType(onlCgformField.getFieldShowType());
				pageForm.setKey(onlCgformField.getDbFieldName());
				list.add(pageForm);
			}
			// 数据处理部分
			formData = onlCgformHeadService.queryManyFormData(formHead.getId(), businessKey);
		} catch (DBException e) {
			e.printStackTrace();
		}
		resultMap.put("formList", list);
		resultMap.put("formData", formData);
		return Result.ok(resultMap);
	}

	@GetMapping({ "/listByHeadId" })
	public Result<?> b(@RequestParam("headId") String var1) {
		QueryWrapper var2 = new QueryWrapper();
		var2.eq("cgform_head_id", var1);
		var2.orderByAsc("order_num");
		List var3 = this.onlCgformFieldService.list(var2);
		return Result.ok(var3);
	}

	@GetMapping({ "/queryById" })
	public Result<OnlCgformField> e(@RequestParam(name = "id", required = true) String var1) {
		Result var2 = new Result();
		OnlCgformField var3 = (OnlCgformField) this.onlCgformFieldService.getById(var1);
		if (var3 == null) {
			var2.error500("未找到对应实体");
		} else {
			var2.setResult(var3);
			var2.setSuccess(true);
		}

		return var2;
	}
}