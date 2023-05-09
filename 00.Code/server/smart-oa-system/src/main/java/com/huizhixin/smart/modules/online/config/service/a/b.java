package com.huizhixin.smart.modules.online.config.service.a;

import org.apache.commons.lang.StringUtils;
import com.huizhixin.smart.modules.online.config.service.DbTableHandleI;
import com.huizhixin.smart.modules.online.config.util.a;

public class b implements DbTableHandleI {
	public String getAddColumnSql(a columnMeta) {
		return " ADD  " + this.a(columnMeta) + "";
	}

	public String getReNameFieldName(a columnMeta) {
		return "RENAME COLUMN  " + columnMeta.getOldColumnName() + " TO " + columnMeta.getColumnName() + "";
	}

	public String getUpdateColumnSql(a cgformcolumnMeta, a datacolumnMeta) {
		return " MODIFY   " + this.a(cgformcolumnMeta, datacolumnMeta) + "";
	}

	public String getMatchClassTypeByDataType(String dataType, int digits) {
		String var3 = "";
		if (dataType.equalsIgnoreCase("varchar2")) {
			var3 = "string";
		}

		if (dataType.equalsIgnoreCase("nvarchar2")) {
			var3 = "string";
		} else if (dataType.equalsIgnoreCase("double")) {
			var3 = "double";
		} else if (dataType.equalsIgnoreCase("number") && digits == 0) {
			var3 = "int";
		} else if (dataType.equalsIgnoreCase("number") && digits != 0) {
			var3 = "double";
		} else if (dataType.equalsIgnoreCase("int")) {
			var3 = "int";
		} else if (dataType.equalsIgnoreCase("Date")) {
			var3 = "date";
		} else if (dataType.equalsIgnoreCase("Datetime")) {
			var3 = "date";
		} else if (dataType.equalsIgnoreCase("blob")) {
			var3 = "blob";
		} else if (dataType.equalsIgnoreCase("clob")) {
			var3 = "text";
		}

		return var3;
	}

	public String dropTableSQL(String tableName) {
		return " DROP TABLE  " + tableName.toLowerCase() + " ";
	}

	public String getDropColumnSql(String fieldName) {
		return " DROP COLUMN " + fieldName.toUpperCase() + "";
	}

	private String a(a var1) {
		String var2 = "";
		if (var1.getColunmType().equalsIgnoreCase("string")) {
			var2 = var1.getColumnName() + " varchar2(" + var1.getColumnSize() + ")";
		} else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var2 = var1.getColumnName() + " datetime";
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			var2 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + ")";
		} else if (var1.getColunmType().equalsIgnoreCase("double")) {
			var2 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ")";
		} else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			var2 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ")";
		} else if (var1.getColunmType().equalsIgnoreCase("text")) {
			var2 = var1.getColumnName() + " CLOB ";
		} else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			var2 = var1.getColumnName() + " BLOB ";
		}

		var2 = var2 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " DEFAULT " + var1.getFieldDefault() : " ");
		var2 = var2 + ("Y".equals(var1.getIsNullable()) ? " NULL" : " NOT NULL");
		return var2;
	}

	private String a(a var1, a var2) {
		String var3 = "";
		String var4 = "";
		if (!var2.getIsNullable().equals(var1.getIsNullable())) {
			var4 = var1.getIsNullable().equals("Y") ? "NULL" : "NOT NULL";
		}

		if (var1.getColunmType().equalsIgnoreCase("string")) {
			var3 = var1.getColumnName() + " varchar2(" + var1.getColumnSize() + ")" + var4;
		} else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var3 = var1.getColumnName() + " date " + var4;
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			var3 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + ") " + var4;
		} else if (var1.getColunmType().equalsIgnoreCase("double")) {
			var3 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") "
					+ var4;
		} else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			var3 = var1.getColumnName() + " NUMBER(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") "
					+ var4;
		} else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			var3 = var1.getColumnName() + " BLOB " + var4;
		} else if (var1.getColunmType().equalsIgnoreCase("text")) {
			var3 = var1.getColumnName() + " CLOB " + var4;
		}

		var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " DEFAULT " + var1.getFieldDefault() : " ");
		var3 = var3 + var4;
		return var3;
	}

	public String getCommentSql(a columnMeta) {
		return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '"
				+ columnMeta.getComment() + "'";
	}

	public String getSpecialHandle(a cgformcolumnMeta, a datacolumnMeta) {
		return null;
	}

	public String dropIndexs(String indexName, String tableName) {
		return "DROP INDEX " + indexName;
	}
}