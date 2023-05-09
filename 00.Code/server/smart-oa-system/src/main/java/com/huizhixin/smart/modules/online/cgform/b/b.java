package com.huizhixin.smart.modules.online.cgform.b;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import com.huizhixin.smart.common.exception.JeecgBootException;
import com.huizhixin.smart.common.system.api.ISysBaseAPI;
import com.huizhixin.smart.common.system.query.QueryGenerator;
import com.huizhixin.smart.common.system.query.QueryRuleEnum;
import com.huizhixin.smart.common.system.vo.LoginUser;
import com.huizhixin.smart.common.util.DateUtils;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.common.util.UUIDGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.common.util.jsonschema.CommonProperty;
import com.huizhixin.smart.common.util.jsonschema.JsonSchemaDescrip;
import com.huizhixin.smart.common.util.jsonschema.JsonschemaUtil;
import com.huizhixin.smart.common.util.jsonschema.validate.DictProperty;
import com.huizhixin.smart.common.util.jsonschema.validate.NumberProperty;
import com.huizhixin.smart.common.util.jsonschema.validate.PopupProperty;
import com.huizhixin.smart.common.util.jsonschema.validate.StringProperty;
import com.huizhixin.smart.common.util.jsonschema.validate.TreeSelectProperty;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformButton;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJava;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJs;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformIndex;
import com.huizhixin.smart.modules.online.cgform.enums.CgformValidPatternEnum;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.online.config.util.d;
import com.huizhixin.smart.modules.system.entity.SysPermissionDataRule;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Comparator;
import java.util.Collections;

public class b {
	private static final Logger B = LoggerFactory.getLogger(b.class);
	public static final String a = "SELECT ";
	public static final String b = " FROM ";
	public static final String c = " AND ";
	public static final String d = " COUNT(*) ";
	public static final String e = " where 1=1  ";
	public static final String f = " ORDER BY ";
	public static final String g = "asc";
	public static final String h = "desc";
	public static final String i = "=";
	public static final String j = ">=";
	public static final String k = "<=";
	public static final String l = "CREATE_TIME";
	public static final String m = "CREATE_BY";
	public static final String n = "UPDATE_TIME";
	public static final String o = "UPDATE_BY";
	public static final String p = "SYS_ORG_CODE";
	public static final int q = 2;
	public static final String r = "'";
	public static final String s = ",";
	public static final String t = "single";
	public static final String u = "id";
	public static final String v = "1";
	public static final String w = "force";
	public static final String x = "normal";
	public static final String y = "popup";
	public static final String z = "sel_search";
	public static final String A = "sub-table-design_";
	private static final String C = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created";

	public static void a(String var0, List<OnlCgformField> var1, StringBuffer var2) {
		var2.append("SELECT ");
		int var3 = var1.size();
		boolean var4 = false;

		for (int var5 = 0; var5 < var3; ++var5) {
			OnlCgformField var6 = (OnlCgformField) var1.get(var5);
			if ("id".equals(var6.getDbFieldName())) {
				var4 = true;
			}

			if (var5 == var3 - 1) {
				var2.append(var6.getDbFieldName() + " ");
			} else {
				var2.append(var6.getDbFieldName() + ",");
			}
		}

		if (!var4) {
			var2.append(",id");
		}

		var2.append(" FROM " + var0);
	}

	public static String a(String var0) {
		return " to_date('" + var0 + "','yyyy-MM-dd HH24:mi:ss')";
	}

	public static boolean b(String var0) {
		if ("list".equals(var0)) {
			return true;
		} else if ("radio".equals(var0)) {
			return true;
		} else if ("checkbox".equals(var0)) {
			return true;
		} else {
			return "list_multi".equals(var0);
		}
	}

