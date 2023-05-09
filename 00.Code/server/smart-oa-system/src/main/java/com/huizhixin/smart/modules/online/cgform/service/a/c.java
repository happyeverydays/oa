package com.huizhixin.smart.modules.online.cgform.service.a;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.hibernate.HibernateException;
import com.huizhixin.smart.common.api.vo.Result;
import com.huizhixin.smart.common.util.MyClassLoader;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.common.util.UUIDGenerator;
import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformButton;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJava;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceJs;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformEnhanceSql;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformHead;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformIndex;
import com.huizhixin.smart.modules.online.cgform.enums.CgformEnum;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformButtonMapper;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformEnhanceJsMapper;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformHeadMapper;
import com.huizhixin.smart.modules.online.cgform.model.OnlGenerateModel;
import com.huizhixin.smart.modules.online.cgform.model.a;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformFieldService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformHeadService;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformIndexService;
import com.huizhixin.smart.modules.online.config.a.b;
import com.huizhixin.smart.modules.online.config.exception.BusinessException;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.online.config.service.DbTableHandleI;
import com.huizhixin.smart.modules.online.config.util.d;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOne;
import org.jeecgframework.codegenerate.generate.impl.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.jeecgframework.codegenerate.generate.pojo.TableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.MainTableVo;
import org.jeecgframework.codegenerate.generate.pojo.onetomany.SubTableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("onlCgformHeadServiceImpl")
public class c extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {
	private static final Logger a = LoggerFactory.getLogger(c.class);
	@Autowired
	private IOnlCgformFieldService fieldService;
	@Autowired
	private IOnlCgformIndexService indexService;
	@Autowired
	private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;
	@Autowired
	private OnlCgformButtonMapper onlCgformButtonMapper;
	@Autowired
	private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
	@Autowired
	private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
	@Autowired
	private b dataBaseConfig;

	@Transactional(rollbackFor = {Exception.class})
	public Result<?> addAll(a model) {
		String var2 = UUID.randomUUID().toString().replace("-", "");
		OnlCgformHead var3 = model.getHead();
		List var4 = model.getFields();
		List var5 = model.getIndexs();
		var3.setId(var2);

		for (int var6 = 0; var6 < var4.size(); ++var6) {
			OnlCgformField var7 = (OnlCgformField) var4.get(var6);
			var7.setId((String) null);
			var7.setCgformHeadId(var2);
			if (var7.getOrderNum() == null) {
				var7.setOrderNum(var6);
			}
		}

		Iterator var8 = var5.iterator();

		while (var8.hasNext()) {
			OnlCgformIndex var9 = (OnlCgformIndex) var8.next();
			var9.setId((String) null);
			var9.setCgformHeadId(var2);
		}

		var3.setIsDbSynch("N");
		var3.setTableVersion(1);
		super.save(var3);
		this.fieldService.saveBatch(var4);
		this.indexService.saveBatch(var5);
		this.a(var3, var4);
		return Result.ok("添加成功");
	}

	@Transactional(rollbackFor = {Exception.class})
	public Result<?> editAll(a model) {
		OnlCgformHead var2 = model.getHead();
		OnlCgformHead var3 = (OnlCgformHead) super.getById(var2.getId());
		if (var3 == null) {
			return Result.error("未找到对应实体");
		} else {
			String var4 = var3.getIsDbSynch();
			if (com.huizhixin.smart.modules.online.cgform.b.b.a(var3, var2)) {
				var4 = "N";
			}

			Integer var5 = var3.getTableVersion();
			if (var5 == null) {
				var5 = 1;
			}

			var2.setTableVersion(var5 + 1);
			List var6 = model.getFields();
			List var7 = model.getIndexs();
			ArrayList var8 = new ArrayList();
			ArrayList var9 = new ArrayList();
			Iterator var10 = var6.iterator();

			while (var10.hasNext()) {
				OnlCgformField var11 = (OnlCgformField) var10.next();
				String var12 = String.valueOf(var11.getId());
				if (var12.length() == 32) {
					var9.add(var11);
				} else {
					String var13 = "_pk";
					if (!var13.equals(var12)) {
						var11.setId((String) null);
						var11.setCgformHeadId(var2.getId());
						var8.add(var11);
					}
				}
			}

			if (var8.size() > 0) {
				var4 = "N";
			}

			int var17 = 0;

			Iterator var18;
			OnlCgformField var20;
			for (var18 = var9.iterator(); var18.hasNext(); this.fieldService.updateById(var20)) {
				var20 = (OnlCgformField) var18.next();
				OnlCgformField var22 = (OnlCgformField) this.fieldService.getById(var20.getId());
				boolean var14 = com.huizhixin.smart.modules.online.cgform.b.b.a(var22, var20);
				if (var14) {
					var4 = "N";
				}

				if ((var22.getOrderNum() == null ? 0 : var22.getOrderNum()) > var17) {
					var17 = var22.getOrderNum();
				}
			}

			for (var18 = var8.iterator(); var18.hasNext(); this.fieldService.save(var20)) {
				var20 = (OnlCgformField) var18.next();
				if (var20.getOrderNum() == null) {
					++var17;
					var20.setOrderNum(var17);
				}
			}

			ArrayList var19 = new ArrayList();
			ArrayList var21 = new ArrayList();
			Iterator var23 = var7.iterator();

			OnlCgformIndex var24;
			while (var23.hasNext()) {
				var24 = (OnlCgformIndex) var23.next();
				String var15 = String.valueOf(var24.getId());
				if (var15.length() == 32) {
					var21.add(var24);
				} else {
					var24.setId((String) null);
					var24.setCgformHeadId(var2.getId());
					var19.add(var24);
				}
			}

			if (var19.size() > 0) {
				var4 = "N";
			}

			this.indexService.saveBatch(var19);

			for (var23 = var21.iterator(); var23.hasNext(); this.indexService.updateById(var24)) {
				var24 = (OnlCgformIndex) var23.next();
				OnlCgformIndex var25 = (OnlCgformIndex) this.indexService.getById(var24.getId());
				boolean var16 = com.huizhixin.smart.modules.online.cgform.b.b.a(var25, var24);
				if (var16) {
					var4 = "N";
				}
			}

			if (model.getDeleteFieldIds().size() > 0) {
				this.fieldService.removeByIds(model.getDeleteFieldIds());
			}

			if (model.getDeleteIndexIds().size() > 0) {
				this.indexService.removeByIds(model.getDeleteIndexIds());
			}

			var2.setIsDbSynch(var4);
			super.updateById(var2);
			this.a(var2, var6);
			return Result.ok("全部修改成功");
		}
	}

