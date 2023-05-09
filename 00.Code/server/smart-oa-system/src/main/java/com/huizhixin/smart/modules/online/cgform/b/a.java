package com.huizhixin.smart.modules.online.cgform.b;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.huizhixin.smart.modules.online.cgform.entity.OnlCgformField;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

public class a extends ExcelDataHandlerDefaultImpl {
	Map<String, OnlCgformField> a;

	public a(List<OnlCgformField> var1) {
		this.a = this.a(var1);
	}

	private Map<String, OnlCgformField> a(List<OnlCgformField> var1) {
		HashMap var2 = new HashMap();
		Iterator var3 = var1.iterator();

		while (var3.hasNext()) {
			OnlCgformField var4 = (OnlCgformField) var3.next();
			var2.put(var4.getDbFieldTxt(), var4);
		}

		return var2;
	}

	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		if (value instanceof Double) {
			map.put(this.a(originKey), PoiPublicUtil.doubleToString((Double) value));
		} else {
			map.put(this.a(originKey), value == null ? "" : value.toString());
		}

	}

	private String a(String var1) {
		return this.a.containsKey(var1)
				? "$mainTable$" + ((OnlCgformField) this.a.get(var1)).getDbFieldName()
				: "$subTable$" + var1;
	}
}