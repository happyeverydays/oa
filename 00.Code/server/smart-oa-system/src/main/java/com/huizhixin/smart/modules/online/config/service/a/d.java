package com.huizhixin.smart.modules.online.config.service.a;

import com.huizhixin.smart.common.util.oConvertUtils;
import com.huizhixin.smart.modules.online.config.service.DbTableHandleI;
import com.huizhixin.smart.modules.online.config.util.a;

public class d implements DbTableHandleI {
	public String getAddColumnSql(a columnMeta) {
		return " ADD  " + this.a(columnMeta) + ";";
	}

	public String getReNameFieldName(a columnMeta) {
		return "  sp_rename '" + columnMeta.getTableName() + "." + columnMeta.getOldColumnName() + "', '"
				+ columnMeta.getColumnName() + "', 'COLUMN';";
	}

	public String getUpdateColumnSql(a cgformcolumnMeta, a datacolumnMeta) {
		return " ALTER COLUMN  " + this.a(cgformcolumnMeta, datacolumnMeta) + ";";
	}

	public String getMatchClassTypeByDataType(String dataType, int digits) {
		String var3 = "";
		if (dataType.equalsIgnoreCase("varchar")) {
			var3 = "string";
		} else if (dataType.equalsIgnoreCase("float")) {
			var3 = "double";
		} else if (dataType.equalsIgnoreCase("int")) {
			var3 = "int";
		} else if (dataType.equalsIgnoreCase("Date")) {
			var3 = "date";
		} else if (dataType.equalsIgnoreCase("Datetime")) {
			var3 = "date";
		} else if (dataType.equalsIgnoreCase("numeric")) {
			var3 = "bigdecimal";
		} else if (dataType.equalsIgnoreCase("varbinary")) {
			var3 = "blob";
		}

		return var3;
	}

	public String dropTableSQL(String tableName) {
		return " DROP TABLE " + tableName + " ;";
	}

	public String getDropColumnSql(String fieldName) {
		return " DROP COLUMN " + fieldName + ";";
	}

	private String a(a var1, a var2) {
		String var3 = "";
		if (var1.getColunmType().equalsIgnoreCase("string")) {
			var3 = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var3 = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			var3 = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("double")) {
			var3 = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			var3 = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("text")) {
			var3 = var1.getColumnName() + " text " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			var3 = var1.getColumnName() + " varbinary(" + var1.getColumnSize() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		}

		return var3;
	}

	private String a(a var1) {
		String var2 = "";
		if (var1.getColunmType().equalsIgnoreCase("string")) {
			var2 = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var2 = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			var2 = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("double")) {
			var2 = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			var2 = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("text")) {
			var2 = var1.getColumnName() + " text " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			var2 = var1.getColumnName() + " varbinary(" + var1.getColumnSize() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		}

		return var2;
	}

	private String b(a var1) {
		String var2 = "";
		if (var1.getColunmType().equalsIgnoreCase("string")) {
			var2 = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") "
					+ ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var2 = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			var2 = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("double")) {
			var2 = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		}

		return var2;
	}

	public String getCommentSql(a columnMeta) {
		StringBuffer var2 = new StringBuffer("EXECUTE ");
		if (oConvertUtils.isEmpty(columnMeta.getOldColumnName())) {
			var2.append("sp_addextendedproperty");
		} else {
			var2.append("sp_updateextendedproperty");
		}

		var2.append(" N'MS_Description', '");
		var2.append(columnMeta.getComment());
		var2.append("', N'SCHEMA', N'dbo', N'TABLE', N'");
		var2.append(columnMeta.getTableName());
		var2.append("', N'COLUMN', N'");
		var2.append(columnMeta.getColumnName() + "'");
		return var2.toString();
	}

	public String getSpecialHandle(a cgformcolumnMeta, a datacolumnMeta) {
		return null;
	}

	public String dropIndexs(String indexName, String tableName) {
		return "DROP INDEX " + indexName + " ON " + tableName;
	}
}