	public void doDbSynch(String code, String synMethod)
			throws HibernateException, IOException, TemplateException, SQLException, DBException {
		OnlCgformHead var3 = (OnlCgformHead) this.getById(code);
		if (var3 == null) {
			throw new DBException("实体配置不存在");
		} else {
			String var4 = var3.getTableName();
			LambdaQueryWrapper<OnlCgformField> var5 = new LambdaQueryWrapper<OnlCgformField>();
			var5.eq(OnlCgformField::getCgformHeadId, code);
			var5.orderByAsc(OnlCgformField::getOrderNum);
			List var6 = this.fieldService.list(var5);
			com.huizhixin.smart.modules.online.config.a.a var7 = new com.huizhixin.smart.modules.online.config.a.a();
			var7.setTableName(var4);
			var7.setJformPkType(var3.getIdType());
			var7.setJformPkSequence(var3.getIdSequence());
			var7.setContent(var3.getTableTxt());
			var7.setColumns(var6);
			var7.setDbConfig(this.dataBaseConfig);
			if ("normal".equals(synMethod)) {
				boolean var8 = d.a(var4);
				if (var8) {
					com.huizhixin.smart.modules.online.config.util.c var9 = new com.huizhixin.smart.modules.online.config.util.c();
					List var10 = var9.b(var7);
					Iterator var11 = var10.iterator();

					while (var11.hasNext()) {
						String var12 = (String) var11.next();
						if (!oConvertUtils.isEmpty(var12) && !oConvertUtils.isEmpty(var12.trim())) {
							((OnlCgformHeadMapper) this.baseMapper).executeDDL(var12);
						}
					}

					List var17 = this.indexService
							.list((Wrapper) (new LambdaQueryWrapper<OnlCgformIndex>()).eq(OnlCgformIndex::getCgformHeadId, code));
					Iterator var18 = var17.iterator();

					while (var18.hasNext()) {
						OnlCgformIndex var13 = (OnlCgformIndex) var18.next();
						String var14 = var9.a(var13.getIndexName(), var4);
						((OnlCgformHeadMapper) this.baseMapper).executeDDL(var14);
					}
				} else {
					com.huizhixin.smart.modules.online.config.util.c.a(var7);
				}
			} else if ("force".equals(synMethod)) {
				DbTableHandleI var15 = d.getTableHandle();
				String var16 = var15.dropTableSQL(var4);
				((OnlCgformHeadMapper) this.baseMapper).executeDDL(var16);
				com.huizhixin.smart.modules.online.config.util.c.a(var7);
			}

			this.indexService.createIndex(code, d.getDatabaseType(), var4);
			var3.setIsDbSynch("Y");
			this.updateById(var3);
		}
	}

	public void deleteRecordAndTable(String id) throws DBException, SQLException {
		OnlCgformHead var2 = (OnlCgformHead) this.getById(id);
		if (var2 == null) {
			throw new DBException("实体配置不存在");
		} else {
			String var3 = d.getTableHandle().dropTableSQL(var2.getTableName());
			((OnlCgformHeadMapper) this.baseMapper).executeDDL(var3);
			((OnlCgformHeadMapper) this.baseMapper).deleteById(id);
		}
	}

	public List<Map<String, Object>> queryListData(String sql) {
		return ((OnlCgformHeadMapper) this.baseMapper).queryList(sql);
	}