	public static String a(List<OnlCgformField> var0, Map<String, Object> var1) {
		StringBuffer var2 = new StringBuffer();
		String var3 = "";

		try {
			var3 = com.huizhixin.smart.modules.online.config.util.d.getDatabaseType();
		} catch (SQLException var12) {
			var12.printStackTrace();
		} catch (DBException var13) {
			var13.printStackTrace();
		}

		Map var4 = QueryGenerator.getRuleMap();
		Iterator var5 = var4.keySet().iterator();

		while (var5.hasNext()) {
			String var6 = (String) var5.next();
			if (oConvertUtils.isNotEmpty(var6) && var6.startsWith("SQL_RULES_COLUMN")) {
				var2.append(" AND ("
						+ QueryGenerator.getSqlRuleValue(((SysPermissionDataRule) var4.get(var6)).getRuleValue())
						+ ")");
			}
		}

		var5 = var0.iterator();

		while (var5.hasNext()) {
			OnlCgformField var14 = (OnlCgformField) var5.next();
			String var7 = var14.getDbFieldName();
			String var8 = var14.getDbType();
			if (var4.containsKey(var7)) {
				a((SysPermissionDataRule) var4.get(var7), var7, var8, var2);
			}

			if (var4.containsKey(oConvertUtils.camelNames(var7))) {
				a((SysPermissionDataRule) var4.get(var7), var7, var8, var2);
			}

			if (1 == var14.getIsQuery()) {
				Object var9;
				if ("single".equals(var14.getQueryMode())) {
					var9 = var1.get(var7);
					if (var9 != null) {
						if ("ORACLE".equals(var3)) {
							var2.append(" AND " + var7 + "=" + a(var9.toString()));
						} else {
							boolean var10 = !com.huizhixin.smart.modules.online.cgform.b.c.a(var8);
							String var11 = QueryGenerator.getSingleQueryConditionSql(var7, "", var9, var10);
							var2.append(" AND " + var11);
						}
					}
				} else {
					var9 = var1.get(var7 + "_begin");
					if (var9 != null) {
						var2.append(" AND " + var7 + ">=");
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var8)) {
							var2.append(var9.toString());
						} else if ("ORACLE".equals(var3)) {
							var2.append(a(var9.toString()));
						} else {
							var2.append("'" + var9.toString() + "'");
						}
					}

					Object var15 = var1.get(var7 + "_end");
					if (var15 != null) {
						var2.append(" AND " + var7 + "<=");
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var8)) {
							var2.append(var15.toString());
						} else if ("ORACLE".equals(var3)) {
							var2.append(a(var15.toString()));
						} else {
							var2.append("'" + var15.toString() + "'");
						}
					}
				}
			}
		}

		return var2.toString();
	}

	public static Map<String, Object> a(HttpServletRequest var0) {
		Map var1 = var0.getParameterMap();
		HashMap var2 = new HashMap();
		Iterator var3 = var1.entrySet().iterator();
		String var5 = "";
		String var6 = "";

		for (Object var7 = null; var3.hasNext(); var2.put(var5, var6)) {
			Entry var4 = (Entry) var3.next();
			var5 = (String) var4.getKey();
			var7 = var4.getValue();
			if (!"_t".equals(var5) && null != var7) {
				if (!(var7 instanceof String[])) {
					var6 = var7.toString();
				} else {
					String[] var8 = (String[]) ((String[]) var7);

					for (int var9 = 0; var9 < var8.length; ++var9) {
						var6 = var8[var9] + ",";
					}

					var6 = var6.substring(0, var6.length() - 1);
				}
			} else {
				var6 = "";
			}
		}

		return var2;
	}

	public static JSONObject a(List<OnlCgformField> var0, List<String> var1,
			com.huizhixin.smart.modules.online.cgform.model.b var2) {
		new JSONObject();
		ArrayList var4 = new ArrayList();
		ArrayList var5 = new ArrayList();
		ISysBaseAPI var6 = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
		Iterator var7 = var0.iterator();

		while (true) {
			OnlCgformField var8;
			String var9;
			do {
				if (!var7.hasNext()) {
					JSONObject var3;
					JsonSchemaDescrip var15;
					if (var4.size() > 0) {
						var15 = new JsonSchemaDescrip(var4);
						var3 = JsonschemaUtil.getJsonSchema(var15, var5);
					} else {
						var15 = new JsonSchemaDescrip();
						var3 = JsonschemaUtil.getJsonSchema(var15, var5);
					}

					return var3;
				}

				var8 = (OnlCgformField) var7.next();
				var9 = var8.getDbFieldName();
			} while ("id".equals(var9));

			String var10 = var8.getDbFieldTxt();
			if ("1".equals(var8.getFieldMustInput())) {
				var4.add(var9);
			}

			String var11 = var8.getFieldShowType();
			Object var12 = null;
			if (com.huizhixin.smart.modules.online.cgform.b.c.a(var8.getDbType())) {
				NumberProperty var19 = new NumberProperty(var9, var10, "number");
				if (CgformValidPatternEnum.INTEGER.getType().equals(var8.getFieldValidType())) {
					var19.setPattern(CgformValidPatternEnum.INTEGER.getPattern());
				}

				var12 = var19;
			} else if (b(var11)) {
				Object var17 = new ArrayList();
				if (oConvertUtils.isNotEmpty(var8.getDictTable())) {
					var17 = var6.queryTableDictItemsByCode(var8.getDictTable(), var8.getDictText(),
							var8.getDictField());
				} else if (oConvertUtils.isNotEmpty(var8.getDictField())) {
					var17 = var6.queryDictItemsByCode(var8.getDictField());
				}

				var12 = new StringProperty(var9, var10, var11, var8.getDbLength(), (List) var17);
			} else if ("popup".equals(var11)) {
				var12 = new PopupProperty(var9, var10, var8.getDictTable(), var8.getDictText(), var8.getDictField());
			} else if ("sel_search".equals(var11)) {
				var12 = new DictProperty(var9, var10, var8.getDictTable(), var8.getDictField(), var8.getDictText());
			} else if (var2 != null && var9.equals(var2.getFieldName())) {
				String var16 = var2.getTableName() + "," + var2.getTextField() + "," + var2.getCodeField();
				TreeSelectProperty var18 = new TreeSelectProperty(var9, var10, var16, var2.getPidField(),
						var2.getPidValue());
				var18.setHasChildField(var2.getHsaChildField());
				var12 = var18;
			} else {
				StringProperty var13 = new StringProperty(var9, var10, var11, var8.getDbLength());
				if (oConvertUtils.isNotEmpty(var8.getFieldValidType())) {
					CgformValidPatternEnum var14 = CgformValidPatternEnum
							.getPatternInfoByType(var8.getFieldValidType());
					if (var14 != null) {
						if (CgformValidPatternEnum.NOTNULL == var14) {
							var4.add(var9);
						} else {
							var13.setPattern(var14.getPattern());
							var13.setErrorInfo(var14.getMsg());
						}
					}
				}

				var12 = var13;
			}

			if (var1 != null && var1.indexOf(var9) >= 0) {
				((CommonProperty) var12).setDisabled(true);
			}

			((CommonProperty) var12).setOrder(var8.getOrderNum());
			var5.add(var12);
		}
	}

	public static JSONObject a(String var0, List<OnlCgformField> var1) {
		new JSONObject();
		ArrayList var3 = new ArrayList();
		ArrayList var4 = new ArrayList();
		ISysBaseAPI var5 = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
		Iterator var6 = var1.iterator();

		while (var6.hasNext()) {
			OnlCgformField var7 = (OnlCgformField) var6.next();
			String var8 = var7.getDbFieldName();
			if (!"id".equals(var8)) {
				String var9 = var7.getDbFieldTxt();
				if ("1".equals(var7.getFieldMustInput())) {
					var3.add(var8);
				}

				String var10 = var7.getFieldShowType();
				String var11 = var7.getDictField();
				Object var12 = null;
				if (com.huizhixin.smart.modules.online.cgform.b.c.a(var7.getDbType())) {
					var12 = new NumberProperty(var8, var9, "number");
				} else if (b(var10)) {
					List var13 = var5.queryDictItemsByCode(var11);
					var12 = new StringProperty(var8, var9, var10, var7.getDbLength(), var13);
				} else {
					var12 = new StringProperty(var8, var9, var10, var7.getDbLength());
				}

				((CommonProperty) var12).setOrder(var7.getOrderNum());
				var4.add(var12);
			}
		}

		JSONObject var2 = JsonschemaUtil.getSubJsonSchema(var0, var3, var4);
		return var2;
	}

	public static Map<String, Object> a(String var0, List<OnlCgformField> var1, JSONObject var2) {
		StringBuffer var3 = new StringBuffer();
		StringBuffer var4 = new StringBuffer();
		String var5 = "";

		try {
			var5 = com.huizhixin.smart.modules.online.config.util.d.getDatabaseType();
		} catch (SQLException var14) {
			var14.printStackTrace();
		} catch (DBException var15) {
			var15.printStackTrace();
		}

		HashMap var6 = new HashMap();
		boolean var7 = false;
		String var8 = null;
		LoginUser var9 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (var9 == null) {
			throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
		} else {
			Iterator var10 = var1.iterator();

			while (true) {
				while (var10.hasNext()) {
					OnlCgformField var11 = (OnlCgformField) var10.next();
					String var12 = var11.getDbFieldName();
					if (null == var12) {
						B.info("--------online保存表单数据遇见空名称的字段------->>" + var11.getId());
					} else if ("id".equals(var12.toLowerCase())) {
						var7 = true;
						var8 = var2.getString(var12);
					} else {
						a(var11, var9, var2, "CREATE_BY", "CREATE_TIME", "SYS_ORG_CODE");
						if ((var11.getIsShowForm() == 1 || !oConvertUtils.isEmpty(var11.getMainField()))
								&& var2.get(var12) != null) {
							String var13;
							if ("".equals(var2.get(var12))) {
								var13 = var11.getDbType();
								if (com.huizhixin.smart.modules.online.cgform.b.c.a(var13)
										|| com.huizhixin.smart.modules.online.cgform.b.c.b(var13)) {
									continue;
								}
							}

							var3.append("," + var12);
							var13 = com.huizhixin.smart.modules.online.cgform.b.c.a(var5, var11, var2, var6);
							var4.append("," + var13);
						}
					}
				}

				if (var7) {
					if (oConvertUtils.isEmpty(var8)) {
						var8 = UUIDGenerator.generate();
					}
				} else {
					var8 = UUIDGenerator.generate();
				}

				String var16 = "insert into " + var0 + "(" + "id" + var3.toString() + ") values(" + "'" + var8 + "'"
						+ var4.toString() + ")";
				var6.put("execute_sql_string", var16);
				B.info("--动态表单保存sql-->" + var16);
				return var6;
			}
		}
	}

	public static Map<String, Object> b(String var0, List<OnlCgformField> var1, JSONObject var2) {
		StringBuffer var3 = new StringBuffer();
		HashMap var4 = new HashMap();
		String var5 = "";

		try {
			var5 = com.huizhixin.smart.modules.online.config.util.d.getDatabaseType();
		} catch (SQLException var11) {
			var11.printStackTrace();
		} catch (DBException var12) {
			var12.printStackTrace();
		}

		LoginUser var6 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (var6 == null) {
			throw new JeecgBootException("online修改表单数据异常:系统未找到当前登陆用户信息");
		} else {
			Iterator var7 = var1.iterator();

			while (true) {
				while (var7.hasNext()) {
					OnlCgformField var8 = (OnlCgformField) var7.next();
					String var9 = var8.getDbFieldName();
					if (null == var9) {
						B.info("--------online修改表单数据遇见空名称的字段------->>" + var8.getId());
					} else {
						a(var8, var6, var2, "UPDATE_BY", "UPDATE_TIME", "SYS_ORG_CODE");
						if (var8.getIsShowForm() == 1 && !"id".equals(var9)) {
							String var10;
							if ("".equals(var2.get(var9))) {
								var10 = var8.getDbType();
								if (com.huizhixin.smart.modules.online.cgform.b.c.a(var10)
										|| com.huizhixin.smart.modules.online.cgform.b.c.b(var10)) {
									continue;
								}
							}

							var10 = com.huizhixin.smart.modules.online.cgform.b.c.a(var5, var8, var2, var4);
							var3.append(var9 + "=" + var10 + ",");
						}
					}
				}

				String var13 = var3.toString();
				if (var13.endsWith(",")) {
					var13 = var13.substring(0, var13.length() - 1);
				}

				String var14 = "update " + var0 + " set " + var13 + " where 1=1  " + " AND " + "id" + "=" + "'"
						+ var2.getString("id") + "'";
				B.info("--动态表单编辑sql-->" + var14);
				var4.put("execute_sql_string", var14);
				return var4;
			}
		}
	}

	public static String a(String var0, List<OnlCgformField> var1, String var2) {
		return a(var0, var1, "id", var2);
	}

	public static String a(String var0, List<OnlCgformField> var1, String var2, String var3) {
		StringBuffer var4 = new StringBuffer();
		var4.append("SELECT ");
		int var5 = var1.size();
		boolean var6 = false;

		for (int var7 = 0; var7 < var5; ++var7) {
			String var8 = ((OnlCgformField) var1.get(var7)).getDbFieldName();
			if ("id".equals(var8)) {
				var6 = true;
			}

			var4.append(var8);
			if (var5 > var7 + 1) {
				var4.append(",");
			}
		}

		if (!var6) {
			var4.append(",id");
		}

		var4.append(" FROM " + var0 + " where 1=1  " + " AND " + var2 + "=" + "'" + var3 + "'");
		return var4.toString();
	}

	public static void a(OnlCgformField var0, LoginUser var1, JSONObject var2, String... var3) {
		String var4 = var0.getDbFieldName();
		boolean var5 = false;
		String[] var6 = var3;
		int var7 = var3.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			String var9 = var6[var8];
			if (var4.toUpperCase().equals(var9)) {
				if (var0.getIsShowForm() == 1) {
					if (var2.get(var4) == null) {
						var5 = true;
					}
				} else {
					var0.setIsShowForm(1);
					var5 = true;
				}

				if (var5) {
					byte var11 = -1;
					switch (var9.hashCode()) {
						case -909973894 :
							if (var9.equals("CREATE_BY")) {
								var11 = 0;
							}
							break;
						case -99751974 :
							if (var9.equals("SYS_ORG_CODE")) {
								var11 = 4;
							}
							break;
						case 837427085 :
							if (var9.equals("UPDATE_BY")) {
								var11 = 2;
							}
							break;
						case 1609067651 :
							if (var9.equals("UPDATE_TIME")) {
								var11 = 3;
							}
							break;
						case 1688939568 :
							if (var9.equals("CREATE_TIME")) {
								var11 = 1;
							}
					}

					switch (var11) {
						case 0 :
							var2.put(var4, var1.getUsername());
							return;
						case 1 :
							var0.setFieldShowType("datetime");
							var2.put(var4, DateUtils.formatDateTime());
							return;
						case 2 :
							var2.put(var4, var1.getUsername());
							return;
						case 3 :
							var0.setFieldShowType("datetime");
							var2.put(var4, DateUtils.formatDateTime());
							return;
						case 4 :
							var2.put(var4, var1.getOrgCode());
					}
				}
				break;
			}
		}

	}

	public static boolean a(Object var0, Object var1) {
		if (oConvertUtils.isEmpty(var0) && oConvertUtils.isEmpty(var1)) {
			return true;
		} else {
			return var0.equals(var1);
		}
	}

	public static boolean a(OnlCgformField var0, OnlCgformField var1) {
		return !a((Object) var0.getDbFieldName(), (Object) var1.getDbFieldName())
				|| !a((Object) var0.getDbFieldTxt(), (Object) var1.getDbFieldTxt())
				|| !a((Object) var0.getDbLength(), (Object) var1.getDbLength())
				|| !a((Object) var0.getDbPointLength(), (Object) var1.getDbPointLength())
				|| !a((Object) var0.getDbType(), (Object) var1.getDbType())
				|| !a((Object) var0.getDbIsNull(), (Object) var1.getDbIsNull())
				|| !a((Object) var0.getDbIsKey(), (Object) var1.getDbIsKey())
				|| !a((Object) var0.getMainTable(), (Object) var1.getMainTable())
				|| !a((Object) var0.getMainField(), (Object) var1.getMainField())
				|| !a((Object) var0.getDbDefaultVal(), (Object) var1.getDbDefaultVal());
	}

	public static boolean a(OnlCgformIndex var0, OnlCgformIndex var1) {
		return !a((Object) var0.getIndexName(), (Object) var1.getIndexName())
				|| !a((Object) var0.getIndexField(), (Object) var1.getIndexField())
				|| !a((Object) var0.getIndexType(), (Object) var1.getIndexType());
	}

	public static boolean a(OnlCgformHead var0, OnlCgformHead var1) {
		return !a((Object) var0.getTableName(), (Object) var1.getTableName())
				|| !a((Object) var0.getTableTxt(), (Object) var1.getTableTxt());
	}

	public static String a(String var0, List<OnlCgformField> var1, Map<String, Object> var2) {
		StringBuffer var3 = new StringBuffer();
		StringBuffer var4 = new StringBuffer();
		Iterator var5 = var1.iterator();

		while (var5.hasNext()) {
			OnlCgformField var6 = (OnlCgformField) var5.next();
			String var7 = var6.getDbFieldName();
			String var8 = var6.getDbType();
			if (var6.getIsShowList() == 1) {
				var4.append("," + var7);
			}

			boolean var9;
			String var10;
			if (oConvertUtils.isNotEmpty(var6.getMainField())) {
				var9 = !com.huizhixin.smart.modules.online.cgform.b.c.a(var8);
				var10 = QueryGenerator.getSingleQueryConditionSql(var7, "", var2.get(var7), var9);
				if (!"".equals(var10)) {
					var3.append(" AND " + var10);
				}
			}

			if (var6.getIsQuery() == 1) {
				if ("single".equals(var6.getQueryMode())) {
					if (var2.get(var7) != null) {
						var9 = !com.huizhixin.smart.modules.online.cgform.b.c.a(var8);
						var10 = QueryGenerator.getSingleQueryConditionSql(var7, "", var2.get(var7), var9);
						if (!"".equals(var10)) {
							var3.append(" AND " + var10);
						}
					}
				} else {
					Object var11 = var2.get(var7 + "_begin");
					if (var11 != null) {
						var3.append(" AND " + var7 + ">=");
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var8)) {
							var3.append(var11.toString());
						} else {
							var3.append("'" + var11.toString() + "'");
						}
					}

					Object var12 = var2.get(var7 + "_end");
					if (var12 != null) {
						var3.append(" AND " + var7 + ">=");
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var8)) {
							var3.append(var12.toString());
						} else {
							var3.append("'" + var12.toString() + "'");
						}
					}
				}
			}
		}

		return "SELECT id" + var4.toString() + " FROM " + var0 + " where 1=1  " + var3.toString();
	}

	public static List<ExcelExportEntity> a(List<OnlCgformField> var0, String var1) {
		ArrayList var2 = new ArrayList();

		for (int var3 = 0; var3 < var0.size(); ++var3) {
			if ((null == var1 || !var1.equals(((OnlCgformField) var0.get(var3)).getDbFieldName()))
					&& ((OnlCgformField) var0.get(var3)).getIsShowList() == 1) {
				ExcelExportEntity var4 = new ExcelExportEntity(((OnlCgformField) var0.get(var3)).getDbFieldTxt(),
						((OnlCgformField) var0.get(var3)).getDbFieldName());
				int var5 = ((OnlCgformField) var0.get(var3)).getDbLength() == 0
						? 12
						: (((OnlCgformField) var0.get(var3)).getDbLength() > 30
								? 30
								: ((OnlCgformField) var0.get(var3)).getDbLength());
				if (((OnlCgformField) var0.get(var3)).getFieldShowType().equals("date")) {
					var4.setFormat("yyyy-MM-dd");
				} else if (((OnlCgformField) var0.get(var3)).getFieldShowType().equals("datetime")) {
					var4.setFormat("yyyy-MM-dd HH:mm:ss");
				}

				var4.setWidth((double) var5);
				var2.add(var4);
			}
		}

		return var2;
	}

	public static boolean a(OnlCgformEnhanceJava var0) {
		String var1 = var0.getCgJavaType();
		String var2 = var0.getCgJavaValue();
		if (oConvertUtils.isNotEmpty(var2)) {
			try {
				if ("class".equals(var1)) {
					Class var3 = Class.forName(var2);
					if (var3 == null || var3.newInstance() == null) {
						return false;
					}
				}

				if ("spring".equals(var1)) {
					Object var5 = SpringContextUtils.getBean(var2);
					if (var5 == null) {
						return false;
					}
				}
			} catch (Exception var4) {
				B.error(var4.getMessage(), var4);
				return false;
			}
		}

		return true;
	}

	public static void a(List<String> var0) {
		Collections.sort(var0, new Comparator<String>()
	    {
	      public int compare(String paramAnonymousString1, String paramAnonymousString2)
	      {
	        if ((paramAnonymousString1 == null) || (paramAnonymousString2 == null)) {
	          return -1;
	        }
	        if (paramAnonymousString1.compareTo(paramAnonymousString2) > 0) {
	          return 1;
	        }
	        if (paramAnonymousString1.compareTo(paramAnonymousString2) < 0) {
	          return -1;
	        }
	        if (paramAnonymousString1.compareTo(paramAnonymousString2) == 0) {
	          return 0;
	        }
	        return 0;
	      }
	    });
   }

	public static void b(List<String> var0) {
      Collections.sort(var0, new Comparator<String>()
      {
          public int compare(String paramAnonymousString1, String paramAnonymousString2)
          {
            if ((paramAnonymousString1 == null) || (paramAnonymousString2 == null)) {
              return -1;
            }
            if (paramAnonymousString1.length() > paramAnonymousString2.length()) {
              return 1;
            }
            if (paramAnonymousString1.length() < paramAnonymousString2.length()) {
              return -1;
            }
            if (paramAnonymousString1.compareTo(paramAnonymousString2) > 0) {
              return 1;
            }
            if (paramAnonymousString1.compareTo(paramAnonymousString2) < 0) {
              return -1;
            }
            if (paramAnonymousString1.compareTo(paramAnonymousString2) == 0) {
              return 0;
            }
            return 0;
          }
        });
   }

	private static String a(String var0, boolean var1) {
		return var1 ? "'" + QueryGenerator.converRuleValue(var0) + "'" : QueryGenerator.converRuleValue(var0);
	}

	private static void a(final SysPermissionDataRule sysPermissionDataRule, final String s, final String s2, final StringBuffer sb) {
        final QueryRuleEnum byValue = QueryRuleEnum.getByValue(sysPermissionDataRule.getRuleConditions());
        final String a = a(sysPermissionDataRule.getRuleValue(), !com.huizhixin.smart.modules.online.cgform.b.c.a(s2));
        if (a == null || byValue == null) {
            return;
        }
        switch (byValue.ordinal()) {
            case 1: {
                sb.append(" AND " + s + " > " + a);
                break;
            }
            case 2: {
                sb.append(" AND " + s + ">=" + a);
                break;
            }
            case 3: {
                sb.append(" AND " + s + " > " + a);
                break;
            }
            case 4: {
                sb.append(" AND " + s + "<=" + a);
                break;
            }
            case 5: {
                sb.append(" AND " + s + "=" + a);
                break;
            }
            case 6: {
                sb.append(" AND " + s + " <> " + a);
                break;
            }
            case 7: {
                sb.append(" AND " + s + " IN " + QueryGenerator.converRuleValue(a));
                break;
            }
            case 8: {
                sb.append(" AND " + s + " LIKE '%" + QueryGenerator.converRuleValue(a) + "%'");
                break;
            }
            case 9: {
                sb.append(" AND " + s + " LIKE '%" + QueryGenerator.converRuleValue(a) + "'");
                break;
            }
            case 10: {
                sb.append(" AND " + s + " LIKE '" + QueryGenerator.converRuleValue(a) + "%'");
                break;
            }
            default: {
            	com.huizhixin.smart.modules.online.cgform.b.b.B.info("--\u67e5\u8be2\u89c4\u5219\u672a\u5339\u914d\u5230---");
                break;
            }
        }
    }

	public static String a(String var0, JSONObject var1) {
		if (var1 == null) {
			return var0;
		} else {
			var0 = var0.replace("#{UUID}", UUIDGenerator.generate());
			Set var2 = QueryGenerator.getSqlRuleParams(var0);
			Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				String var4 = (String) var3.next();
				if (var1.get(var4) == null) {
					String var5 = QueryGenerator.converRuleValue(var4);
					var0 = var0.replace("#{" + var4 + "}", var5);
				} else {
					var0 = var0.replace("#{" + var4 + "}", var1.getString(var4));
				}
			}

			return var0;
		}
	}

	public static String b(String var0, List<OnlCgformButton> var1) {
		var0 = c(var0, var1);
		String[] var2 = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created".split(",");
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String var5 = var2[var4];
			Pattern var6;
			Matcher var7;
			if ("beforeAdd,afterAdd,mounted,created".indexOf(var5) >= 0) {
				var6 = Pattern.compile("(" + var5 + "\\s*\\(\\)\\s*\\{)");
				var7 = var6.matcher(var0);
				if (var7.find()) {
					var0 = var0.replace(var7.group(0), var5
							+ "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
				}
			} else {
				var6 = Pattern.compile("(" + var5 + "\\s*\\(row\\)\\s*\\{)");
				var7 = var6.matcher(var0);
				if (var7.find()) {
					var0 = var0.replace(var7.group(0), var5
							+ "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
				} else {
					Pattern var8 = Pattern.compile("(" + var5 + "\\s*\\(\\)\\s*\\{)");
					Matcher var9 = var8.matcher(var0);
					if (var9.find()) {
						var0 = var0.replace(var9.group(0), var5
								+ "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
					}
				}
			}
		}

		return c(var0);
	}

	public static void a(OnlCgformEnhanceJs var0, String var1, List<OnlCgformField> var2) {
		if (var0 != null && !oConvertUtils.isEmpty(var0.getCgJs())) {
			String var3 = var0.getCgJs();
			String var4 = "onlChange";
			Pattern var5 = Pattern.compile("(" + var1 + "_" + var4 + "\\s*\\(\\)\\s*\\{)");
			Matcher var6 = var5.matcher(var3);
			if (var6.find()) {
				var3 = var3.replace(var6.group(0), var1 + "_" + var4
						+ "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
				Iterator var7 = var2.iterator();

				while (var7.hasNext()) {
					OnlCgformField var8 = (OnlCgformField) var7.next();
					Pattern var9 = Pattern.compile("(" + var8.getDbFieldName() + "\\s*\\(\\))");
					Matcher var10 = var9.matcher(var3);
					if (var10.find()) {
						var3 = var3.replace(var10.group(0), var8.getDbFieldName() + "(that,event)");
					}
				}
			}

			var0.setCgJs(var3);
		}
	}

	public static void a(OnlCgformEnhanceJs var0, String var1, List<OnlCgformField> var2, boolean var3) {
		if (var0 != null && !oConvertUtils.isEmpty(var0.getCgJs())) {
			String var4 = var0.getCgJs();
			String var5 = "onlChange";
			Pattern var6 = Pattern.compile("([^_]" + var5 + "\\s*\\(\\)\\s*\\{)");
			Matcher var7 = var6.matcher(var4);
			if (var7.find()) {
				var4 = var4.replace(var7.group(0), var5
						+ "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
				Iterator var8 = var2.iterator();

				while (var8.hasNext()) {
					OnlCgformField var9 = (OnlCgformField) var8.next();
					Pattern var10 = Pattern.compile("(" + var9.getDbFieldName() + "\\s*\\(\\))");
					Matcher var11 = var10.matcher(var4);
					if (var11.find()) {
						var4 = var4.replace(var11.group(0), var9.getDbFieldName() + "(that,event)");
					}
				}
			}

			var0.setCgJs(var4);
			a(var0, var1, var2);
		}
	}

	public static String c(String var0) {
		B.info("最终的增强JS", var0);
		return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}"
				+ var0 + "}";
	}

	public static String c(String var0, List<OnlCgformButton> var1) {
		if (var1 != null) {
			Iterator var2 = var1.iterator();

			while (true) {
				while (var2.hasNext()) {
					OnlCgformButton var3 = (OnlCgformButton) var2.next();
					String var4 = var3.getButtonCode();
					Pattern var5;
					Matcher var6;
					if ("link".equals(var3.getButtonStyle())) {
						var5 = Pattern.compile("(" + var4 + "\\s*\\(row\\)\\s*\\{)");
						var6 = var5.matcher(var0);
						if (var6.find()) {
							var0 = var0.replace(var6.group(0), var4
									+ "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
						} else {
							Pattern var7 = Pattern.compile("(" + var4 + "\\s*\\(\\)\\s*\\{)");
							Matcher var8 = var7.matcher(var0);
							if (var8.find()) {
								var0 = var0.replace(var8.group(0), var4
										+ "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
							}
						}
					} else if ("button".equals(var3.getButtonStyle()) || "form".equals(var3.getButtonStyle())) {
						var5 = Pattern.compile("(" + var4 + "\\s*\\(\\)\\s*\\{)");
						var6 = var5.matcher(var0);
						if (var6.find()) {
							var0 = var0.replace(var6.group(0), var4
									+ "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
						}
					}
				}

				return var0;
			}
		} else {
			return var0;
		}
	}

	public static JSONArray c(List<OnlCgformField> var0) {
		JSONArray var1 = new JSONArray();
		ISysBaseAPI var2 = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
		Iterator var3 = var0.iterator();

		while (var3.hasNext()) {
			OnlCgformField var4 = (OnlCgformField) var3.next();
			String var5 = var4.getDbFieldName();
			if (!"id".equals(var5)) {
				JSONObject var6 = new JSONObject();
				var6.put("title", var4.getDbFieldTxt());
				var6.put("key", var5);
				var6.put("width", "186px");
				var6.put("type", b(var4));
				var6.put("defaultValue", var4.getDbDefaultVal());
				var6.put("placeholder", "请输入" + var4.getDbFieldTxt());
				var6.put("validateRules", a(var4));
				if ("list".equals(var4.getFieldShowType())) {
					Object var7 = new ArrayList();
					if (oConvertUtils.isNotEmpty(var4.getDictTable())) {
						var7 = var2.queryTableDictItemsByCode(var4.getDictTable(), var4.getDictText(),
								var4.getDictField());
					} else if (oConvertUtils.isNotEmpty(var4.getDictField())) {
						var7 = var2.queryDictItemsByCode(var4.getDictField());
					}

					var6.put("options", var7);
				}

				var1.add(var6);
			}
		}

		return var1;
	}

	private static JSONArray a(OnlCgformField var0) {
		JSONArray var1 = new JSONArray();
		JSONObject var2;
		if (var0.getDbIsNull() == 0 || "1".equals(var0.getFieldMustInput())) {
			var2 = new JSONObject();
			var2.put("required", true);
			var2.put("message", var0.getDbFieldTxt() + "不能为空!");
			var1.add(var2);
		}

		if (oConvertUtils.isNotEmpty(var0.getFieldValidType())) {
			var2 = new JSONObject();
			var2.put("pattern", var0.getFieldValidType());
			var2.put("message", var0.getDbFieldTxt() + "格式不正确");
			var1.add(var2);
		}

		return var1;
	}

	public static Map<String, Object> a(Map<String, Object> var0) {
		HashMap var1 = new HashMap();
		if (var0 != null && !var0.isEmpty()) {
			Set var2 = var0.keySet();
			Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				String var4 = (String) var3.next();
				String var5 = var4.toLowerCase();
				var1.put(var5, var0.get(var4));
			}

			return var1;
		} else {
			return var1;
		}
	}

	public static List<Map<String, Object>> d(List<Map<String, Object>> var0) {
		ArrayList var1 = new ArrayList();
		Iterator var2 = var0.iterator();

		while (var2.hasNext()) {
			Map var3 = (Map) var2.next();
			HashMap var4 = new HashMap();
			Set var5 = var3.keySet();
			Iterator var6 = var5.iterator();

			while (var6.hasNext()) {
				String var7 = (String) var6.next();
				String var8 = var7.toLowerCase();
				var4.put(var8, var3.get(var7));
			}

			var1.add(var4);
		}

		return var1;
	}

	public static Map<String, Object> c(String var0, List<OnlCgformField> var1, JSONObject var2) {
		StringBuffer var3 = new StringBuffer();
		StringBuffer var4 = new StringBuffer();
		String var5 = "";

		try {
			var5 = com.huizhixin.smart.modules.online.config.util.d.getDatabaseType();
		} catch (SQLException var13) {
			var13.printStackTrace();
		} catch (DBException var14) {
			var14.printStackTrace();
		}

		HashMap var6 = new HashMap();
		boolean var7 = false;
		String var8 = null;
		Iterator var9 = var1.iterator();

		while (true) {
			while (var9.hasNext()) {
				OnlCgformField var10 = (OnlCgformField) var9.next();
				String var11 = var10.getDbFieldName();
				if (null == var11) {
					B.info("--------online保存表单数据遇见空名称的字段------->>" + var10.getId());
				} else if (var2.get(var11) != null) {
					String var12;
					if ("".equals(var2.get(var11))) {
						var12 = var10.getDbType();
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var12)
								|| com.huizhixin.smart.modules.online.cgform.b.c.b(var12)) {
							continue;
						}
					}

					if ("id".equals(var11.toLowerCase())) {
						var7 = true;
						var8 = var2.getString(var11);
					} else {
						var3.append("," + var11);
						var12 = com.huizhixin.smart.modules.online.cgform.b.c.a(var5, var10, var2, var6);
						var4.append("," + var12);
					}
				}
			}

			if (!var7 || oConvertUtils.isEmpty(var8)) {
				var8 = UUIDGenerator.generate();
			}

			String var15 = "insert into " + var0 + "(" + "id" + var3.toString() + ") values(" + "'" + var8 + "'"
					+ var4.toString() + ")";
			var6.put("execute_sql_string", var15);
			B.info("--表单设计器表单保存sql-->" + var15);
			return var6;
		}
	}

	public static Map<String, Object> d(String var0, List<OnlCgformField> var1, JSONObject var2) {
		StringBuffer var3 = new StringBuffer();
		HashMap var4 = new HashMap();
		String var5 = "";

		try {
			var5 = com.huizhixin.smart.modules.online.config.util.d.getDatabaseType();
		} catch (SQLException var10) {
			var10.printStackTrace();
		} catch (DBException var11) {
			var11.printStackTrace();
		}

		Iterator var6 = var1.iterator();

		while (true) {
			while (var6.hasNext()) {
				OnlCgformField var7 = (OnlCgformField) var6.next();
				String var8 = var7.getDbFieldName();
				if (null == var8) {
					B.info("--------online修改表单数据遇见空名称的字段------->>" + var7.getId());
				} else if (!"id".equals(var8) && var2.get(var8) != null) {
					String var9;
					if ("".equals(var2.get(var8))) {
						var9 = var7.getDbType();
						if (com.huizhixin.smart.modules.online.cgform.b.c.a(var9)
								|| com.huizhixin.smart.modules.online.cgform.b.c.b(var9)) {
							continue;
						}
					}

					var9 = com.huizhixin.smart.modules.online.cgform.b.c.a(var5, var7, var2, var4);
					var3.append(var8 + "=" + var9 + ",");
				}
			}

			String var12 = var3.toString();
			if (var12.endsWith(",")) {
				var12 = var12.substring(0, var12.length() - 1);
			}

			String var13 = "update " + var0 + " set " + var12 + " where 1=1  " + " AND " + "id" + "=" + "'"
					+ var2.getString("id") + "'";
			B.info("--表单设计器表单编辑sql-->" + var13);
			var4.put("execute_sql_string", var13);
			return var4;
		}
	}

	private static String b(OnlCgformField var0) {
		if ("checkbox".equals(var0.getFieldShowType())) {
			return "checkbox";
		} else if ("list".equals(var0.getFieldShowType())) {
			return "select";
		} else if ("datetime".equals(var0.getFieldShowType())) {
			return "datetime";
		} else if ("date".equals(var0.getFieldShowType())) {
			return "date";
		} else {
			return !"int".equals(var0.getDbType()) && !"double".equals(var0.getDbType())
					&& !"BigDecimal".equals(var0.getDbType()) ? "input" : "inputNumber";
		}
	}
}