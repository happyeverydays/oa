package org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Test {

	public static void main(String[] args) {

		List<String> userids = new ArrayList<String>();
		userids.add("11");
		userids.add("33");
		System.out.println(StringUtils.join(userids));
		
	}

}
