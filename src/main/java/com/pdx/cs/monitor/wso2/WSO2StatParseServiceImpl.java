package com.pdx.cs.monitor.wso2;

import java.util.Formatter;

import com.pdx.cs.monitor.core.MonitorException;
import com.pdx.cs.monitor.core.ParseService;
import com.pdx.cs.monitor.wso2.config.Service_;

public class WSO2StatParseServiceImpl implements ParseService {

	private static final String HEALTH_OK = "HEALTH_OK";
	private static final String HEALTH_BAD = "HEALTH_BAD";
	private static final String AVG_RES_TIME = "Average Response Time";
	private static final String REQ_COUNT = "Request Count";
	private static final String RES_COUNT = "Response Count";

	public String parse(String input, Service_ conf) throws MonitorException {
		long curAvgResTime = 0L;
		long currReqCount = 0L;
		long currResCount = 0L;

		String[] lines = input.split("\\n");

		for (String line : lines) {
			if (line.indexOf(AVG_RES_TIME) != -1) {
				long val = WSO2MonitorUtil.extractNumber(line);
				if (val > 0L)
					curAvgResTime = val;
			} else if (line.indexOf(REQ_COUNT) != -1) {
				long val = WSO2MonitorUtil.extractNumber(line);
				if (val > 0L)
					currReqCount = val;
			} else if (line.indexOf(RES_COUNT) != -1) {
				long val = WSO2MonitorUtil.extractNumber(line);
				if (val > 0L) {
					currResCount = val;
				}

			}

		}

		StringBuilder output = new StringBuilder();
		output.append("WSO2_SERVICE:").append(conf.getName()).append(":");

		String avgFormat = "AVG_RES_TIME=%d:%s";
		Formatter fmt = new Formatter(output);
		if (curAvgResTime > conf.getAvgCritResTime()) {
			fmt.format(avgFormat, new Object[] { Long.valueOf(curAvgResTime), HEALTH_BAD });
		} else {
			fmt.format(avgFormat, new Object[] { Long.valueOf(curAvgResTime), HEALTH_OK });
		}
		output.append("\n");
		output.append("WSO2_SERVICE:").append(conf.getName()).append(":");

		String countFormat = "PENDING_MSG_COUNT-reqCount=%d,resCount=%d:%s";
		if (Math.abs(currReqCount - currResCount) > conf.getMsgCritPending())
			fmt.format(countFormat,
					new Object[] { Long.valueOf(currReqCount), Long.valueOf(currResCount), HEALTH_BAD });
		else {
			fmt.format(countFormat, new Object[] { Long.valueOf(currReqCount), Long.valueOf(currResCount), HEALTH_OK });
		}

		output.append("\n");
		fmt.close();
		return output.toString();
	}

}
