package com.huizhixin.smart.modules.online.cgform.service.a;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;

import com.huizhixin.smart.common.constant.CommonConstant;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformFieldMapper;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformFieldService;
import com.huizhixin.smart.modules.system.entity.SysAnnouncement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("onlCgformFieldServiceImpl")
public class b extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {
	private static final Logger a = LoggerFactory.getLogger(b.class);
	@Autowired
	private OnlCgformFieldMapper onlCgformFieldMapper;
	private static final String b = "0";

	public Map<String, Object> queryAutolistPage(String tbname, String headId, Map<String, Object> params,
			List<String> needList) {
		HashMap var5 = new HashMap();
		LambdaQueryWrapper<OnlCgformField> var6 = new LambdaQueryWrapper<OnlCgformField>();
		var6.eq(OnlCgformField::getCgformHeadId, headId);

		List var7 = this.list(var6);
		List var8 = this.queryAvailableFields(tbname, true, var7, needList);
		StringBuffer var9 = new StringBuffer();
		com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, var8, var9);
		String var10 = com.huizhixin.smart.modules.online.cgform.b.b.a(var7, params);
		var9.append(" where 1=1  " + var10);
		String var11 = params.get("order").toString();
		String var12 = params.get("column").toString();
		if (this.a(var12, var7)) {
			var9.append(" ORDER BY " + oConvertUtils.camelToUnderline(var12));
			if ("asc".equals(var11)) {
				var9.append(" asc");
			} else {
				var9.append(" desc");
			}
		}

		Integer var13 = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
		if (var13 == -521) {
			List var14 = this.onlCgformFieldMapper.queryListBySql(var9.toString());
			if (var14 != null && var14.size() != 0) {
				var5.put("total", var14.size());
				var5.put("records", com.huizhixin.smart.modules.online.cgform.b.b.d(var14));
			} else {
				var5.put("total", 0);
			}
		} else {
			Integer var17 = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
			Page var15 = new Page((long) var17, (long) var13);
			a.info("---Online查询sql:>>" + var9.toString());
			IPage var16 = this.onlCgformFieldMapper.selectPageBySql(var15, var9.toString());
			var5.put("total", var16.getTotal());
			var5.put("records", com.huizhixin.smart.modules.online.cgform.b.b.d(var16.getRecords()));
		}