	public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
		this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
	}

	public OnlCgformEnhanceJs queryEnhance(String code, String type) {
		return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne(
				(Wrapper) ((LambdaQueryWrapper<OnlCgformEnhanceJs>) (new LambdaQueryWrapper<OnlCgformEnhanceJs>()).eq(OnlCgformEnhanceJs::getCgJsType, type))
						.eq(OnlCgformEnhanceJs::getCgformHeadId, code));
	}

	public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
		this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
	}

	public OnlCgformEnhanceSql queryEnhanceSql(String formId, String buttonCode) {
		return (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne(
				(Wrapper) ((LambdaQueryWrapper<OnlCgformEnhanceSql>) (new LambdaQueryWrapper<OnlCgformEnhanceSql>()).eq(OnlCgformEnhanceSql::getCgformHeadId,
						formId)).eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
	}

	public void saveEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
		this.onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
	}

	public void editEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
		this.onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
	}

	public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		LambdaQueryWrapper<OnlCgformEnhanceJava> var2 = new LambdaQueryWrapper<OnlCgformEnhanceJava>();
		var2.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
		var2.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
		var2.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
		var2.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
		return (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne(var2);
	}

	public void saveEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
	}

	public void editEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);
	}

	public List<OnlCgformButton> queryButtonList(String code, boolean isListButton) {
		LambdaQueryWrapper<OnlCgformButton> var3 = new LambdaQueryWrapper();
		var3.eq(OnlCgformButton::getButtonStatus, "1");
		var3.eq(OnlCgformButton::getCgformHeadId, code);
		if (isListButton) {
			var3.in(OnlCgformButton::getButtonStyle, new Object[]{"link", "button"});
		} else {
			var3.eq(OnlCgformButton::getButtonStyle, "form");
		}

		var3.orderByAsc(OnlCgformButton::getOrderNum);
		return this.onlCgformButtonMapper.selectList(var3);
	}

	public List<OnlCgformButton> queryButtonList(String code) {
		LambdaQueryWrapper<OnlCgformButton> var2 = new LambdaQueryWrapper();
		var2.eq(OnlCgformButton::getButtonStatus, "1");
		var2.eq(OnlCgformButton::getCgformHeadId, code);
		var2.orderByAsc(OnlCgformButton::getOrderNum);
		return this.onlCgformButtonMapper.selectList(var2);
	}

	public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		LambdaQueryWrapper<OnlCgformEnhanceJava> var2 = new LambdaQueryWrapper();
		var2.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
		var2.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
		var2.eq(OnlCgformEnhanceJava::getCgJavaType, onlCgformEnhanceJava.getCgJavaType());
		var2.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
		Integer var3 = this.onlCgformEnhanceJavaMapper.selectCount(var2);
		if (var3 != null) {
			if (var3 == 1 && oConvertUtils.isEmpty(onlCgformEnhanceJava.getId())) {
				return false;
			}

			if (var3 == 2) {
				return false;
			}
		}

		return true;
	}

	public List<String> queryOnlinetables() {
		return ((OnlCgformHeadMapper) this.baseMapper).queryOnlinetables();
	}

	@Transactional(rollbackFor = {Exception.class})
	public void saveDbTable2Online(String tbname) {
		OnlCgformHead var2 = new OnlCgformHead();
		var2.setTableType(1);
		var2.setIsCheckbox("Y");
		var2.setIsDbSynch("Y");
		var2.setIsTree("N");
		var2.setIsPage("Y");
		var2.setQueryMode("group");
		var2.setTableName(tbname.toLowerCase());
		var2.setTableTxt(tbname);
		var2.setTableVersion(1);
		var2.setFormTemplate("1");
		String var3 = UUIDGenerator.generate();
		var2.setId(var3);
		ArrayList var4 = new ArrayList();

		try {
			List var5 = DbReadTableUtil.b(tbname);

			for (int var6 = 0; var6 < var5.size(); ++var6) {
				ColumnVo var7 = (ColumnVo) var5.get(var6);
				a.info("  columnt : " + var7.toString());
				String var8 = var7.getFieldDbName();
				OnlCgformField var9 = new OnlCgformField();
				var9.setCgformHeadId(var3);
				var9.setDbFieldNameOld(var7.getFieldDbName().toLowerCase());
				var9.setDbFieldName(var7.getFieldDbName().toLowerCase());
				if (oConvertUtils.isNotEmpty(var7.getFiledComment())) {
					var9.setDbFieldTxt(var7.getFiledComment());
				} else {
					var9.setDbFieldTxt(var7.getFieldName());
				}

				var9.setDbIsKey(0);
				var9.setIsShowForm(1);
				var9.setIsQuery(0);
				var9.setFieldMustInput("0");
				var9.setIsShowList(1);
				var9.setOrderNum(var6 + 1);
				var9.setQueryMode("group");
				var9.setDbLength(oConvertUtils.getInt(var7.getPrecision()));
				var9.setFieldLength(120);
				var9.setDbPointLength(oConvertUtils.getInt(var7.getScale()));
				var9.setFieldShowType("text");
				var9.setDbIsNull("Y".equals(var7.getNullable()) ? 1 : 0);
				if ("id".equalsIgnoreCase(var8)) {
					String[] var10 = new String[]{"java.lang.Integer", "java.lang.Long"};
					String var11 = var7.getFieldType();
					if (Arrays.asList(var10).contains(var11)) {
						var2.setIdType("NATIVE");
					} else {
						var2.setIdType("UUID");
					}

					var9.setDbIsKey(1);
					var9.setIsShowForm(0);
					var9.setIsShowList(0);
				}

				if ("java.lang.Integer".equalsIgnoreCase(var7.getFieldType())) {
					var9.setDbType("int");
				} else if ("java.lang.Long".equalsIgnoreCase(var7.getFieldType())) {
					var9.setDbType("int");
				} else if ("java.util.Date".equalsIgnoreCase(var7.getFieldType())) {
					var9.setDbType("Date");
					var9.setFieldShowType("date");
				} else if (!"java.lang.Double".equalsIgnoreCase(var7.getFieldType())
						&& !"java.lang.Float".equalsIgnoreCase(var7.getFieldType())) {
					if (!"java.math.BigDecimal".equalsIgnoreCase(var7.getFieldType())
							&& !"BigDecimal".equalsIgnoreCase(var7.getFieldType())) {
						if (!"byte[]".equalsIgnoreCase(var7.getFieldType()) && !var7.getFieldType().contains("blob")) {
							var9.setDbType("string");
						} else {
							var9.setDbType("Blob");
							var7.setCharmaxLength((String) null);
						}
					} else {
						var9.setDbType("BigDecimal");
					}
				} else {
					var9.setDbType("double");
				}

				if (oConvertUtils.isEmpty(var7.getPrecision()) && oConvertUtils.isNotEmpty(var7.getCharmaxLength())) {
					if (Long.valueOf(var7.getCharmaxLength()) >= 3000L) {
						var9.setDbType("Text");
						var9.setFieldShowType("textarea");

						try {
							var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
						} catch (Exception var12) {
							a.error(var12.getMessage(), var12);
						}
					} else {
						var9.setDbLength(Integer.valueOf(var7.getCharmaxLength()));
					}
				} else {
					if (oConvertUtils.isNotEmpty(var7.getPrecision())) {
						var9.setDbLength(Integer.valueOf(var7.getPrecision()));
					} else if (var9.getDbType().equals("int")) {
						var9.setDbLength(10);
					}

					if (oConvertUtils.isNotEmpty(var7.getScale())) {
						var9.setDbPointLength(Integer.valueOf(var7.getScale()));
					}
				}

				var4.add(var9);
			}
		} catch (Exception var13) {
			a.error(var13.getMessage(), var13);
		}

		if (oConvertUtils.isEmpty(var2.getFormCategory())) {
			var2.setFormCategory("bdfl_include");
		}

		this.save(var2);
		this.fieldService.saveBatch(var4);
	}

	private void a(OnlCgformHead var1, List<OnlCgformField> var2) {
		if (var1.getTableType() == 3) {
			var1 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectById(var1.getId());

			for (int var3 = 0; var3 < var2.size(); ++var3) {
				OnlCgformField var4 = (OnlCgformField) var2.get(var3);
				String var5 = var4.getMainTable();
				if (!oConvertUtils.isEmpty(var5)) {
					OnlCgformHead var6 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
							.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var5));
					if (var6 != null) {
						String var7 = var6.getSubTableStr();
						if (oConvertUtils.isEmpty(var7)) {
							var7 = var1.getTableName();
						} else if (var7.indexOf(var1.getTableName()) < 0) {
							ArrayList var8 = new ArrayList(Arrays.asList(var7.split(",")));

							for (int var9 = 0; var9 < var8.size(); ++var9) {
								String var10 = (String) var8.get(var9);
								OnlCgformHead var11 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(
										(Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var10));
								if (var11 != null && var1.getTabOrderNum() < var11.getTabOrderNum()) {
									var8.add(var9, var1.getTableName());
									break;
								}
							}

							if (var8.indexOf(var1.getTableName()) < 0) {
								var8.add(var1.getTableName());
							}

							var7 = String.join(",", var8);
						}

						var6.setSubTableStr(var7);
						((OnlCgformHeadMapper) this.baseMapper).updateById(var6);
						break;
					}
				}
			}
		}

	}

	@Transactional(rollbackFor = {Exception.class})
	public void saveManyFormData(String code, JSONObject json) throws DBException, BusinessException {
		OnlCgformHead var3 = (OnlCgformHead) this.getById(code);
		if (var3 == null) {
			throw new DBException("数据库主表ID[" + code + "]不存在");
		} else {
			String var4 = "add";
			this.executeEnhanceJava(var4, "start", var3, json);
			json.put("id", UUIDGenerator.generate());
			String var5 = var3.getTableName();
			if (var3.getTableType() == 2) {
				String var6 = var3.getSubTableStr();
				String[] var7 = var6.split(",");
				String[] var8 = var7;
				int var9 = var7.length;

				for (int var10 = 0; var10 < var9; ++var10) {
					String var11 = var8[var10];
					JSONArray var12 = json.getJSONArray(var11);
					if (var12 != null && var12.size() != 0) {
						OnlCgformHead var13 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
								.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var11));
						if (var13 != null) {
							List var14 = this.fieldService.list((Wrapper) (new LambdaQueryWrapper<OnlCgformField>())
									.eq(OnlCgformField::getCgformHeadId, var13.getId()));
							String var15 = "";
							String var16 = null;
							Iterator var17 = var14.iterator();

							while (var17.hasNext()) {
								OnlCgformField var18 = (OnlCgformField) var17.next();
								if (!oConvertUtils.isEmpty(var18.getMainField())) {
									var15 = var18.getDbFieldName();
									String var19 = var18.getMainField();
									var16 = json.getString(var19);
								}
							}

							for (int var20 = 0; var20 < var12.size(); ++var20) {
								JSONObject var21 = var12.getJSONObject(var20);
								if (var16 != null) {
									var21.put(var15, var16);
								}

								this.fieldService.saveFormData(var14, var11, var21);
							}
						}
					}
				}
			}

			if ("Y".equals(var3.getIsTree())) {
				this.fieldService.saveTreeFormData(code, var5, json, var3.getTreeIdField(),
						var3.getTreeParentIdField());
			} else {
				this.fieldService.saveFormData(code, var5, json, false);
			}

			this.executeEnhanceSql(var4, code, json);
			this.executeEnhanceJava(var4, "end", var3, json);
		}
	}

	public Map<String, Object> querySubFormData(String table, String mainId) throws DBException {
		new HashMap();
		OnlCgformHead var4 = (OnlCgformHead) this
				.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, table));
		if (var4 == null) {
			throw new DBException("数据库子表[" + table + "]不存在");
		} else {
			List var5 = this.fieldService.queryFormFields(var4.getId(), false);
			String var6 = null;
			Iterator var7 = var5.iterator();

			while (var7.hasNext()) {
				OnlCgformField var8 = (OnlCgformField) var7.next();
				if (oConvertUtils.isNotEmpty(var8.getMainField())) {
					var6 = var8.getDbFieldName();
					break;
				}
			}

			List var9 = this.fieldService.querySubFormData(var5, table, var6, mainId);
			if (var9 != null && var9.size() == 0) {
				throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
			} else if (var9.size() > 1) {
				throw new DBException("数据库子表[" + table + "]存在多条记录,主表ID为" + mainId);
			} else {
				Map var3 = (Map) var9.get(0);
				return var3;
			}
		}
	}

	public List<Map<String, Object>> queryManySubFormData(String table, String mainId) throws DBException {
		OnlCgformHead var3 = (OnlCgformHead) this
				.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, table));
		if (var3 == null) {
			throw new DBException("数据库子表[" + table + "]不存在");
		} else {
			List var4 = this.fieldService.queryFormFields(var3.getId(), false);
			String var5 = null;
			Iterator var6 = var4.iterator();

			while (var6.hasNext()) {
				OnlCgformField var7 = (OnlCgformField) var6.next();
				if (oConvertUtils.isNotEmpty(var7.getMainField())) {
					var5 = var7.getDbFieldName();
					break;
				}
			}

			List var10 = this.fieldService.querySubFormData(var4, table, var5, mainId);
			if (var10 != null && var10.size() == 0) {
				throw new DBException("数据库子表[" + table + "]未找到相关信息,主表ID为" + mainId);
			} else {
				ArrayList var11 = new ArrayList(var10.size());
				Iterator var8 = var10.iterator();

				while (var8.hasNext()) {
					Map var9 = (Map) var8.next();
					var11.add(com.huizhixin.smart.modules.online.cgform.b.b.a(var9));
				}

				return var11;
			}
		}
	}

	public Map<String, Object> queryManyFormData(String code, String id) throws DBException {
		OnlCgformHead var3 = (OnlCgformHead) this.getById(code);
		if (var3 == null) {
			throw new DBException("数据库主表ID[" + code + "]不存在");
		} else {
			List var4 = this.fieldService.queryFormFields(code, true);
			Map var5 = this.fieldService.queryFormData(var4, var3.getTableName(), id);
			if (var3.getTableType() == 2) {
				String var6 = var3.getSubTableStr();
				String[] var7 = var6.split(",");
				String[] var8 = var7;
				int var9 = var7.length;

				for (int var10 = 0; var10 < var9; ++var10) {
					String var11 = var8[var10];
					OnlCgformHead var12 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
							.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var11));
					if (var12 != null) {
						List var13 = this.fieldService.queryFormFields(var12.getId(), false);
						String var14 = "";
						String var15 = null;
						Iterator var16 = var13.iterator();

						while (var16.hasNext()) {
							OnlCgformField var17 = (OnlCgformField) var16.next();
							if (!oConvertUtils.isEmpty(var17.getMainField())) {
								var14 = var17.getDbFieldName();
								String var18 = var17.getMainField();
								var15 = var5.get(var18).toString();
							}
						}

						List var19 = this.fieldService.querySubFormData(var13, var11, var14, var15);
						if (var19 != null && var19.size() != 0) {
							var5.put(var11, var19);
						} else {
							var5.put(var11, new String[0]);
						}
					}
				}
			}

			return var5;
		}
	}

	@Transactional(rollbackFor = {Exception.class})
	public void editManyFormData(String code, JSONObject json) throws DBException, BusinessException {
		OnlCgformHead var3 = (OnlCgformHead) this.getById(code);
		if (var3 == null) {
			throw new DBException("数据库主表ID[" + code + "]不存在");
		} else {
			String var4 = "edit";
			this.executeEnhanceJava(var4, "start", var3, json);
			String var5 = var3.getTableName();
			if ("Y".equals(var3.getIsTree())) {
				this.fieldService.editTreeFormData(code, var5, json, var3.getTreeIdField(),
						var3.getTreeParentIdField());
			} else {
				this.fieldService.editFormData(code, var5, json, false);
			}

			if (var3.getTableType() == 2) {
				String var6 = var3.getSubTableStr();
				String[] var7 = var6.split(",");
				String[] var8 = var7;
				int var9 = var7.length;

				for (int var10 = 0; var10 < var9; ++var10) {
					String var11 = var8[var10];
					OnlCgformHead var12 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
							.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var11));
					if (var12 != null) {
						List var13 = this.fieldService.list((Wrapper) (new LambdaQueryWrapper<OnlCgformField>())
								.eq(OnlCgformField::getCgformHeadId, var12.getId()));
						String var14 = "";
						String var15 = null;
						Iterator var16 = var13.iterator();

						while (var16.hasNext()) {
							OnlCgformField var17 = (OnlCgformField) var16.next();
							if (!oConvertUtils.isEmpty(var17.getMainField())) {
								var14 = var17.getDbFieldName();
								String var18 = var17.getMainField();
								var15 = json.getString(var18);
							}
						}

						if (!oConvertUtils.isEmpty(var15)) {
							this.fieldService.deleteAutoList(var11, var14, var15);
							JSONArray var19 = json.getJSONArray(var11);
							if (var19 != null && var19.size() != 0) {
								for (int var20 = 0; var20 < var19.size(); ++var20) {
									JSONObject var21 = var19.getJSONObject(var20);
									if (var15 != null) {
										var21.put(var14, var15);
									}

									this.fieldService.saveFormData(var13, var11, var21);
								}
							}
						}
					}
				}
			}

			this.executeEnhanceJava(var4, "end", var3, json);
			this.executeEnhanceSql(var4, code, json);
		}
	}

	public void executeEnhanceJava(String buttonCode, String eventType, OnlCgformHead head, JSONObject json)
			throws BusinessException {
		LambdaQueryWrapper<OnlCgformEnhanceJava> var5 = new LambdaQueryWrapper();
		var5.eq(OnlCgformEnhanceJava::getActiveStatus, "1");
		var5.eq(OnlCgformEnhanceJava::getButtonCode, buttonCode);
		var5.eq(OnlCgformEnhanceJava::getCgformHeadId, head.getId());
		var5.eq(OnlCgformEnhanceJava::getEvent, eventType);
		OnlCgformEnhanceJava var6 = (OnlCgformEnhanceJava) this.onlCgformEnhanceJavaMapper.selectOne(var5);
		if (var6 != null) {
			String var7 = var6.getCgJavaType();
			String var8 = var6.getCgJavaValue();
			if (oConvertUtils.isNotEmpty(var8)) {
				Object var9 = null;
				if ("class".equals(var7)) {
					try {
						var9 = MyClassLoader.getClassByScn(var8).newInstance();
					} catch (InstantiationException var11) {
						a.error(var11.getMessage(), var11);
					} catch (IllegalAccessException var12) {
						a.error(var12.getMessage(), var12);
					}
				} else if ("spring".equals(var7)) {
					var9 = SpringContextUtils.getBean(var8);
				}

				if (var9 instanceof CgformEnhanceJavaInter) {
					CgformEnhanceJavaInter var10 = (CgformEnhanceJavaInter) var9;
					var10.execute(head.getTableName(), json);
				}
			}
		}

	}

	public void executeEnhanceSql(String buttonCode, String formId, JSONObject json) {
		LambdaQueryWrapper<OnlCgformEnhanceSql> var4 = new LambdaQueryWrapper();
		var4.eq(OnlCgformEnhanceSql::getButtonCode, buttonCode);
		var4.eq(OnlCgformEnhanceSql::getCgformHeadId, formId);
		OnlCgformEnhanceSql var5 = (OnlCgformEnhanceSql) this.onlCgformEnhanceSqlMapper.selectOne(var4);
		if (var5 != null) {
			String var6 = com.huizhixin.smart.modules.online.cgform.b.b.a(var5.getCgbSql(), json);
			String[] var7 = var6.split(";");
			String[] var8 = var7;
			int var9 = var7.length;

			for (int var10 = 0; var10 < var9; ++var10) {
				String var11 = var8[var10];
				if (var11 != null && !var11.toLowerCase().trim().equals("")) {
					((OnlCgformHeadMapper) this.baseMapper).executeDDL(var11);
				}
			}
		}

	}

	public void executeCustomerButton(String buttonCode, String formId, String dataId) throws BusinessException {
		OnlCgformHead var4 = (OnlCgformHead) this.getById(formId);
		if (var4 == null) {
			throw new BusinessException("未找到表配置信息");
		} else {
			Map var5 = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(var4.getTableName(), dataId);
			JSONObject var6 = JSONObject.parseObject(JSON.toJSONString(var5));
			this.executeEnhanceJava(buttonCode, "start", var4, var6);
			this.executeEnhanceSql(buttonCode, formId, var6);
			this.executeEnhanceJava(buttonCode, "end", var4, var6);
		}
	}

	public List<OnlCgformButton> queryValidButtonList(String headId) {
		LambdaQueryWrapper<OnlCgformButton> var2 = new LambdaQueryWrapper();
		var2.eq(OnlCgformButton::getCgformHeadId, headId);
		var2.eq(OnlCgformButton::getButtonStatus, "1");
		var2.orderByAsc(OnlCgformButton::getOrderNum);
		return this.onlCgformButtonMapper.selectList(var2);
	}

	public OnlCgformEnhanceJs queryEnhanceJs(String formId, String cgJsType) {
		LambdaQueryWrapper<OnlCgformEnhanceJs> var3 = new LambdaQueryWrapper();
		var3.eq(OnlCgformEnhanceJs::getCgformHeadId, formId);
		var3.eq(OnlCgformEnhanceJs::getCgJsType, cgJsType);
		return (OnlCgformEnhanceJs) this.onlCgformEnhanceJsMapper.selectOne(var3);
	}

	public void deleteOneTableInfo(String formId, String dataId) throws BusinessException {
		OnlCgformHead var3 = (OnlCgformHead) this.getById(formId);
		if (var3 == null) {
			throw new BusinessException("未找到表配置信息");
		} else {
			Map var4 = ((OnlCgformHeadMapper) this.baseMapper).queryOneByTableNameAndId(var3.getTableName(), dataId);
			if (var4 == null) {
				throw new BusinessException("未找到数据信息");
			} else {
				String var5 = "delete";
				JSONObject var6 = JSONObject.parseObject(JSON.toJSONString(var4));
				this.executeEnhanceJava(var5, "start", var3, var6);
				String var7 = "delete from " + var3.getTableName() + " where id = '" + dataId + "'";
				((OnlCgformHeadMapper) this.baseMapper).deleteOne(var7);
				this.executeEnhanceSql(var5, formId, var6);
				this.executeEnhanceJava(var5, "end", var3, var6);
			}
		}
	}

	public JSONObject queryFormItem(OnlCgformHead head) {
		List var2 = this.fieldService.queryAvailableFields(head.getId(), head.getTableName(), false);
		List var3 = this.fieldService.queryDisabledFields(head.getTableName());
		JSONObject var4 = com.huizhixin.smart.modules.online.cgform.b.b.a(var2, var3,
				(com.huizhixin.smart.modules.online.cgform.model.b) null);
		if (head.getTableType() == 2) {
			String var5 = head.getSubTableStr();
			if (oConvertUtils.isNotEmpty(var5)) {
				String[] var6 = var5.split(",");
				int var7 = var6.length;

				for (int var8 = 0; var8 < var7; ++var8) {
					String var9 = var6[var8];
					OnlCgformHead var10 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
							.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var9));
					if (var10 != null) {
						List var11 = this.fieldService.queryAvailableFields(var10.getId(), var10.getTableName(), false);
						JSONObject var12 = new JSONObject();
						if (1 == var10.getRelationType()) {
							var12 = com.huizhixin.smart.modules.online.cgform.b.b.a(var11, (List) null,
									(com.huizhixin.smart.modules.online.cgform.model.b) null);
						} else {
							var12.put("columns", com.huizhixin.smart.modules.online.cgform.b.b.c(var11));
						}

						var12.put("relationType", var10.getRelationType());
						var12.put("view", "tab");
						var12.put("describe", var10.getTableTxt());
						var12.put("key", var10.getTableName());
						var4.getJSONObject("properties").put(var10.getTableName(), var12);
					}
				}
			}
		}

		return var4;
	}

	public void generateCode(OnlGenerateModel model) throws Exception {
		TableVo var2 = new TableVo();
		var2.setEntityName(model.getEntityName());
		var2.setEntityPackage(model.getEntityPackage());
		var2.setFtlDescription(model.getFtlDescription());
		var2.setTableName(model.getTableName());
		var2.setSearchFieldNum(-1);
		ArrayList var3 = new ArrayList();
		ArrayList var4 = new ArrayList();
		this.a(model.getCode(), var3, var4);
		OnlCgformHead var5 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
				.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getId, model.getCode()));
		if ("Y".equals(var5.getIsTree())) {
			HashMap var6 = new HashMap();
			var6.put("pidField", var5.getTreeParentIdField());
			var6.put("hasChildren", var5.getTreeIdField());
			var6.put("textField", var5.getTreeFieldname());
			var2.setExtendParams(var6);
		}

		String var7 = CgformEnum.getTemplatePathByConfig(var5.getTableType(), var5.getIsTree());
		(new CodeGenerateOne(var2, var3, var4)).generateCodeFile(model.getProjectPath(), var7);
	}

	public void generateOneToMany(OnlGenerateModel model) throws Exception {
		MainTableVo var2 = new MainTableVo();
		var2.setEntityName(model.getEntityName());
		var2.setEntityPackage(model.getEntityPackage());
		var2.setFtlDescription(model.getFtlDescription());
		var2.setTableName(model.getTableName());
		ArrayList var3 = new ArrayList();
		ArrayList var4 = new ArrayList();
		this.a(model.getCode(), var3, var4);
		List var5 = model.getSubList();
		ArrayList var6 = new ArrayList();
		Iterator var7 = var5.iterator();

		while (var7.hasNext()) {
			OnlGenerateModel var8 = (OnlGenerateModel) var7.next();
			OnlCgformHead var9 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(
					(Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var8.getTableName()));
			if (var9 != null) {
				SubTableVo var10 = new SubTableVo();
				var10.setEntityName(var8.getEntityName());
				var10.setEntityPackage(model.getEntityPackage());
				var10.setTableName(var8.getTableName());
				var10.setFtlDescription(var8.getFtlDescription());
				Integer var11 = var9.getRelationType();
				var10.setForeignRelationType(var11 == 1 ? "1" : "0");
				ArrayList var12 = new ArrayList();
				ArrayList var13 = new ArrayList();
				OnlCgformField var14 = this.a(var9.getId(), var12, var13);
				if (var14 != null) {
					var10.setOriginalForeignKeys(new String[]{var14.getDbFieldName()});
					var10.setForeignKeys(new String[]{var14.getDbFieldName()});
					var10.setColums(var12);
					var10.setOriginalColumns(var13);
					var6.add(var10);
				}
			}
		}

		String var15 = CgformEnum.MANY.getTemplatePath();
		(new CodeGenerateOneToMany(var2, var3, var4, var6)).generateCodeFile(model.getProjectPath(), var15);
	}

	private OnlCgformField a(String var1, List<ColumnVo> var2, List<ColumnVo> var3) {
		LambdaQueryWrapper<OnlCgformField> var4 = new LambdaQueryWrapper();
		var4.eq(OnlCgformField::getCgformHeadId, var1);
		var4.orderByAsc(OnlCgformField::getOrderNum);
		List var5 = this.fieldService.list(var4);
		OnlCgformField var6 = null;
		Iterator var7 = var5.iterator();

		while (true) {
			OnlCgformField var8;
			ColumnVo var9;
			do {
				if (!var7.hasNext()) {
					return var6;
				}

				var8 = (OnlCgformField) var7.next();
				if (oConvertUtils.isNotEmpty(var8.getMainTable())) {
					var6 = var8;
				}

				var9 = new ColumnVo();
				var9.setFieldLength(var8.getFieldLength());
				var9.setFieldHref(var8.getFieldHref());
				var9.setFieldValidType(var8.getFieldValidType());
				var9.setFieldDefault(var8.getDbDefaultVal());
				var9.setFieldShowType(var8.getFieldShowType());
				var9.setFieldOrderNum(var8.getOrderNum());
				var9.setIsKey(var8.getDbIsKey() == 1 ? "Y" : "N");
				var9.setIsShow(var8.getIsShowForm() == 1 ? "Y" : "N");
				var9.setIsShowList(var8.getIsShowList() == 1 ? "Y" : "N");
				var9.setIsQuery(var8.getIsQuery() == 1 ? "Y" : "N");
				var9.setQueryMode(var8.getQueryMode());
				var9.setDictField(var8.getDictField());
				var9.setDictTable(var8.getDictTable());
				var9.setDictText(var8.getDictText());
				var9.setFieldDbName(var8.getDbFieldName());
				var9.setFieldName(oConvertUtils.camelName(var8.getDbFieldName()));
				var9.setFiledComment(var8.getDbFieldTxt());
				var9.setFieldDbType(var8.getDbType());
				var9.setFieldType(this.a(var8.getDbType()));
				var9.setClassType(var8.getFieldShowType());
				var9.setClassType_row(var8.getFieldShowType());
				var9.setNullable(var8.getDbIsNull() == 1 ? "Y" : "N");
				var3.add(var9);
			} while (var8.getIsShowForm() != 1 && var8.getIsShowList() != 1);

			var2.add(var9);
		}
	}

	private String a(String var1) {
		var1 = var1.toLowerCase();
		if (var1.indexOf("int") >= 0) {
			return "java.lang.Integer";
		} else if (var1.indexOf("double") >= 0) {
			return "java.lang.Double";
		} else if (var1.indexOf("decimal") >= 0) {
			return "java.math.BigDecimal";
		} else {
			return var1.indexOf("date") >= 0 ? "java.util.Date" : "java.lang.String";
		}
	}

	public void addCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
		OnlCgformHead var3 = (OnlCgformHead) this
				.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, tbname));
		if (var3 == null) {
			throw new DBException("数据库主表[" + tbname + "]不存在");
		} else {
			if (var3.getTableType() == 2) {
				String var4 = var3.getSubTableStr();
				String[] var5 = var4.split(",");
				String[] var6 = var5;
				int var7 = var5.length;

				for (int var8 = 0; var8 < var7; ++var8) {
					String var9 = var6[var8];
					String var10 = json.getString("sub-table-design_" + var9);
					if (!oConvertUtils.isEmpty(var10)) {
						JSONArray var11 = JSONArray.parseArray(URLDecoder.decode(var10, "UTF-8"));
						if (var11 != null && var11.size() != 0) {
							OnlCgformHead var12 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper).selectOne(
									(Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var9));
							if (var12 != null) {
								List var13 = this.fieldService.list((Wrapper) (new LambdaQueryWrapper<OnlCgformField>())
										.eq(OnlCgformField::getCgformHeadId, var12.getId()));
								String var14 = "";
								String var15 = null;
								Iterator var16 = var13.iterator();

								while (var16.hasNext()) {
									OnlCgformField var17 = (OnlCgformField) var16.next();
									if (!oConvertUtils.isEmpty(var17.getMainField())) {
										var14 = var17.getDbFieldName();
										String var18 = var17.getMainField();
										var15 = json.getString(var18);
									}
								}

								for (int var19 = 0; var19 < var11.size(); ++var19) {
									JSONObject var20 = var11.getJSONObject(var19);
									if (var15 != null) {
										var20.put(var14, var15);
									}

									this.fieldService.executeInsertSQL(
											com.huizhixin.smart.modules.online.cgform.b.b.c(var9, var13, var20));
								}
							}
						}
					}
				}
			}

			this.fieldService.saveFormData(var3.getId(), tbname, json, true);
		}
	}

	public void editCrazyFormData(String tbname, JSONObject json) throws DBException, UnsupportedEncodingException {
		OnlCgformHead var3 = (OnlCgformHead) this
				.getOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, tbname));
		if (var3 == null) {
			throw new DBException("数据库主表[" + tbname + "]不存在");
		} else {
			if (var3.getTableType() == 2) {
				String var4 = var3.getSubTableStr();
				String[] var5 = var4.split(",");
				String[] var6 = var5;
				int var7 = var5.length;

				for (int var8 = 0; var8 < var7; ++var8) {
					String var9 = var6[var8];
					OnlCgformHead var10 = (OnlCgformHead) ((OnlCgformHeadMapper) this.baseMapper)
							.selectOne((Wrapper) (new LambdaQueryWrapper<OnlCgformHead>()).eq(OnlCgformHead::getTableName, var9));
					if (var10 != null) {
						List var11 = this.fieldService.list((Wrapper) (new LambdaQueryWrapper<OnlCgformField>())
								.eq(OnlCgformField::getCgformHeadId, var10.getId()));
						String var12 = "";
						String var13 = null;
						Iterator var14 = var11.iterator();

						while (var14.hasNext()) {
							OnlCgformField var15 = (OnlCgformField) var14.next();
							if (!oConvertUtils.isEmpty(var15.getMainField())) {
								var12 = var15.getDbFieldName();
								String var16 = var15.getMainField();
								var13 = json.getString(var16);
							}
						}

						if (!oConvertUtils.isEmpty(var13)) {
							this.fieldService.deleteAutoList(var9, var12, var13);
							String var18 = json.getString("sub-table-design_" + var9);
							if (!oConvertUtils.isEmpty(var18)) {
								JSONArray var19 = JSONArray.parseArray(URLDecoder.decode(var18, "UTF-8"));
								if (var19 != null && var19.size() != 0) {
									for (int var20 = 0; var20 < var19.size(); ++var20) {
										JSONObject var17 = var19.getJSONObject(var20);
										if (var13 != null) {
											var17.put(var12, var13);
										}

										this.fieldService.executeInsertSQL(
												com.huizhixin.smart.modules.online.cgform.b.b.c(var9, var11, var17));
									}
								}
							}
						}
					}
				}
			}

			this.fieldService.editFormData(var3.getId(), tbname, json, true);
		}
	}
}