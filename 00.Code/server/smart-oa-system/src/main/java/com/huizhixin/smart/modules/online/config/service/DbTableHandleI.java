package com.huizhixin.smart.modules.online.config.service;

import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.online.config.util.a;

public interface DbTableHandleI {
	String getAddColumnSql(a var1);

	String getReNameFieldName(a var1);

	String getUpdateColumnSql(a var1, a var2) throws DBException;

	String getMatchClassTypeByDataType(String var1, int var2);

	String dropTableSQL(String var1);

	String getDropColumnSql(String var1);

	String getCommentSql(a var1);

	String getSpecialHandle(a var1, a var2);

	String dropIndexs(String var1, String var2);
}