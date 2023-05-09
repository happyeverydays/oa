package com.huizhixin.smart.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("onl_cgform_index")
public class OnlCgformIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.UUID)
	private String id;
	private String cgformHeadId;
	private String indexName;
	private String indexField;
	private String indexType;
	private String createBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String updateBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public String getId() {
		return this.id;
	}

	public String getCgformHeadId() {
		return this.cgformHeadId;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public String getIndexField() {
		return this.indexField;
	}

	public String getIndexType() {
		return this.indexType;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCgformHeadId(String cgformHeadId) {
		this.cgformHeadId = cgformHeadId;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setIndexField(String indexField) {
		this.indexField = indexField;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof OnlCgformIndex)) {
			return false;
		} else {
			OnlCgformIndex var2 = (OnlCgformIndex) o;
			if (!var2.canEqual(this)) {
				return false;
			} else {
				label119 : {
					String var3 = this.getId();
					String var4 = var2.getId();
					if (var3 == null) {
						if (var4 == null) {
							break label119;
						}
					} else if (var3.equals(var4)) {
						break label119;
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

				label105 : {
					String var7 = this.getIndexName();
					String var8 = var2.getIndexName();
					if (var7 == null) {
						if (var8 == null) {
							break label105;
						}
					} else if (var7.equals(var8)) {
						break label105;
					}

					return false;
				}

				String var9 = this.getIndexField();
				String var10 = var2.getIndexField();
				if (var9 == null) {
					if (var10 != null) {
						return false;
					}
				} else if (!var9.equals(var10)) {
					return false;
				}

				label91 : {
					String var11 = this.getIndexType();
					String var12 = var2.getIndexType();
					if (var11 == null) {
						if (var12 == null) {
							break label91;
						}
					} else if (var11.equals(var12)) {
						break label91;
					}

					return false;
				}

				String var13 = this.getCreateBy();
				String var14 = var2.getCreateBy();
				if (var13 == null) {
					if (var14 != null) {
						return false;
					}
				} else if (!var13.equals(var14)) {
					return false;
				}

				label77 : {
					Date var15 = this.getCreateTime();
					Date var16 = var2.getCreateTime();
					if (var15 == null) {
						if (var16 == null) {
							break label77;
						}
					} else if (var15.equals(var16)) {
						break label77;
					}

					return false;
				}

				label70 : {
					String var17 = this.getUpdateBy();
					String var18 = var2.getUpdateBy();
					if (var17 == null) {
						if (var18 == null) {
							break label70;
						}
					} else if (var17.equals(var18)) {
						break label70;
					}

					return false;
				}

				Date var19 = this.getUpdateTime();
				Date var20 = var2.getUpdateTime();
				if (var19 == null) {
					if (var20 != null) {
						return false;
					}
				} else if (!var19.equals(var20)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof OnlCgformIndex;
	}

	public int hashCode() {
		boolean var1 = true;
		byte var2 = 1;
		String var3 = this.getId();
		int var12 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
		String var4 = this.getCgformHeadId();
		var12 = var12 * 59 + (var4 == null ? 43 : var4.hashCode());
		String var5 = this.getIndexName();
		var12 = var12 * 59 + (var5 == null ? 43 : var5.hashCode());
		String var6 = this.getIndexField();
		var12 = var12 * 59 + (var6 == null ? 43 : var6.hashCode());
		String var7 = this.getIndexType();
		var12 = var12 * 59 + (var7 == null ? 43 : var7.hashCode());
		String var8 = this.getCreateBy();
		var12 = var12 * 59 + (var8 == null ? 43 : var8.hashCode());
		Date var9 = this.getCreateTime();
		var12 = var12 * 59 + (var9 == null ? 43 : var9.hashCode());
		String var10 = this.getUpdateBy();
		var12 = var12 * 59 + (var10 == null ? 43 : var10.hashCode());
		Date var11 = this.getUpdateTime();
		var12 = var12 * 59 + (var11 == null ? 43 : var11.hashCode());
		return var12;
	}

	public String toString() {
		return "OnlCgformIndex(id=" + this.getId() + ", cgformHeadId=" + this.getCgformHeadId() + ", indexName="
				+ this.getIndexName() + ", indexField=" + this.getIndexField() + ", indexType=" + this.getIndexType()
				+ ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy="
				+ this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
	}
}