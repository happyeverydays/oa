package com.huizhixin.smart.modules.online.cgform.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Iterator;
import java.util.List;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformIndex;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformHeadMapper;
import com.huizhixin.smart.modules.online.cgform.mapper.OnlCgformIndexMapper;
import com.huizhixin.smart.modules.online.cgform.service.IOnlCgformIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("onlCgformIndexServiceImpl")
public class d extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {
	@Autowired
	private OnlCgformHeadMapper onlCgformHeadMapper;

	public void createIndex(String code, String databaseType, String tbname) {
		LambdaQueryWrapper<OnlCgformIndex> var4 = new LambdaQueryWrapper();
		var4.eq(OnlCgformIndex::getCgformHeadId, code);
		List var5 = this.list(var4);
		String var8;
		if (var5 != null && var5.size() > 0) {
			for (Iterator var6 = var5.iterator(); var6.hasNext(); this.onlCgformHeadMapper.executeDDL(var8)) {
				OnlCgformIndex var7 = (OnlCgformIndex) var6.next();
				var8 = "";
				String var9 = var7.getIndexName();
				String var10 = var7.getIndexField();
				String var11 = "normal".equals(var7.getIndexType()) ? " index " : var7.getIndexType() + " index ";
				byte var13 = -1;
				switch (databaseType.hashCode()) {
					case -1955532418 :
						if (databaseType.equals("ORACLE")) {
							var13 = 1;
						}
						break;
					case -1620389036 :
						if (databaseType.equals("POSTGRESQL")) {
							var13 = 3;
						}
						break;
					case 73844866 :
						if (databaseType.equals("MYSQL")) {
							var13 = 0;
						}
						break;
					case 912124529 :
						if (databaseType.equals("SQLSERVER")) {
							var13 = 2;
						}
				}

				switch (var13) {
					case 0 :
						var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
						break;
					case 1 :
						var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
						break;
					case 2 :
						var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
						break;
					case 3 :
						var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
						break;
					default :
						var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
				}
			}
		}

	}
}