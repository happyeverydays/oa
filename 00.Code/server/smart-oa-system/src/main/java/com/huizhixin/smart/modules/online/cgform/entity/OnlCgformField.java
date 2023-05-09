package com.huizhixin.smart.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("onl_cgform_field")
public class OnlCgformField implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.UUID)
	private String id;
	private String cgformHeadId;
	private String dbFieldName;
	private String dbFieldTxt;
	private String dbFieldNameOld;
	private Integer dbIsKey;
	private Integer dbIsNull;
	private String dbType;
	private Integer dbLength;
	private Integer dbPointLength;
	private String dbDefaultVal;
	private String dictField;
	private String dictTable;
	private String dictText;
	private String fieldShowType;
	private String fieldHref;
	private Integer fieldLength;
	private String fieldValidType;
	private String fieldMustInput;
	private String fieldExtendJson;
	private String fieldValueRuleCode;
	private Integer isQuery;
	private Integer isShowForm;
	private Integer isShowList;
	private String queryMode;
	private String mainTable;
	private String mainField;
	private Integer orderNum;
	private String updateBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String createBy;

	

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof OnlCgformField)) {
			return false;
		} else {
			OnlCgformField var2 = (OnlCgformField) o;
			if (!var2.canEqual(this)) {
				return false;
			} else {
				label395 : {
					String var3 = this.getId();
					String var4 = var2.getId();
					if (var3 == null) {
						if (var4 == null) {
							break label395;
						}
					} else if (var3.equals(var4)) {
						break label395;
					}

					return false;
				}

				String var5 = this.getCgformHeadId();
				String var6 = var2.getCgformHeadId();
				if (var5 == null) {
					if (var6 != null) {
						return false;
					}
				} else if (!var5.equals(var6)) {
					return false;
				}

				String var7 = this.getDbFieldName();
				String var8 = var2.getDbFieldName();
				if (var7 == null) {
					if (var8 != null) {
						return false;
					}
				} else if (!var7.equals(var8)) {
					return false;
				}

				label374 : {
					String var9 = this.getDbFieldTxt();
					String var10 = var2.getDbFieldTxt();
					if (var9 == null) {
						if (var10 == null) {
							break label374;
						}
					} else if (var9.equals(var10)) {
						break label374;
					}

					return false;
				}

				label367 : {
					String var11 = this.getDbFieldNameOld();
					String var12 = var2.getDbFieldNameOld();
					if (var11 == null) {
						if (var12 == null) {
							break label367;
						}
					} else if (var11.equals(var12)) {
						break label367;
					}

					return false;
				}

				label360 : {
					Integer var13 = this.getDbIsKey();
					Integer var14 = var2.getDbIsKey();
					if (var13 == null) {
						if (var14 == null) {
							break label360;
						}
					} else if (var13.equals(var14)) {
						break label360;
					}

					return false;
				}

				Integer var15 = this.getDbIsNull();
				Integer var16 = var2.getDbIsNull();
				if (var15 == null) {
					if (var16 != null) {
						return false;
					}
				} else if (!var15.equals(var16)) {
					return false;
				}

				label346 : {
					String var17 = this.getDbType();
					String var18 = var2.getDbType();
					if (var17 == null) {
						if (var18 == null) {
							break label346;
						}
					} else if (var17.equals(var18)) {
						break label346;
					}

					return false;
				}

				Integer var19 = this.getDbLength();
				Integer var20 = var2.getDbLength();
				if (var19 == null) {
					if (var20 != null) {
						return false;
					}
				} else if (!var19.equals(var20)) {
					return false;
				}

				label332 : {
					Integer var21 = this.getDbPointLength();
					Integer var22 = var2.getDbPointLength();
					if (var21 == null) {
						if (var22 == null) {
							break label332;
						}
					} else if (var21.equals(var22)) {
						break label332;
					}

					return false;
				}

				String var23 = this.getDbDefaultVal();
				String var24 = var2.getDbDefaultVal();
				if (var23 == null) {
					if (var24 != null) {
						return false;
					}
				} else if (!var23.equals(var24)) {
					return false;
				}

				String var25 = this.getDictField();
				String var26 = var2.getDictField();
				if (var25 == null) {
					if (var26 != null) {
						return false;
					}
				} else if (!var25.equals(var26)) {
					return false;
				}

				label311 : {
					String var27 = this.getDictTable();
					String var28 = var2.getDictTable();
					if (var27 == null) {
						if (var28 == null) {
							break label311;
						}
					} else if (var27.equals(var28)) {
						break label311;
					}

					return false;
				}

				label304 : {
					String var29 = this.getDictText();
					String var30 = var2.getDictText();
					if (var29 == null) {
						if (var30 == null) {
							break label304;
						}
					} else if (var29.equals(var30)) {
						break label304;
					}

					return false;
				}

				String var31 = this.getFieldShowType();
				String var32 = var2.getFieldShowType();
				if (var31 == null) {
					if (var32 != null) {
						return false;
					}
				} else if (!var31.equals(var32)) {
					return false;
				}

				String var33 = this.getFieldHref();
				String var34 = var2.getFieldHref();
				if (var33 == null) {
					if (var34 != null) {
						return false;
					}
				} else if (!var33.equals(var34)) {
					return false;
				}

				label283 : {
					Integer var35 = this.getFieldLength();
					Integer var36 = var2.getFieldLength();
					if (var35 == null) {
						if (var36 == null) {
							break label283;
						}
					} else if (var35.equals(var36)) {
						break label283;
					}

					return false;
				}

				String var37 = this.getFieldValidType();
				String var38 = var2.getFieldValidType();
				if (var37 == null) {
					if (var38 != null) {
						return false;
					}
				} else if (!var37.equals(var38)) {
					return false;
				}

				String var39 = this.getFieldMustInput();
				String var40 = var2.getFieldMustInput();
				if (var39 == null) {
					if (var40 != null) {
						return false;
					}
				} else if (!var39.equals(var40)) {
					return false;
				}

				label262 : {
					String var41 = this.getFieldExtendJson();
					String var42 = var2.getFieldExtendJson();
					if (var41 == null) {
						if (var42 == null) {
							break label262;
						}
					} else if (var41.equals(var42)) {
						break label262;
					}

					return false;
				}

				label255 : {
					String var43 = this.getFieldValueRuleCode();
					String var44 = var2.getFieldValueRuleCode();
					if (var43 == null) {
						if (var44 == null) {
							break label255;
						}
					} else if (var43.equals(var44)) {
						break label255;
					}

					return false;
				}

				label248 : {
					Integer var45 = this.getIsQuery();
					Integer var46 = var2.getIsQuery();
					if (var45 == null) {
						if (var46 == null) {
							break label248;
						}
					} else if (var45.equals(var46)) {
						break label248;
					}

					return false;
				}

				Integer var47 = this.getIsShowForm();
				Integer var48 = var2.getIsShowForm();
				if (var47 == null) {
					if (var48 != null) {
						return false;
					}
				} else if (!var47.equals(var48)) {
					return false;
				}

				label234 : {
					Integer var49 = this.getIsShowList();
					Integer var50 = var2.getIsShowList();
					if (var49 == null) {
						if (var50 == null) {
							break label234;
						}
					} else if (var49.equals(var50)) {
						break label234;
					}

					return false;
				}

				String var51 = this.getQueryMode();
				String var52 = var2.getQueryMode();
				if (var51 == null) {
					if (var52 != null) {
						return false;
					}
				} else if (!var51.equals(var52)) {
					return false;
				}

				label220 : {
					String var53 = this.getMainTable();
					String var54 = var2.getMainTable();
					if (var53 == null) {
						if (var54 == null) {
							break label220;
						}
					} else if (var53.equals(var54)) {
						break label220;
					}

					return false;
				}

				String var55 = this.getMainField();
				String var56 = var2.getMainField();
				if (var55 == null) {
					if (var56 != null) {
						return false;
					}
				} else if (!var55.equals(var56)) {
					return false;
				}

				Integer var57 = this.getOrderNum();
				Integer var58 = var2.getOrderNum();
				if (var57 == null) {
					if (var58 != null) {
						return false;
					}
				} else if (!var57.equals(var58)) {
					return false;
				}

				label199 : {
					String var59 = this.getUpdateBy();
					String var60 = var2.getUpdateBy();
					if (var59 == null) {
						if (var60 == null) {
							break label199;
						}
					} else if (var59.equals(var60)) {
						break label199;
					}

					return false;
				}

				label192 : {
					Date var61 = this.getUpdateTime();
					Date var62 = var2.getUpdateTime();
					if (var61 == null) {
						if (var62 == null) {
							break label192;
						}
					} else if (var61.equals(var62)) {
						break label192;
					}

					return false;
				}

				Date var63 = this.getCreateTime();
				Date var64 = var2.getCreateTime();
				if (var63 == null) {
					if (var64 != null) {
						return false;
					}
				} else if (!var63.equals(var64)) {
					return false;
				}

				String var65 = this.getCreateBy();
				String var66 = var2.getCreateBy();
				if (var65 == null) {
					if (var66 != null) {
						return false;
					}
				} else if (!var65.equals(var66)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof OnlCgformField;
	}

	public int hashCode() {
		boolean var1 = true;
		byte var2 = 1;
		String var3 = this.getId();
		int var35 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
		String var4 = this.getCgformHeadId();
		var35 = var35 * 59 + (var4 == null ? 43 : var4.hashCode());
		String var5 = this.getDbFieldName();
		var35 = var35 * 59 + (var5 == null ? 43 : var5.hashCode());
		String var6 = this.getDbFieldTxt();
		var35 = var35 * 59 + (var6 == null ? 43 : var6.hashCode());
		String var7 = this.getDbFieldNameOld();
		var35 = var35 * 59 + (var7 == null ? 43 : var7.hashCode());
		Integer var8 = this.getDbIsKey();
		var35 = var35 * 59 + (var8 == null ? 43 : var8.hashCode());
		Integer var9 = this.getDbIsNull();
		var35 = var35 * 59 + (var9 == null ? 43 : var9.hashCode());
		String var10 = this.getDbType();
		var35 = var35 * 59 + (var10 == null ? 43 : var10.hashCode());
		Integer var11 = this.getDbLength();
		var35 = var35 * 59 + (var11 == null ? 43 : var11.hashCode());
		Integer var12 = this.getDbPointLength();
		var35 = var35 * 59 + (var12 == null ? 43 : var12.hashCode());
		String var13 = this.getDbDefaultVal();
		var35 = var35 * 59 + (var13 == null ? 43 : var13.hashCode());
		String var14 = this.getDictField();
		var35 = var35 * 59 + (var14 == null ? 43 : var14.hashCode());
		String var15 = this.getDictTable();
		var35 = var35 * 59 + (var15 == null ? 43 : var15.hashCode());
		String var16 = this.getDictText();
		var35 = var35 * 59 + (var16 == null ? 43 : var16.hashCode());
		String var17 = this.getFieldShowType();
		var35 = var35 * 59 + (var17 == null ? 43 : var17.hashCode());
		String var18 = this.getFieldHref();
		var35 = var35 * 59 + (var18 == null ? 43 : var18.hashCode());
		Integer var19 = this.getFieldLength();
		var35 = var35 * 59 + (var19 == null ? 43 : var19.hashCode());
		String var20 = this.getFieldValidType();
		var35 = var35 * 59 + (var20 == null ? 43 : var20.hashCode());
		String var21 = this.getFieldMustInput();
		var35 = var35 * 59 + (var21 == null ? 43 : var21.hashCode());
		String var22 = this.getFieldExtendJson();
		var35 = var35 * 59 + (var22 == null ? 43 : var22.hashCode());
		String var23 = this.getFieldValueRuleCode();
		var35 = var35 * 59 + (var23 == null ? 43 : var23.hashCode());
		Integer var24 = this.getIsQuery();
		var35 = var35 * 59 + (var24 == null ? 43 : var24.hashCode());
		Integer var25 = this.getIsShowForm();
		var35 = var35 * 59 + (var25 == null ? 43 : var25.hashCode());
		Integer var26 = this.getIsShowList();
		var35 = var35 * 59 + (var26 == null ? 43 : var26.hashCode());
		String var27 = this.getQueryMode();
		var35 = var35 * 59 + (var27 == null ? 43 : var27.hashCode());
		String var28 = this.getMainTable();
		var35 = var35 * 59 + (var28 == null ? 43 : var28.hashCode());
		String var29 = this.getMainField();
		var35 = var35 * 59 + (var29 == null ? 43 : var29.hashCode());
		Integer var30 = this.getOrderNum();
		var35 = var35 * 59 + (var30 == null ? 43 : var30.hashCode());
		String var31 = this.getUpdateBy();
		var35 = var35 * 59 + (var31 == null ? 43 : var31.hashCode());
		Date var32 = this.getUpdateTime();
		var35 = var35 * 59 + (var32 == null ? 43 : var32.hashCode());
		Date var33 = this.getCreateTime();
		var35 = var35 * 59 + (var33 == null ? 43 : var33.hashCode());
		String var34 = this.getCreateBy();
		var35 = var35 * 59 + (var34 == null ? 43 : var34.hashCode());
		return var35;
	}

	public String toString() {
		return "OnlCgformField(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", dbFieldName="
				+ this.getDbFieldName() + ", dbFieldTxt=" + this.getDbFieldTxt() + ", dbFieldNameOld="
				+ this.getDbFieldNameOld() + ", dbIsKey=" + this.getDbIsKey() + ", dbIsNull=" + this.getDbIsNull()
				+ ", dbType=" + this.getDbType() + ", dbLength=" + this.getDbLength() + ", dbPointLength="
				+ this.getDbPointLength() + ", dbDefaultVal=" + this.getDbDefaultVal() + ", dictField="
				+ this.getDictField() + ", dictTable=" + this.getDictTable() + ", dictText=" + this.getDictText()
				+ ", fieldShowType=" + this.getFieldShowType() + ", fieldHref=" + this.getFieldHref() + ", fieldLength="
				+ this.getFieldLength() + ", fieldValidType=" + this.getFieldValidType() + ", fieldMustInput="
				+ this.getFieldMustInput() + ", fieldExtendJson=" + this.getFieldExtendJson() + ", fieldValueRuleCode="
				+ this.getFieldValueRuleCode() + ", isQuery=" + this.getIsQuery() + ", isShowForm="
				+ this.getIsShowForm() + ", isShowList=" + this.getIsShowList() + ", queryMode=" + this.getQueryMode()
				+ ", mainTable=" + this.getMainTable() + ", mainField=" + this.getMainField() + ", orderNum="
				+ this.getOrderNum() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime()
				+ ", createTime=" + this.getCreateTime() + ", createBy=" + this.getCreateBy() + ")";
	}
}