		return var5;
	}

	public void saveFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
		LambdaQueryWrapper<OnlCgformField> var5 = new LambdaQueryWrapper<OnlCgformField>();
		var5.eq(OnlCgformField::getCgformHeadId, code);
		List var6 = this.list(var5);
		if (isCrazy) {
			((OnlCgformFieldMapper) this.baseMapper)
					.executeInsertSQL(com.huizhixin.smart.modules.online.cgform.b.b.c(tbname, var6, json));
		} else {
			((OnlCgformFieldMapper) this.baseMapper)
					.executeInsertSQL(com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, var6, json));
		}

	}

	public void saveTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
		LambdaQueryWrapper<OnlCgformField> var6 = new LambdaQueryWrapper<OnlCgformField>();
		var6.eq(OnlCgformField::getCgformHeadId, code);
		List var7 = this.list(var6);
		Iterator var8 = var7.iterator();

		while (true) {
			while (var8.hasNext()) {
				OnlCgformField var9 = (OnlCgformField) var8.next();
				if (hasChildField.equals(var9.getDbFieldName()) && var9.getIsShowForm() != 1) {
					var9.setIsShowForm(1);
					json.put(hasChildField, "0");
				} else if (pidField.equals(var9.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
					var9.setIsShowForm(1);
					json.put(pidField, "0");
				}
			}

			Map var10 = com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, var7, json);
			((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(var10);
			if (!"0".equals(json.getString(pidField))) {
				((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + tbname + " set " + hasChildField
						+ " = '1' where id = '" + json.getString(pidField) + "'");
			}

			return;
		}
	}

	public void saveFormData(List<OnlCgformField> fieldList, String tbname, JSONObject json) {
		Map var4 = com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, fieldList, json);
		((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(var4);
	}

	public void editFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
		LambdaQueryWrapper<OnlCgformField> var5 = new LambdaQueryWrapper<OnlCgformField>();
		var5.eq(OnlCgformField::getCgformHeadId, code);
		List var6 = this.list(var5);
		if (isCrazy) {
			((OnlCgformFieldMapper) this.baseMapper)
					.executeUpdatetSQL(com.huizhixin.smart.modules.online.cgform.b.b.d(tbname, var6, json));
		} else {
			((OnlCgformFieldMapper) this.baseMapper)
					.executeUpdatetSQL(com.huizhixin.smart.modules.online.cgform.b.b.b(tbname, var6, json));
		}

	}

	public void editTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
		String var6 = "select * from " + tbname + " where id = '" + json.getString("id") + "'";
		Map var7 = ((OnlCgformFieldMapper) this.baseMapper).queryFormData(var6);
		String var8 = var7.get(pidField).toString();
		LambdaQueryWrapper<OnlCgformField> var9 = new LambdaQueryWrapper<OnlCgformField>();
		var9.eq(OnlCgformField::getCgformHeadId, code);
		List var10 = this.list(var9);
		Iterator var11 = var10.iterator();

		while (var11.hasNext()) {
			OnlCgformField var12 = (OnlCgformField) var11.next();
			if (pidField.equals(var12.getDbFieldName()) && oConvertUtils.isEmpty(json.get(pidField))) {
				var12.setIsShowForm(1);
				json.put(pidField, "0");
			}
		}

		Map var14 = com.huizhixin.smart.modules.online.cgform.b.b.b(tbname, var10, json);
		((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(var14);
		if (!var8.equals(json.getString(pidField))) {
			if (!"0".equals(var8)) {
				String var15 = "select count(*) from " + tbname + " where " + pidField + " = '" + var8 + "'";
				Integer var13 = ((OnlCgformFieldMapper) this.baseMapper).queryCountBySql(var15);
				if (var13 == null || var13 == 0) {
					((OnlCgformFieldMapper) this.baseMapper).editFormData(
							"update " + tbname + " set " + hasChildField + " = '0' where id = '" + var8 + "'");
				}
			}

			if (!"0".equals(json.getString(pidField))) {
				((OnlCgformFieldMapper) this.baseMapper).editFormData("update " + tbname + " set " + hasChildField
						+ " = '1' where id = '" + json.getString(pidField) + "'");
			}
		}

	}

	public Map<String, Object> queryFormData(String code, String tbname, String id) {
		LambdaQueryWrapper<OnlCgformField> var4 = new LambdaQueryWrapper<OnlCgformField>();
		var4.eq(OnlCgformField::getCgformHeadId, code);
		var4.eq(OnlCgformField::getIsShowForm, 1);
		List var5 = this.list(var4);
		String var6 = com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, var5, id);
		return this.onlCgformFieldMapper.queryFormData(var6);
	}

	public void deleteAutoList(String tbname, String id) {
		this.deleteAutoList(tbname, "id", id);
	}

	public void deleteAutoList(String tbname, String linkField, String linkValue) {
		String[] var4 = linkValue.split(",");
		StringBuffer var5 = new StringBuffer();
		String[] var6 = var4;
		int var7 = var4.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			String var9 = var6[var8];
			if (var9 != null && !"".equals(var9)) {
				var5.append("'" + var9 + "',");
			}
		}

		String var10 = var5.toString();
		String var11 = "DELETE FROM " + tbname + " where " + linkField + " in(" + var10.substring(0, var10.length() - 1)
				+ ")";
		a.info("--删除sql-->" + var11);
		this.onlCgformFieldMapper.deleteAutoList(var11);
	}

	public List<Map<String, String>> getAutoListQueryInfo(String code) {
		LambdaQueryWrapper<OnlCgformField> var2 = new LambdaQueryWrapper<OnlCgformField>();
		var2.eq(OnlCgformField::getCgformHeadId, code);
		var2.eq(OnlCgformField::getIsQuery, 1);
		List var3 = this.list(var2);
		ArrayList var4 = new ArrayList();
		int var5 = 0;

		HashMap var8;
		for (Iterator var6 = var3.iterator(); var6.hasNext(); var4.add(var8)) {
			OnlCgformField var7 = (OnlCgformField) var6.next();
			var8 = new HashMap();
			var8.put("label", var7.getDbFieldTxt());
			var8.put("view", var7.getFieldShowType());
			var8.put("mode", var7.getQueryMode());
			var8.put("field", var7.getDbFieldName());
			++var5;
			if (var5 > 2) {
				var8.put("hidden", "1");
			}
		}

		return var4;
	}

	public List<OnlCgformField> queryFormFields(String code, boolean isform) {
		LambdaQueryWrapper<OnlCgformField> var3 = new LambdaQueryWrapper<OnlCgformField>();
		var3.eq(OnlCgformField::getCgformHeadId, code);
		if (isform) {
			var3.eq(OnlCgformField::getIsShowForm, 1);
		}

		return this.list(var3);
	}

	public Map<String, Object> queryFormData(List<OnlCgformField> fieldList, String tbname, String id) {
		String var4 = com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, fieldList, id);
		return this.onlCgformFieldMapper.queryFormData(var4);
	}

	public List<Map<String, Object>> querySubFormData(List<OnlCgformField> fieldList, String tbname, String linkField,
			String value) {
		String var5 = com.huizhixin.smart.modules.online.cgform.b.b.a(tbname, fieldList, linkField, value);
		return this.onlCgformFieldMapper.queryListData(var5);
	}

	public IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, String sql) {
		return ((OnlCgformFieldMapper) this.baseMapper).selectPageBySql(page, sql);
	}

	public List<String> selectOnlineHideColumns(String tbname) {
		String var2 = "online:" + tbname + "%";
		LoginUser var3 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String var4 = var3.getId();
		List var5 = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(var4, var2);
		ArrayList var6 = new ArrayList();
		if (var5 != null && var5.size() != 0 && var5.get(0) != null) {
			Iterator var7 = var5.iterator();

			while (var7.hasNext()) {
				String var8 = (String) var7.next();
				if (!oConvertUtils.isEmpty(var8)) {
					String var9 = var8.substring(var8.lastIndexOf(":") + 1);
					if (!oConvertUtils.isEmpty(var9)) {
						var6.add(var9);
					}
				}
			}

			return var6;
		} else {
			return var6;
		}
	}

	public List<OnlCgformField> queryAvailableFields(String cgFormId, String tbname, boolean isList) {
		LambdaQueryWrapper<OnlCgformField> var4 = new LambdaQueryWrapper<OnlCgformField>();
		var4.eq(OnlCgformField::getCgformHeadId, cgFormId);
		if (isList) {
			var4.eq(OnlCgformField::getIsShowList, 1);
		} else {
			var4.eq(OnlCgformField::getIsShowForm, 1);
		}

		var4.orderByAsc(OnlCgformField::getOrderNum);
		List var5 = this.list(var4);
		String var6 = "online:" + tbname + "%";
		LoginUser var7 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String var8 = var7.getId();
		List var9 = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(var8, var6);
		if (var9 != null && var9.size() != 0 && var9.get(0) != null) {
			ArrayList var10 = new ArrayList();

			for (int var11 = 0; var11 < var5.size(); ++var11) {
				boolean var12 = true;
				OnlCgformField var13 = (OnlCgformField) var5.get(var11);
				String var14 = var13.getDbFieldName();

				for (int var15 = 0; var15 < var9.size(); ++var15) {
					String var16 = (String) var9.get(var15);
					if (!oConvertUtils.isEmpty(var16)) {
						String var17 = var16.substring(var16.lastIndexOf(":") + 1);
						if (!oConvertUtils.isEmpty(var17) && var17.equals(var14)) {
							var12 = false;
						}
					}
				}

				if (var12) {
					var10.add(var13);
				}
			}

			return var10;
		} else {
			return var5;
		}
	}

	public List<String> queryDisabledFields(String tbname) {
		String var2 = "online:" + tbname + "%";
		LoginUser var3 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String var4 = var3.getId();
		ArrayList var5 = new ArrayList();
		List var6 = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineDisabledColumns(var4, var2);
		if (var6 != null && var6.size() != 0 && var6.get(0) != null) {
			Iterator var7 = var6.iterator();

			while (var7.hasNext()) {
				String var8 = (String) var7.next();
				if (!oConvertUtils.isEmpty(var8)) {
					String var9 = var8.substring(var8.lastIndexOf(":") + 1);
					if (!oConvertUtils.isEmpty(var9)) {
						var5.add(var9);
					}
				}
			}

			return var5;
		} else {
			return var5;
		}
	}

	public List<OnlCgformField> queryAvailableFields(String tbname, boolean isList, List<OnlCgformField> List,
			List<String> needList) {
		ArrayList var5 = new ArrayList();
		String var6 = "online:" + tbname + "%";
		LoginUser var7 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String var8 = var7.getId();
		List var9 = ((OnlCgformFieldMapper) this.baseMapper).selectOnlineHideColumns(var8, var6);
		boolean var10 = true;
		if (var9 == null || var9.size() == 0 || var9.get(0) == null) {
			var10 = false;
		}

		Iterator var11 = List.iterator();

		while (true) {
			while (var11.hasNext()) {
				OnlCgformField var12 = (OnlCgformField) var11.next();
				String var13 = var12.getDbFieldName();
				if (needList != null && needList.contains(var13)) {
					var12.setIsQuery(1);
					var5.add(var12);
				} else {
					if (isList) {
						if (var12.getIsShowList() != 1) {
							continue;
						}
					} else if (var12.getIsShowForm() != 1) {
						continue;
					}

					if (var10) {
						boolean var14 = true;

						for (int var15 = 0; var15 < var9.size(); ++var15) {
							String var16 = (String) var9.get(var15);
							if (!oConvertUtils.isEmpty(var16)) {
								String var17 = var16.substring(var16.lastIndexOf(":") + 1);
								if (!oConvertUtils.isEmpty(var17)) {
									if (var17.equals(var13)) {
										var14 = false;
									}

									if (var14) {
										var5.add(var12);
									}
								}
							}
						}
					} else {
						var5.add(var12);
					}
				}
			}

			return var5;
		}
	}

	public boolean a(String var1, List<OnlCgformField> var2) {
		boolean var3 = false;
		Iterator var4 = var2.iterator();

		while (var4.hasNext()) {
			OnlCgformField var5 = (OnlCgformField) var4.next();
			if (oConvertUtils.camelToUnderline(var1).equals(var5.getDbFieldName())) {
				var3 = true;
				break;
			}
		}

		return var3;
	}

	public void executeInsertSQL(Map<String, Object> params) {
		((OnlCgformFieldMapper) this.baseMapper).executeInsertSQL(params);
	}

	public void executeUpdatetSQL(Map<String, Object> params) {
		((OnlCgformFieldMapper) this.baseMapper).executeUpdatetSQL(params);
	}
}