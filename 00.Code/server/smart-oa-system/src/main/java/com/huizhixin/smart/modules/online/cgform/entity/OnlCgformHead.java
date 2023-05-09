package com.huizhixin.smart.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgform_head")
public class OnlCgformHead implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.UUID)
	private String id;
	private String tableName;
	private Integer tableType;
	private Integer tableVersion;
	private String tableTxt;
	private String isCheckbox;
	private String isDbSynch;
	private String isPage;
	private String isTree;
	private String idSequence;
	private String idType;
	private String queryMode;
	private Integer relationType;
	private String subTableStr;
	private Integer tabOrderNum;
	private String treeParentIdField;
	private String treeIdField;
	private String treeFieldname;
	private String formCategory;
	private String formTemplate;
	private String formTemplateMobile;
	private String updateBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private String createBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public String getId() {
		return this.id;
	}

	public String getTableName() {
		return this.tableName;
	}

	public Integer getTableType() {
		return this.tableType;
	}

	public Integer getTableVersion() {
		return this.tableVersion;
	}

	public String getTableTxt() {
		return this.tableTxt;
	}

	public String getIsCheckbox() {
		return this.isCheckbox;
	}

	public String getIsDbSynch() {
		return this.isDbSynch;
	}

	public String getIsPage() {
		return this.isPage;
	}

	public String getIsTree() {
		return this.isTree;
	}

	public String getIdSequence() {
		return this.idSequence;
	}

	public String getIdType() {
		return this.idType;
	}

	public String getQueryMode() {
		return this.queryMode;
	}

	public Integer getRelationType() {
		return this.relationType;
	}

	public String getSubTableStr() {
		return this.subTableStr;
	}

	public Integer getTabOrderNum() {
		return this.tabOrderNum;
	}

	public String getTreeParentIdField() {
		return this.treeParentIdField;
	}

	public String getTreeIdField() {
		return this.treeIdField;
	}

	public String getTreeFieldname() {
		return this.treeFieldname;
	}

	public String getFormCategory() {
		return this.formCategory;
	}

	public String getFormTemplate() {
		return this.formTemplate;
	}

	public String getFormTemplateMobile() {
		return this.formTemplateMobile;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public void setTableVersion(Integer tableVersion) {
		this.tableVersion = tableVersion;
	}

	public void setTableTxt(String tableTxt) {
		this.tableTxt = tableTxt;
	}

	public void setIsCheckbox(String isCheckbox) {
		this.isCheckbox = isCheckbox;
	}

	public void setIsDbSynch(String isDbSynch) {
		this.isDbSynch = isDbSynch;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public void setIsTree(String isTree) {
		this.isTree = isTree;
	}

	public void setIdSequence(String idSequence) {
		this.idSequence = idSequence;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public void setSubTableStr(String subTableStr) {
		this.subTableStr = subTableStr;
	}

	public void setTabOrderNum(Integer tabOrderNum) {
		this.tabOrderNum = tabOrderNum;
	}

	public void setTreeParentIdField(String treeParentIdField) {
		this.treeParentIdField = treeParentIdField;
	}

	public void setTreeIdField(String treeIdField) {
		this.treeIdField = treeIdField;
	}

	public void setTreeFieldname(String treeFieldname) {
		this.treeFieldname = treeFieldname;
	}

	public void setFormCategory(String formCategory) {
		this.formCategory = formCategory;
	}

	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}

	public void setFormTemplateMobile(String formTemplateMobile) {
		this.formTemplateMobile = formTemplateMobile;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof OnlCgformHead)) {
			return false;
		} else {
			OnlCgformHead var2 = (OnlCgformHead) o;
			if (!var2.canEqual(this)) {
				return false;
			} else {
				label311 : {
					String var3 = this.getId();
					String var4 = var2.getId();
					if (var3 == null) {
						if (var4 == null) {
							break label311;
						}
					} else if (var3.equals(var4)) {
						break label311;
					}

					return false;
				}

				String var5 = this.getTableName();
				String var6 = var2.getTableName();
				if (var5 == null) {
					if (var6 != null) {
						return false;
					}
				} else if (!var5.equals(var6)) {
					return false;
				}

				label297 : {
					Integer var7 = this.getTableType();
					Integer var8 = var2.getTableType();
					if (var7 == null) {
						if (var8 == null) {
							break label297;
						}
					} else if (var7.equals(var8)) {
						break label297;
					}

					return false;
				}

				Integer var9 = this.getTableVersion();
				Integer var10 = var2.getTableVersion();
				if (var9 == null) {
					if (var10 != null) {
						return false;
					}
				} else if (!var9.equals(var10)) {
					return false;
				}

				label283 : {
					String var11 = this.getTableTxt();
					String var12 = var2.getTableTxt();
					if (var11 == null) {
						if (var12 == null) {
							break label283;
						}
					} else if (var11.equals(var12)) {
						break label283;
					}

					return false;
				}

				String var13 = this.getIsCheckbox();
				String var14 = var2.getIsCheckbox();
				if (var13 == null) {
					if (var14 != null) {
						return false;
					}
				} else if (!var13.equals(var14)) {
					return false;
				}

				label269 : {
					String var15 = this.getIsDbSynch();
					String var16 = var2.getIsDbSynch();
					if (var15 == null) {
						if (var16 == null) {
							break label269;
						}
					} else if (var15.equals(var16)) {
						break label269;
					}

					return false;
				}

				label262 : {
					String var17 = this.getIsPage();
					String var18 = var2.getIsPage();
					if (var17 == null) {
						if (var18 == null) {
							break label262;
						}
					} else if (var17.equals(var18)) {
						break label262;
					}

					return false;
				}

				String var19 = this.getIsTree();
				String var20 = var2.getIsTree();
				if (var19 == null) {
					if (var20 != null) {
						return false;
					}
				} else if (!var19.equals(var20)) {
					return false;
				}

				label248 : {
					String var21 = this.getIdSequence();
					String var22 = var2.getIdSequence();
					if (var21 == null) {
						if (var22 == null) {
							break label248;
						}
					} else if (var21.equals(var22)) {
						break label248;
					}

					return false;
				}

				label241 : {
					String var23 = this.getIdType();
					String var24 = var2.getIdType();
					if (var23 == null) {
						if (var24 == null) {
							break label241;
						}
					} else if (var23.equals(var24)) {
						break label241;
					}

					return false;
				}

				String var25 = this.getQueryMode();
				String var26 = var2.getQueryMode();
				if (var25 == null) {
					if (var26 != null) {
						return false;
					}
				} else if (!var25.equals(var26)) {
					return false;
				}

				Integer var27 = this.getRelationType();
				Integer var28 = var2.getRelationType();
				if (var27 == null) {
					if (var28 != null) {
						return false;
					}
				} else if (!var27.equals(var28)) {
					return false;
				}

				label220 : {
					String var29 = this.getSubTableStr();
					String var30 = var2.getSubTableStr();
					if (var29 == null) {
						if (var30 == null) {
							break label220;
						}
					} else if (var29.equals(var30)) {
						break label220;
					}

					return false;
				}

				Integer var31 = this.getTabOrderNum();
				Integer var32 = var2.getTabOrderNum();
				if (var31 == null) {
					if (var32 != null) {
						return false;
					}
				} else if (!var31.equals(var32)) {
					return false;
				}

				String var33 = this.getTreeParentIdField();
				String var34 = var2.getTreeParentIdField();
				if (var33 == null) {
					if (var34 != null) {
						return false;
					}
				} else if (!var33.equals(var34)) {
					return false;
				}

				label199 : {
					String var35 = this.getTreeIdField();
					String var36 = var2.getTreeIdField();
					if (var35 == null) {
						if (var36 == null) {
							break label199;
						}
					} else if (var35.equals(var36)) {
						break label199;
					}

					return false;
				}

				String var37 = this.getTreeFieldname();
				String var38 = var2.getTreeFieldname();
				if (var37 == null) {
					if (var38 != null) {
						return false;
					}
				} else if (!var37.equals(var38)) {
					return false;
				}

				label185 : {
					String var39 = this.getFormCategory();
					String var40 = var2.getFormCategory();
					if (var39 == null) {
						if (var40 == null) {
							break label185;
						}
					} else if (var39.equals(var40)) {
						break label185;
					}

					return false;
				}

				String var41 = this.getFormTemplate();
				String var42 = var2.getFormTemplate();
				if (var41 == null) {
					if (var42 != null) {
						return false;
					}
				} else if (!var41.equals(var42)) {
					return false;
				}

				label171 : {
					String var43 = this.getFormTemplateMobile();
					String var44 = var2.getFormTemplateMobile();
					if (var43 == null) {
						if (var44 == null) {
							break label171;
						}
					} else if (var43.equals(var44)) {
						break label171;
					}

					return false;
				}

				String var45 = this.getUpdateBy();
				String var46 = var2.getUpdateBy();
				if (var45 == null) {
					if (var46 != null) {
						return false;
					}
				} else if (!var45.equals(var46)) {
					return false;
				}

				label157 : {
					Date var47 = this.getUpdateTime();
					Date var48 = var2.getUpdateTime();
					if (var47 == null) {
						if (var48 == null) {
							break label157;
						}
					} else if (var47.equals(var48)) {
						break label157;
					}

					return false;
				}

				label150 : {
					String var49 = this.getCreateBy();
					String var50 = var2.getCreateBy();
					if (var49 == null) {
						if (var50 == null) {
							break label150;
						}
					} else if (var49.equals(var50)) {
						break label150;
					}

					return false;
				}

				Date var51 = this.getCreateTime();
				Date var52 = var2.getCreateTime();
				if (var51 == null) {
					if (var52 != null) {
						return false;
					}
				} else if (!var51.equals(var52)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof OnlCgformHead;
	}

	public int hashCode() {
		boolean var1 = true;
		byte var2 = 1;
		String var3 = this.getId();
		int var28 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
		String var4 = this.getTableName();
		var28 = var28 * 59 + (var4 == null ? 43 : var4.hashCode());
		Integer var5 = this.getTableType();
		var28 = var28 * 59 + (var5 == null ? 43 : var5.hashCode());
		Integer var6 = this.getTableVersion();
		var28 = var28 * 59 + (var6 == null ? 43 : var6.hashCode());
		String var7 = this.getTableTxt();
		var28 = var28 * 59 + (var7 == null ? 43 : var7.hashCode());
		String var8 = this.getIsCheckbox();
		var28 = var28 * 59 + (var8 == null ? 43 : var8.hashCode());
		String var9 = this.getIsDbSynch();
		var28 = var28 * 59 + (var9 == null ? 43 : var9.hashCode());
		String var10 = this.getIsPage();
		var28 = var28 * 59 + (var10 == null ? 43 : var10.hashCode());
		String var11 = this.getIsTree();
		var28 = var28 * 59 + (var11 == null ? 43 : var11.hashCode());
		String var12 = this.getIdSequence();
		var28 = var28 * 59 + (var12 == null ? 43 : var12.hashCode());
		String var13 = this.getIdType();
		var28 = var28 * 59 + (var13 == null ? 43 : var13.hashCode());
		String var14 = this.getQueryMode();
		var28 = var28 * 59 + (var14 == null ? 43 : var14.hashCode());
		Integer var15 = this.getRelationType();
		var28 = var28 * 59 + (var15 == null ? 43 : var15.hashCode());
		String var16 = this.getSubTableStr();
		var28 = var28 * 59 + (var16 == null ? 43 : var16.hashCode());
		Integer var17 = this.getTabOrderNum();
		var28 = var28 * 59 + (var17 == null ? 43 : var17.hashCode());
		String var18 = this.getTreeParentIdField();
		var28 = var28 * 59 + (var18 == null ? 43 : var18.hashCode());
		String var19 = this.getTreeIdField();
		var28 = var28 * 59 + (var19 == null ? 43 : var19.hashCode());
		String var20 = this.getTreeFieldname();
		var28 = var28 * 59 + (var20 == null ? 43 : var20.hashCode());
		String var21 = this.getFormCategory();
		var28 = var28 * 59 + (var21 == null ? 43 : var21.hashCode());
		String var22 = this.getFormTemplate();
		var28 = var28 * 59 + (var22 == null ? 43 : var22.hashCode());
		String var23 = this.getFormTemplateMobile();
		var28 = var28 * 59 + (var23 == null ? 43 : var23.hashCode());
		String var24 = this.getUpdateBy();
		var28 = var28 * 59 + (var24 == null ? 43 : var24.hashCode());
		Date var25 = this.getUpdateTime();
		var28 = var28 * 59 + (var25 == null ? 43 : var25.hashCode());
		String var26 = this.getCreateBy();
		var28 = var28 * 59 + (var26 == null ? 43 : var26.hashCode());
		Date var27 = this.getCreateTime();
		var28 = var28 * 59 + (var27 == null ? 43 : var27.hashCode());
		return var28;
	}

	public String toString() {
		return "OnlCgformHead(id=" + this.getId() + ", tableName=" + this.getTableName() + ", tableType="
				+ this.getTableType() + ", tableVersion=" + this.getTableVersion() + ", tableTxt=" + this.getTableTxt()
				+ ", isCheckbox=" + this.getIsCheckbox() + ", isDbSynch=" + this.getIsDbSynch() + ", isPage="
				+ this.getIsPage() + ", isTree=" + this.getIsTree() + ", idSequence=" + this.getIdSequence()
				+ ", idType=" + this.getIdType() + ", queryMode=" + this.getQueryMode() + ", relationType="
				+ this.getRelationType() + ", subTableStr=" + this.getSubTableStr() + ", tabOrderNum="
				+ this.getTabOrderNum() + ", treeParentIdField=" + this.getTreeParentIdField() + ", treeIdField="
				+ this.getTreeIdField() + ", treeFieldname=" + this.getTreeFieldname() + ", formCategory="
				+ this.getFormCategory() + ", formTemplate=" + this.getFormTemplate() + ", formTemplateMobile="
				+ this.getFormTemplateMobile() + ", updateBy=" + this.getUpdateBy() + ", updateTime="
				+ this.getUpdateTime() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime()
				+ ")";
	}
}