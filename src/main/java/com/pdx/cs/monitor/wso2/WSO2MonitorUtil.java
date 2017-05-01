package com.pdx.cs.monitor.wso2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WSO2MonitorUtil {
	public static long extractNumber(String in) {
		Float f = new Float(-1.0F);
		Matcher matcher = Pattern.compile("\\d+(\\.\\d+)?").matcher(in);
		while (matcher.find()) {
			String res = matcher.group().trim();
			if (!(res.isEmpty()))
				f = Float.valueOf(Float.parseFloat(res));
		}
		return f.longValue();
	}

}
