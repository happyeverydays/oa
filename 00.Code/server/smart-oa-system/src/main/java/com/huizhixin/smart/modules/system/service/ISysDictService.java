package com.huizhixin.smart.modules.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huizhixin.smart.common.system.vo.DictModel;
import com.huizhixin.smart.modules.system.entity.SysDict;
import com.huizhixin.smart.modules.system.entity.SysDictItem;
import com.huizhixin.smart.modules.system.model.TreeSelectModel;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictService extends IService<SysDict> {

    public List<DictModel> queryDictItemsByCode(String code);

    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);
    
	public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);

    public String queryDictTextByKey(String code, String key);

    String queryTableDictTextByKey(String table, String text, String code, String key);

    /**
     * 根据字典类型删除关联表中其对应的数据
     *
     * @param sysDict
     * @return
     */
    boolean deleteByDictId(SysDict sysDict);

    /**
     * 添加一对多
     */
    public void saveMain(SysDict sysDict, List<SysDictItem> sysDictItemList);
    
    /**
	 * 查询所有部门 作为字典信息 id -->value,departName -->text
	 * @return
	 */
	public List<DictModel> queryAllDepartBackDictModel();
	
	/**
	 * 查询所有用户  作为字典信息 username -->value,realname -->text
	 * @return
	 */
	public List<DictModel> queryAllUserBackDictModel();
	
	/**
	 * 通过关键字查询字典表
	 * @param table
	 * @param text
	 * @param code
	 * @param keyword
	 * @return
	 */
	public List<DictModel> queryTableDictItems(String table, String text, String code,String keyword);
	
	/**
	  * 根据表名、显示字段名、存储字段名 查询树
	 * @param table
	 * @param text
	 * @param code
	 * @param pidField
	 * @param pid
	 * @param hasChildField
	 * @return
	 */
	List<TreeSelectModel> queryTreeList(String table, String text, String code, String pidField,String pid,String hasChildField,String condtion);

}
