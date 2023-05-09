package com.huizhixin.smart.modules.online.cgform.service.a;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import com.huizhixin.smart.modules.online.cgform.b.b;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformFieldMapper;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformSqlService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("onlCgformSqlServiceImpl")
public class e implements IOnlCgformSqlService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void saveBatchOnlineTable(String tableName, List<OnlCgformField> fieldList,
			List<Map<String, Object>> dataList) {
		SqlSession var4 = null;

		try {
			var4 = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			OnlCgformFieldMapper var5 = (OnlCgformFieldMapper) var4.getMapper(OnlCgformFieldMapper.class);
			short var6 = 1000;
			int var7;
			String var8;
			Map var9;
			if (var6 >= dataList.size()) {
				for (var7 = 0; var7 < dataList.size(); ++var7) {
					var8 = JSON.toJSONString(dataList.get(var7));
					var9 = b.a(tableName, fieldList, JSONObject.parseObject(var8));
					var5.executeInsertSQL(var9);
				}
			} else {
				for (var7 = 0; var7 < dataList.size(); ++var7) {
					var8 = JSON.toJSONString(dataList.get(var7));
					var9 = b.a(tableName, fieldList, JSONObject.parseObject(var8));
					var5.executeInsertSQL(var9);
					if (var7 % var6 == 0) {
						var4.commit();
						var4.clearCache();
					}
				}
			}

			var4.commit();
		} catch (Exception var13) {
			var13.printStackTrace();
		} finally {
			var4.close();
		}

	}
}